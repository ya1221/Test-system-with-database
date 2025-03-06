package Yahav_Yehoshua_Bariah;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FileAutomaticExam {
	public static void createFileE(Connection connection, int idExa, int idRep) throws FileNotFoundException {
		// Output: The function writes to a file the content of all the questions, and
		// their answers and for close questions the answers - None of the above, There
		// is more than one correct answer.
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		File fileE = new File("exam_" + (Exam.getTimeCreated(connection, idExa)).replace(":", "-") + ".txt");
		PrintWriter pwE = new PrintWriter(fileE);
		String sql = "SELECT q.Question_ID, q.Question_Content, q.Question_Type, a.Answer_Content "
				+ "FROM Question_Of_Automatic_Exam qae " + "JOIN Question q ON qae.Question_ID = q.Question_ID "
				+ "JOIN Answer a ON qae.Answer_ID = a.Answer_ID " + "WHERE qae.Exam_ID = ? "
				+ "ORDER BY q.Question_ID, a.Answer_Content";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idExa);
			resultSet = preparedStatement.executeQuery();
			int currentQuestionId = -1, questionId, i = 1;
			boolean isClose = false;
			while (resultSet.next()) {
				questionId = resultSet.getInt("Question_ID");
				if (questionId != currentQuestionId) {
					if (currentQuestionId != -1 && isClose)
						pwE.println("\t" + i + ") None of the above\n");
					i = 1;
					isClose = resultSet.getString("Question_Type").equals("Close_Question");
					pwE.println(resultSet.getString("Question_Content"));
					currentQuestionId = questionId;
				}
				if (isClose)
					pwE.println("\t" + i + ") " + resultSet.getString("Answer_Content"));
				else
					pwE.println();
				i++;
			}
			pwE.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionToDataBase.closePreparedStatement(preparedStatement);
			ConnectionToDataBase.closeResultSet(resultSet);
		}
	}

	public static void createFileS(Connection connection, int idExa, int idRep) throws FileNotFoundException {
		// Output: The function writes to the file the content of the questions and
		// their correct answer.
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		File fileS = new File("solution_" + (Exam.getTimeCreated(connection, idExa)).replace(":", "-") + ".txt");
		PrintWriter pwS = new PrintWriter(fileS);
		String sql = "SELECT q.Question_ID, q.Question_Content, q.Question_Type, a.Answer_Content, qae.Correct "
				+ "FROM Question_Of_Automatic_Exam qae " + "JOIN Question q ON qae.Question_ID = q.Question_ID "
				+ "JOIN Answer a ON qae.Answer_ID = a.Answer_ID " + "WHERE qae.Exam_ID = ? "
				+ "ORDER BY q.Question_ID, a.Answer_Content";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idExa);
			resultSet = preparedStatement.executeQuery();
			int currentQuestionId = -1, questionId;
			boolean haveTrue = false;
			while (resultSet.next()) {
				questionId = resultSet.getInt("Question_ID");
				if (questionId != currentQuestionId) {
					if (currentQuestionId != -1 && !haveTrue)
						pwS.println("\tNone of the above\n");
					haveTrue = false;
					pwS.println(resultSet.getString("Question_Content"));
					currentQuestionId = questionId;
				}
				if (resultSet.getString("Question_Type").equals("Open_Question")) {
					pwS.println("\tAnswer for open\n");
					haveTrue = true;
				} else if (resultSet.getBoolean("Correct")) {
					pwS.println("\t" + resultSet.getString("Answer_Content") + "\n");
					haveTrue = true;
				}
			}
			pwS.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionToDataBase.closePreparedStatement(preparedStatement);
			ConnectionToDataBase.closeResultSet(resultSet);
		}
	}
}