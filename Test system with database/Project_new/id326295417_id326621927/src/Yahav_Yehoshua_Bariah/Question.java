package Yahav_Yehoshua_Bariah;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Question {
	public enum eDifficulty {
		Hard, Medium, Easy
	}

	public static String getContent(Connection connection, int idQue, int idRep) {
		// Output: The function returns the content of the question.
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT Question_Content FROM Question WHERE Repository_ID = ? AND Question_ID = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idRep);
			preparedStatement.setInt(2, idQue);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				return resultSet.getString("Question_Content");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionToDataBase.closePreparedStatement(preparedStatement);
			ConnectionToDataBase.closeResultSet(resultSet);
		}
		return null;
	}

	public static eDifficulty getDifficulty(Connection connection, int numQue, int idRep) {
		// Output: The function returns the difficulty level of the question.
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT Difficulty FROM Question WHERE Repository_ID = ? ORDER BY Question_ID LIMIT 1 OFFSET ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idRep);
			preparedStatement.setInt(2, numQue - 1);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				return eDifficulty.valueOf(resultSet.getString("Difficulty"));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionToDataBase.closePreparedStatement(preparedStatement);
			ConnectionToDataBase.closeResultSet(resultSet);
		}
		return null;
	}

	public static String getTypeOfQuestion(Connection connection, int numQue, int idRep) {
		// Output: The function returns the question type (Open question/Close
		// questions).
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT Question_Type FROM Question WHERE Repository_ID = ? ORDER BY Question_ID LIMIT 1 OFFSET ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idRep);
			preparedStatement.setInt(2, numQue - 1);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				return resultSet.getString("Question_Type");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionToDataBase.closePreparedStatement(preparedStatement);
			ConnectionToDataBase.closeResultSet(resultSet);
		}
		return null;
	}

	public static int findId(Connection connection, int numQue, int idRep) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT Question_ID FROM Question WHERE Repository_ID = ? ORDER BY Question_ID LIMIT 1 OFFSET ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idRep);
			preparedStatement.setInt(2, numQue - 1);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				return resultSet.getInt("Question_ID");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionToDataBase.closePreparedStatement(preparedStatement);
			ConnectionToDataBase.closeResultSet(resultSet);
		}
		return -1;
	}

	public static boolean addQuestion(Connection connection, String que, Question.eDifficulty difficulty, int idRep,
			String typeQue) {
		// Output: The function adds the question to the table close_question and
		// returns true, else it returns false.
		PreparedStatement preparedStatement = null;
		String sql = "WITH ins_question AS ("
				+ "    INSERT INTO Question (Question_Content, Difficulty, Question_Type, Repository_ID) "
				+ "    VALUES (?, ?::Difficulty_ENUM, ?::Question_Type_ENUM, ?) " + "    RETURNING Question_ID" + ") "
				+ "INSERT INTO " + typeQue + " (Question_ID) " + "SELECT Question_ID FROM ins_question";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, que);
			preparedStatement.setString(2, difficulty.name());
			preparedStatement.setString(3, typeQue);
			preparedStatement.setInt(4, idRep);
			if (preparedStatement.executeUpdate() == 0)
				return false;
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			ConnectionToDataBase.closePreparedStatement(preparedStatement);
		}
	}

	public static boolean deleteQuestion(Connection connection, int numQue, int idRep) {
		// Output: The function returns true if the number is correct and the question
		// has been deleted, else it returns false.
		int idQue = Question.findId(connection, numQue, idRep);
		if (idQue == -1)
			return false;
		PreparedStatement preparedStatement = null;
		String sql = "DELETE FROM Question WHERE Question_ID = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idQue);
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

	public static String listAnswersForExem(Connection connection, int idQue, int idRep) {
		// Output: The function returns the content of the question and all the answers
		// of the question.
		StringBuffer res = new StringBuffer(Question.getContent(connection, idQue, idRep) + "\n");
		return res.toString();
	}

	public static String toStringQuestion(Connection connection, int numQue, int idQue, int idRep) {
		// Output: The function returns the question details and all the answers to the
		// question.
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT Question_Content, Difficulty, Question_Type FROM Question WHERE Repository_ID = ? ORDER BY Question_ID LIMIT 1 OFFSET ?";
		String content = null, difficulty = null, type = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idRep);
			preparedStatement.setInt(2, numQue - 1);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				content = resultSet.getString("Question_Content");
				difficulty = resultSet.getString("Difficulty");
				type = resultSet.getString("Question_Type");
			} else
				return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			ConnectionToDataBase.closePreparedStatement(preparedStatement);
			ConnectionToDataBase.closeResultSet(resultSet);
		}
		StringBuffer res = new StringBuffer("Serial (" + idQue + ") " + content + " - difficulty level -> " + difficulty
				+ " - question type -> " + type + "\n");
		return res.toString();
	}
}
