package Yahav_Yehoshua_Bariah;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FileManualExam {
	public static void createFileE(Connection connection, int idExa, int idRep) throws FileNotFoundException {
		// Output: The function writes to a file the content of all the questions, and
		// their answers and for close questions the answers - None of the above, There
		// is more than one correct answer.
		File fileE = new File("exam_" + (Exam.getTimeCreated(connection, idExa)).replace(":", "-") + ".txt");
		PrintWriter pwE = new PrintWriter(fileE);
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT * FROM Question q "
				+ "JOIN Question_Of_Exam qoe ON q.Question_ID = qoe.Question_ID WHERE qoe.Exam_ID = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idExa);
			resultSet = preparedStatement.executeQuery();
			int countAns;
			while (resultSet.next()) {
				countAns = CloseQuestions.getCountAnswer(connection, resultSet.getInt("Question_ID"), idRep);
				if (resultSet.getString("Question_Type").equals("Close_Question")) {
					pwE.println(CloseQuestions.listAnswersForExem(connection, resultSet.getInt("Question_ID"), idRep)
							+ "\t" + (countAns + 1) + ") None of the above\n\t" + (countAns + 2)
							+ ") There is more than one correct answer" + "\n");
				} else
					pwE.println(OpenQuestion.listAnswersForExem(connection, resultSet.getInt("Question_ID"), idRep));
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
		int countTrue;
		File fileS = new File("solution_" + (Exam.getTimeCreated(connection, idExa)).replace(":", "-") + ".txt");
		PrintWriter pwS = new PrintWriter(fileS);
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT * FROM Question q "
				+ "JOIN Question_Of_Exam qoe ON q.Question_ID = qoe.Question_ID WHERE qoe.Exam_ID = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idExa);
			resultSet = preparedStatement.executeQuery();
			int countAns;
			while (resultSet.next()) {
				countAns = CloseQuestions.getCountAnswer(connection, resultSet.getInt("Question_ID"), idRep);
				if (resultSet.getString("Question_Type").equals("Close_Question")) {
					pwS.println(resultSet.getString("Question_Content"));
					countTrue = CloseQuestions.getCountCorrectAnswer(connection, resultSet.getInt("Question_ID"),
							idRep);
					if (countTrue == 1)
						pwS.println(
								CloseQuestions.showCorrectAnswer(connection, resultSet.getInt("Question_ID"), idRep));
					else if (countTrue == 0)
						pwS.println("\tNone of the above\n");
					else
						pwS.println("\tThere is more than one correct answer\n");
				} else
					pwS.println(OpenQuestion.getContent(connection, resultSet.getInt("Question_ID"), idRep) + "\n"
							+ OpenQuestion.showCorrectAnswer(connection, resultSet.getInt("Question_ID")));
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
