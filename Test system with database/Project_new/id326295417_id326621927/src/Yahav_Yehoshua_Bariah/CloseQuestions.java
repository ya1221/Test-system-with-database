package Yahav_Yehoshua_Bariah;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CloseQuestions extends Question {
	public static int MaxOfAnswers = 10;

	public static int getCountCorrectAnswer(Connection connection, int idQue, int idRep) {
		// Output: The function returns the number of correct answers.
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT COUNT(*) AS correct_answers_count FROM Close_Question_with_answer "
				+ "WHERE Question_ID = ? AND Correct = TRUE";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idQue);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				return resultSet.getInt("correct_answers_count");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionToDataBase.closePreparedStatement(preparedStatement);
			ConnectionToDataBase.closeResultSet(resultSet);
		}
		return -1;
	}

	public static int getCountAnswer(Connection connection, int idQue, int idRep) {
		// Output: The function returns the number of correct answers.
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT COUNT(*) AS answers_count FROM Close_Question_with_answer " + "WHERE Question_ID = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idQue);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				return resultSet.getInt("answers_count");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionToDataBase.closePreparedStatement(preparedStatement);
			ConnectionToDataBase.closeResultSet(resultSet);
		}
		return -1;
	}

	public static boolean addAnswerToQuestion(Connection connection, int numQue, int numAns, boolean correctAns,
			int idRep) {
		// Output: The function adds the answer to the array of answers of the question.
		PreparedStatement preparedStatement = null;
		String sql = "INSERT INTO Close_Question_with_answer (Question_ID, Answer_ID, Correct) VALUES (?, ?, ?)";
		try {
			int idQue = Question.findId(connection, numQue, idRep);
			int idAns = Answer.findId(connection, numAns, idRep);
			if (idQue == -1 || idAns == -1)
				return false;
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idQue);
			preparedStatement.setInt(2, idAns);
			preparedStatement.setBoolean(3, correctAns);
			if (preparedStatement.executeUpdate() == 0)
				return false;
			return true;
		} catch (SQLException e) {
			return false;
		} finally {
			ConnectionToDataBase.closePreparedStatement(preparedStatement);
		}
	}

	public static boolean deleteAns(Connection connection, int numQue, int numAns, int idRep) {
		// Output: The function deletes the answer from the array of the answers.
		PreparedStatement preparedStatement = null;
		String sql = "DELETE FROM Close_Question_with_answer WHERE Question_ID = ? AND Answer_ID = ?";
		try {
			int idQue = Question.findId(connection, numQue, idRep);
			if (idQue == -1)
				return false;
			int idAns = Answer.findIdFromQue(connection, numAns, idQue);
			if (idAns == -1)
				return false;
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idQue);
			preparedStatement.setInt(2, idAns);
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

	public static String showCorrectAnswer(Connection connection, int idQue, int idRep) {
		// Output: The function returns the content of the correct answer of the
		// question.
		StringBuffer res = new StringBuffer();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT a.Answer_Content FROM Close_Question_with_answer cqa "
				+ "JOIN Answer a ON cqa.Answer_ID = a.Answer_ID " + "WHERE cqa.Question_ID = ? AND cqa.Correct = TRUE";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idQue);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next())
				res.append("\t" + resultSet.getString("Answer_Content") + "\n");
			return res.toString();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionToDataBase.closePreparedStatement(preparedStatement);
			ConnectionToDataBase.closeResultSet(resultSet);
		}
		return null;
	}

	public static String listAnswersForExem(Connection connection, int idQue, int idRep) {
		// Output: The function returns the content of the question and all the answers
		// of the question.
		StringBuffer res = new StringBuffer(Question.listAnswersForExem(connection, idQue, idRep));
		int i = 1;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT a.Answer_Content FROM Close_Question_with_answer cqa "
				+ "JOIN Answer a ON cqa.Answer_ID = a.Answer_ID " + "WHERE cqa.Question_ID = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idQue);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				res.append("\t" + i + ") " + resultSet.getString("Answer_Content") + "\n");
				i++;
			}
			return res.toString();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionToDataBase.closePreparedStatement(preparedStatement);
			ConnectionToDataBase.closeResultSet(resultSet);
		}
		return null;
	}

	public static String toStringCloseQuestion(Connection connection, int numQue, int idRep) {
		// Output: The function returns the question details and all the answers to the
		// question.
		int i = 1, idQue = Question.findId(connection, numQue, idRep);
		if (idQue == -1)
			return null;
		StringBuffer res = new StringBuffer(Question.toStringQuestion(connection, numQue, idQue, idRep));
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT * FROM Close_Question_with_answer cqa " + "JOIN Answer a ON cqa.Answer_ID = a.Answer_ID "
				+ "WHERE cqa.Question_ID = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idQue);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				res.append("\t" + i + ") " + resultSet.getString("Answer_Content") + " -> "
						+ resultSet.getBoolean("Correct") + "\n");
				i++;
			}
			return res.toString();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionToDataBase.closePreparedStatement(preparedStatement);
			ConnectionToDataBase.closeResultSet(resultSet);
		}
		return null;
	}
}
