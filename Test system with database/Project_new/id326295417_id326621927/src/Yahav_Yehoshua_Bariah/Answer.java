package Yahav_Yehoshua_Bariah;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Answer {
	public static String getContent(Connection connection, int numAns, int idRep) {
		// Output: The function returns the content of the answer.
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT Answer_Content FROM Answer WHERE Repository_ID = ? ORDER BY Answer_ID LIMIT 1 OFFSET ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idRep);
			preparedStatement.setInt(2, numAns - 1);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getString("Answer_Content");
			} else
				return null;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionToDataBase.closePreparedStatement(preparedStatement);
			ConnectionToDataBase.closeResultSet(resultSet);
		}
		return null;
	}

	public static int findId(Connection connection, int numAns, int idRep) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT Answer_ID FROM Answer WHERE Repository_ID = ? ORDER BY Answer_ID LIMIT 1 OFFSET ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idRep);
			preparedStatement.setInt(2, numAns - 1);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				return resultSet.getInt("Answer_ID");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionToDataBase.closePreparedStatement(preparedStatement);
			ConnectionToDataBase.closeResultSet(resultSet);
		}
		return -1;
	}

	public static int findIdFromQue(Connection connection, int numAns, int idQue) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT Answer_ID FROM Close_Question_with_answer WHERE Question_ID = ? ORDER BY Answer_ID LIMIT 1 OFFSET ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idQue);
			preparedStatement.setInt(2, numAns - 1);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				return resultSet.getInt("Answer_ID");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionToDataBase.closePreparedStatement(preparedStatement);
			ConnectionToDataBase.closeResultSet(resultSet);
		}
		return -1;
	}

	public static boolean deleteAnswerFromRepository(Connection connection, int numAns, int idRep) {
		PreparedStatement preparedStatement = null;
		String sql = "DELETE FROM Answer WHERE Repository_ID = ? AND Answer_ID = ?";
		int idAns = Answer.findId(connection, numAns, idRep);
		if (idAns == -1)
			return false;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idRep);
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

	public static boolean addAnswerToRepository(Connection connection, String ans, int numRep) {
		// Output: The function adds the answer to the repository (array) and returns
		// true if the answer does not already exist, else it returns false.
		PreparedStatement preparedStatement = null;
		String sql = "INSERT INTO Answer (Answer_Content, Repository_ID) VALUES (?, ?)";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, ans);
			preparedStatement.setInt(2, numRep);
			if (preparedStatement.executeUpdate() == 0)
				return false;
			return true;
		} catch (SQLException e) {
			return false;
		} finally {
			ConnectionToDataBase.closePreparedStatement(preparedStatement);
		}
	}

	public static String toStringAnswer(Connection connection, int numAns, int numRep) {
		// Output: The function returns the content of the answer.
		StringBuffer res = new StringBuffer(Answer.getContent(connection, numAns, numRep));
		return res.toString();
	}
}
