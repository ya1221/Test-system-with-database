package Yahav_Yehoshua_Bariah;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class ConnectionToDataBase {
	public static void closeResultSet(ResultSet resultSet) {
		try {
			if (resultSet != null)
				resultSet.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void closePreparedStatement(PreparedStatement preparedStatement) {
		try {
			if (preparedStatement != null)
				preparedStatement.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static Connection connectionUP() {
		String dbUrl = "jdbc:postgresql://localhost:5432/id326295417_id326621927";
		String username = "postgres";
		String password = "Yahav12data";

		try {
			Connection connection = DriverManager.getConnection(dbUrl, username, password);
			return connection;
		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public static void closeConnection(Connection connection) {
		try {
			if (connection != null && !connection.isClosed())
				connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}
