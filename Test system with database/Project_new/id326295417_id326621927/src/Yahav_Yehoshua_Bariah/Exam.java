package Yahav_Yehoshua_Bariah;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.postgresql.util.PSQLException;

public class Exam {
	public static String getTimeCreated(Connection connection, int idExa) {
		String sql = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		sql = "SELECT Time_Created_At FROM Exam WHERE Exam_ID = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idExa);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				return (resultSet.getTimestamp("Time_Created_At")).toString();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionToDataBase.closePreparedStatement(preparedStatement);
			ConnectionToDataBase.closeResultSet(resultSet);
		}
		return null;
	}

	public static int findId(Connection connection, int numExe, int idRep) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT Exam_ID FROM Exam WHERE Repository_ID = ? ORDER BY Exam_ID LIMIT 1 OFFSET ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idRep);
			preparedStatement.setInt(2, numExe - 1);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				return resultSet.getInt("Exam_ID");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionToDataBase.closePreparedStatement(preparedStatement);
			ConnectionToDataBase.closeResultSet(resultSet);
		}
		return -1;
	}

	public static int addExam(Connection connection, String type, int idRep) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "INSERT INTO Exam (Exam_Type, Repository_ID) VALUES (?::Exam_Type_ENUM, ?) RETURNING Exam_ID";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, type);
			preparedStatement.setInt(2, idRep);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				return resultSet.getInt("Exam_ID");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionToDataBase.closePreparedStatement(preparedStatement);
		}
		return -1;
	}

	public static boolean deleteExam(Connection connection, int idExa) {
		PreparedStatement preparedStatement = null;
		String sql = "DELETE FROM Exam WHERE Exam_ID = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idExa);
			if (preparedStatement.executeUpdate() == 0)
				return false;
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionToDataBase.closePreparedStatement(preparedStatement);
		}
		return false;
	}

	public static boolean addQuestion(Connection connection, int idExa, int numQue, int idRep) {
		PreparedStatement preparedStatement = null;
		String sql = "INSERT INTO Question_Of_Exam (Exam_ID, Question_ID) VALUES (?, ?)";

		try {
			int idQue = Question.findId(connection, numQue, idRep);
			if (idQue == -1)
				return false;
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idExa);
			preparedStatement.setInt(2, idQue);
			if (preparedStatement.executeUpdate() == 0)
				return false;
			return true;
		} catch (PSQLException e) {
			System.out.println(Check.extractErrorMessage(e.getMessage()));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionToDataBase.closePreparedStatement(preparedStatement);
		}
		return false;
	}

	public static boolean addQuestionToAutomatic(Connection connection, int idExa, int numQue, int numAns,
			boolean correct, int idRep) {
		PreparedStatement preparedStatement = null;
		String sql = "INSERT INTO Question_Of_Automatic_Exam (Exam_ID, Question_ID, Answer_ID, Correct) VALUES (?, ?, ?, ?)";
		try {
			int idQue = Question.findId(connection, numQue, idRep);
			int idAns = Answer.findId(connection, numAns, idRep);
			if (idQue == -1 || idAns == -1)
				return false;
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idExa);
			preparedStatement.setInt(2, idQue);
			preparedStatement.setInt(3, idAns);
			preparedStatement.setBoolean(4, correct);
			if (preparedStatement.executeUpdate() == 0)
				return false;
			return true;
		} catch (SQLException e) {
			return false;
		} finally {
			ConnectionToDataBase.closePreparedStatement(preparedStatement);
		}
	}

	public static String showAllExam(Connection connection, int idRep) {
		StringBuffer res = new StringBuffer("This is the list of all the exams that belong to the repository: \n");
		int i = 1;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT * FROM Exam WHERE Repository_ID = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idRep);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				res.append(i + ") " + "Serial (" + resultSet.getInt("Exam_ID") + ") - "
						+ Exam.getTimeCreated(connection, resultSet.getInt("Exam_ID")).replace(":", "-") + " - "
						+ resultSet.getString("Exam_Type") + "\n");
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionToDataBase.closePreparedStatement(preparedStatement);
			ConnectionToDataBase.closeResultSet(resultSet);
		}
		return res.toString();
	}
}
