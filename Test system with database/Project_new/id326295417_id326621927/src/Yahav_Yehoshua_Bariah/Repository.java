package Yahav_Yehoshua_Bariah;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Repository {
	public static final int MaxOfQuestion = 10;

	public static String getProfession(Connection connection, int numRep) {
		// Output: The function returns the profession of the repository.
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT Profession FROM Answer ORDER BY Repository_ID LIMIT 1 OFFSET ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, numRep - 1);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getString("Profession");
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

	public static int findId(Connection connection, int numRep) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT Repository_ID FROM Repository ORDER BY Repository_ID LIMIT 1 OFFSET ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, numRep - 1);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				return resultSet.getInt("Repository_ID");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionToDataBase.closePreparedStatement(preparedStatement);
			ConnectionToDataBase.closeResultSet(resultSet);
		}
		return -1;
	}

	public static int countInRepository(Connection connection, String src, int idRep) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT COUNT(*) FROM " + src + " WHERE Repository_ID = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idRep);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				return resultSet.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionToDataBase.closePreparedStatement(preparedStatement);
			ConnectionToDataBase.closeResultSet(resultSet);
		}
		return -1;
	}

	public static void deleteRepository(Connection connection, int idRep) {
		PreparedStatement preparedStatement = null;
		String sql = "DELETE FROM Repository WHERE Repository_ID = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idRep);
			if (preparedStatement.executeUpdate() == 0)
				System.out.println("The Repository is not deleted.");
			else
				System.out.println("The Repository was successfully deleted.");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionToDataBase.closePreparedStatement(preparedStatement);
		}
	}

	public static String showAllAnswer(Connection connection, int idRep) {
		// Output: The function returns all the answers in the repository.
		int i = 1;
		StringBuffer res = new StringBuffer("This is the list of all the answers that exist in the repository:\n");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT Answer_Content FROM Answer WHERE Repository_ID = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idRep);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				res.append(i + ") " + resultSet.getString("Answer_Content") + "\n");
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

	public static boolean addAnswerToQuestionInRepository(Scanner input, int numQue, Connection connection, int idRep) {
		// Output: The function adds the answer to the question and returns true if the
		// answer does not already exist, if there are not 10 answers to the question
		// and if all the entered values ​​are correct, else it returns false and
		// prints a message about it.
		int numAns;
		String correctAns;
		boolean correct = false;
		System.out.println(Repository.showAllAnswer(connection, idRep)
				+ "\nEnter the answer number you want to add to the question, or enter 0 to return to the main menu.");
		numAns = input.nextInt();
		if (numAns == 0)
			correct = true;
		else if (1 <= numAns && numAns <= Repository.countInRepository(connection, "Answer", idRep)) {
			correct = true;
			if (Question.getTypeOfQuestion(connection, numQue, idRep).equals("Close_Question")) {
				do {
					System.out.println("Is the answer correct for the question? <true/false>");
					correctAns = input.next();
					if (correctAns.equals("true"))
						correct = true;
					else if (correctAns.equals("false"))
						correct = false;
					else
						System.out.println("The entered value is invalid.");
				} while (!correctAns.equals("true") && !correctAns.equals("false"));
				correct = CloseQuestions.addAnswerToQuestion(connection, numQue, numAns, correct, idRep);
			} else
				correct = OpenQuestion.addAnswerToQuestion(connection, numQue, numAns, idRep);
			if (!correct)
				System.out.println("An incorrect value was entered/ answer already exists in this question.");
		} else
			System.out.println("The number entered is invalid.");
		return correct;
	}

	public static int countRepositories(Connection connection) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT COUNT(*) FROM Repository";
		try {
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				return resultSet.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionToDataBase.closePreparedStatement(preparedStatement);
			ConnectionToDataBase.closeResultSet(resultSet);
		}
		return -1;
	}

	public static boolean addRepositoryToSystemRepository(Connection connection, String profession) {
		// Output: The function returns true if the new repository add to the ArrayList
		// of repository, else returns false.
		PreparedStatement preparedStatement = null;
		String sql = "INSERT INTO Repository (Profession) VALUES (?)";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, profession);
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

	public static String toStringAllRepositories(Connection connection) {
		// Output: The function returns the repository details.
		StringBuffer res = new StringBuffer("The list of the professions is -");
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT Profession FROM Repository";
		int i = 1;
		try {
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				res.append("\n" + i + ") " + resultSet.getString("Profession"));
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			ConnectionToDataBase.closePreparedStatement(preparedStatement);
			ConnectionToDataBase.closeResultSet(resultSet);
		}
		return res.toString();
	}

	public static String toStringRepository(Connection connection, int idRep) {
		// Output: The function returns all the details of the questions in the
		// repository and all the answers to the question.
		StringBuffer res = new StringBuffer(
				"This is the list of all the questions in the repository and the answers that belong to them: \n");
		int i = 1;
		String type = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT Question_Type FROM Question WHERE Repository_ID = ? ORDER BY Question_ID";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idRep);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				type = resultSet.getString("Question_Type");
				if (type.equals("Close_Question")) {
					res.append(i + ") " + CloseQuestions.toStringCloseQuestion(connection, i, idRep));
				} else if (type.equals("Open_Question"))
					res.append(i + ") " + OpenQuestion.toStringOpenQuestion(connection, i, idRep));
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			ConnectionToDataBase.closePreparedStatement(preparedStatement);
			ConnectionToDataBase.closeResultSet(resultSet);
		}
		return res.toString();
	}
}