package Yahav_Yehoshua_Bariah;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OpenQuestion extends Question {

	public static boolean addAnswerToQuestion(Connection connection, int numQue, int numAns, int idRep) {
		// Output: The function adds the answer to the question.
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "UPDATE Open_Question SET Answer_ID = ? WHERE Question_ID = (SELECT Question_ID FROM Question "
				+ "ORDER BY Question_ID LIMIT 1 OFFSET ?)";
		try {
			int idAns = Answer.findId(connection, numAns, idRep);
			if (idAns == -1)
				return false;
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idAns);
			preparedStatement.setInt(2, numQue - 1);
			if (preparedStatement.executeUpdate() == 0)
				return false;
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionToDataBase.closePreparedStatement(preparedStatement);
			ConnectionToDataBase.closeResultSet(resultSet);
		}
		return false;
	}

	public static boolean deleteAns(Connection connection, int numQue, int idRep) {
		// Output: The function deletes the answer.
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "UPDATE Open_Question SET Answer_ID = NULL WHERE Question_ID = (SELECT Question_ID FROM Open_Question "
				+ "ORDER BY Question_ID LIMIT 1 OFFSET ?)";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, numQue - 1);
			if (preparedStatement.executeUpdate() == 0)
				return false;
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionToDataBase.closePreparedStatement(preparedStatement);
			ConnectionToDataBase.closeResultSet(resultSet);
		}
		return false;
	}

	public static String showCorrectAnswer(Connection connection, int idQue) {
		// Output: The function returns the content of the correct answer of the
		// question.
		StringBuffer res = new StringBuffer();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT a.Answer_Content FROM Open_Question oq " + "JOIN Answer a ON oq.Answer_ID = a.Answer_ID "
				+ "WHERE oq.Question_ID = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idQue);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
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

	public static String toStringOpenQuestion(Connection connection, int numQue, int idRep) {
		// Output: The function returns the content of the question and all the answers
		// of the question.
		int idQue = Question.findId(connection, numQue, idRep);
		StringBuffer res = new StringBuffer(Question.toStringQuestion(connection, numQue, idQue, idRep));
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT a.Answer_Content FROM Open_Question oq JOIN Answer a ON oq.Answer_ID = a.Answer_ID "
				+ "WHERE Question_ID = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, Question.findId(connection, numQue, idRep));
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				res.append("\t1) " + resultSet.getString("Answer_Content") + " -> " + "true" + "\n");
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
