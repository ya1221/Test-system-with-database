package Yahav_Yehoshua_Bariah;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Program {
	private static Scanner input = new Scanner(System.in);

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		int numFun;
		Connection connection = null;
		do {
			connection = ConnectionToDataBase.connectionUP();
			System.out.println(Repository.toStringAllRepositories(connection)
					+ "\nEnter the number of the function you want to perform."
					+ "\n1 - Create a test in a profession from the list." + "\n2 - Add a new profession to the list."
					+ "\n0 - Exit.");
			numFun = input.nextInt();
			switch (numFun) {
			case 1:
				RepositoryFunction(connection);
				break;
			case 2:
				addRepository(connection);
				break;
			case 0:
				System.out.println("Thank you, good day!");
				break;
			default:
				System.out.println("Invalid option");
				break;
			}
		} while (numFun != 0);
		ConnectionToDataBase.closeConnection(connection);
	}

	public static void RepositoryFunction(Connection connection) throws FileNotFoundException {
		if (Repository.countRepositories(connection) != 0) {
			int numFun;
			int idRep = 0;
			do {
				System.out.println(
						"Enter the profession's number from the list , or enter 0 to return to the main menu -");
				numFun = input.nextInt();
				if (0 < numFun && numFun <= Repository.countRepositories(connection)) {
					idRep = Repository.findId(connection, numFun);
				}
			} while (numFun != 0 && idRep == 0);

			if (numFun != 0) {
				do {
					System.out.println(
							"1 - Displaying all the questions in the repository and the answers entered to the questions.\n"
									+ "2 - Adding a new answer to the repository.\n"
									+ "3 - Adding an answer from the repository to an existing question.\n"
									+ "4 - Adding a new question to the repository.\n"
									+ "5 - Deleting an answer to a question from the repository.\n"
									+ "6 - Deleting a question from the repository.\n"
									+ "7 - Deleting an answer form the repository.\n" + "8 - Creating a exam.\n"
									+ "9 - Creating files for exam or delete exem.\n"
									+ "10 - Deleting the repository.\n" + "0 - Exit.\n"
									+ "Enter the number of the function you want to perform.");
					numFun = input.nextInt();
					switch (numFun) {
					case 1:
						System.out.println(Repository.toStringRepository(connection, idRep));
						break;
					case 2:
						addAnswerToRepository(connection, idRep);
						break;
					case 3:
						addAnswerToQuestion(connection, idRep);
						break;
					case 4:
						addQuestion(connection, idRep);
						break;
					case 5:
						deleteAnswerFromQuestion(connection, idRep);
						break;
					case 6:
						deleteQuestion(connection, idRep);
						break;
					case 7:
						deleteAnswer(connection, idRep);
						break;
					case 8:
						createExam(connection, idRep);
						break;
					case 9:
						showExamAndDelete(connection, idRep);
						break;
					case 10:
						Repository.deleteRepository(connection, idRep);
					case 0:
						break;
					default:
						System.out.println("Invalid option");
						break;
					}
				} while (numFun != 0);
			}
		} else
			System.out.println("There are no professions in the system, please enter a new profession.");
	}

	public static void addRepository(Connection connection) {
		String newString;
		do {
			System.out.println("Enter the new profession -");
			do {
				newString = input.nextLine();
			} while (newString.isEmpty());
		} while (!Repository.addRepositoryToSystemRepository(connection, newString));
	}

	public static void addAnswerToRepository(Connection connection, int idRep) {
		// Output: The function adds the answer to the repository.
		boolean correct;
		String newString;
		do {
			System.out.println("Enter the new answer -");
			do {
				newString = input.nextLine();
			} while (newString.isEmpty());
			correct = Answer.addAnswerToRepository(connection, newString, idRep);
			if (!correct)
				System.out.println("This answer already exists in the repository.");
		} while (!correct);
	}

	public static void addAnswerToQuestion(Connection connection, int idRep) {
		// Output: The function adds the answer.
		int countQue = Repository.countInRepository(connection, "Question", idRep);
		if (countQue != 0 && Repository.countInRepository(connection, "Answer", idRep) != 0) {
			int numQue, idQue;
			boolean correct = false;
			do {
				System.out.println(Repository.toStringRepository(connection, idRep)
						+ "\nEnter the number of the question, or enter 0 to return to the main menu.");
				numQue = input.nextInt();
				idQue = Question.findId(connection, numQue, idRep);
				if (idQue != -1) {
					if (CloseQuestions.getCountAnswer(connection, idQue, idRep) != CloseQuestions.MaxOfAnswers) {
						if (1 <= numQue && numQue <= countQue)
							correct = Repository.addAnswerToQuestionInRepository(input, numQue, connection, idRep);
					} else
						System.out.println("This question already has 10 answers associated with it.");
				}
			} while (!correct && numQue != 0);
		} else
			System.out.println("There are no questions or answers in the repository");
	}

	public static void addQuestion(Connection connection, int idRep) {
		// Output: The function adds the question to the repository (array).
		if (Repository.countInRepository(connection, "Question", idRep) != Repository.MaxOfQuestion) {
			boolean correct;
			String newString;
			String difficultyQueString;
			Question.eDifficulty difficultyQue;
			boolean typeQue;
			do {
				System.out.println("Enter the new question.");
				do {
					newString = input.nextLine();
				} while (newString.isEmpty());
				do {
					System.out.println("Enter the difficulty level of the question <Hard, Medium, Easy>");
					difficultyQueString = input.next(); // Receiving the difficulty level of the new question from the
														// user.
					correct = difficultyQueString.equals("Hard") || difficultyQueString.equals("Medium")
							|| difficultyQueString.equals("Easy");
					if (!correct)
						System.out.println("The value is incorrect.");
				} while (!correct);
				difficultyQue = Question.eDifficulty.valueOf(difficultyQueString);
				System.out.println("The type of the new quetion is a close question <true/false>");
				typeQue = input.nextBoolean();
				if (typeQue)
					correct = Question.addQuestion(connection, newString, difficultyQue, idRep, "Close_Question");
				else
					correct = Question.addQuestion(connection, newString, difficultyQue, idRep, "Open_Question");
				if (!correct)
					System.out.println("This question already exists in the repository.");
			} while (!correct);
		} else
			System.out.println("This repository already has 10 questions associated with it.");
	}

	public static void deleteAnswerFromQuestion(Connection connection, int idRep) {
		// Output: The function deletes the answer from the question.
		if (Repository.countInRepository(connection, "Question", idRep) != 0
				&& Repository.countInRepository(connection, "Answer", idRep) != 0) {
			int numQue, numAns;
			boolean correct = true;
			String type = null;
			do {
				System.out.println(Repository.toStringRepository(connection, idRep)
						+ "\nEnter the number of the question, or enter 0 to return to the main menu.");
				numQue = input.nextInt();
				if (numQue != 0) {
					System.out.println("Enter the number of the answer, or enter 0 to return to the main menu.");
					numAns = input.nextInt();
					if (numAns != 0) {
						type = Question.getTypeOfQuestion(connection, numQue, idRep);
						if (type.equals("Close_Question"))
							correct = CloseQuestions.deleteAns(connection, numQue, numAns, idRep);
						else if (type.equals("Open_Question"))
							correct = OpenQuestion.deleteAns(connection, numQue, idRep);
						if (!correct)
							System.out.println("One of the values is incorrect.");
					}
				}
			} while (!correct);
		} else
			System.out.println("There are no questions or answers in the repository");
	}

	public static void deleteQuestion(Connection connection, int idRep) {
		// Output: The function deletes the question from the Repository.
		if (Repository.countInRepository(connection, "Question", idRep) != 0) {
			boolean correct = true;
			int numQue;
			do {
				System.out.println(Repository.toStringRepository(connection, idRep)
						+ "\nEnter the number of the question, or enter 0 to return to the main menu.");
				numQue = input.nextInt();
				if (numQue != 0) {
					correct = Question.deleteQuestion(connection, numQue, idRep);
					if (!correct)
						System.out.println("The value is incorrect.");
				}
			} while (!correct);
		} else
			System.out.println("There are no questions in the repository.");
	}

	public static void deleteAnswer(Connection connection, int idRep) {
		System.out.println(Repository.showAllAnswer(connection, idRep)
				+ "\nEnter the answer number you want to add to the question, or enter 0 to return to the main menu.");
		int numAns = input.nextInt();
		if (numAns != 0)
			if (Answer.deleteAnswerFromRepository(connection, numAns, idRep))
				System.out.println("The Answer was successfully deleted.");
			else
				System.out.println("The Answer is not deleted.");
	}

	public static void createExam(Connection connection, int idRep) throws FileNotFoundException {
		System.out.println("1) Manual exam\n" + "2) Automatic exam\n" + "0) Exit.\n"
				+ "Enter the number of the function you want to perform.");
		int idExa = -1, num = input.nextInt();
		switch (num) {
		case 1:
			idExa = ManualExam.createExam(connection, idRep);
			break;
		case 2:
			idExa = AutomaticExam.createExam(connection, idRep);
			break;
		case 0:
			break;
		default:
			System.out.println("Invalid option");
			break;
		}
		if (num == 1 || num == 2)
			if (idExa != -1)
				CreateFileEandSforExam(connection, idExa, idRep);
			else
				System.out.println("Can not create exam.");
	}

	public static void showExamAndDelete(Connection connection, int idRep) throws FileNotFoundException {
		System.out.println(Exam.showAllExam(connection, idRep));
		int numExa, idExa = -1;
		do {
			System.out.println("Enter the exam's number from the list , or enter 0 to return to the main menu -");
			numExa = input.nextInt();
			if (numExa != 0)
				idExa = Exam.findId(connection, numExa, idRep);
		} while (idExa == -1 && numExa != 0);
		if (numExa != 0) {
			System.out.println("1) Create files exam\n" + "2) Delete exam\n" + "0) Exit.\n"
					+ "Enter the number of the function you want to perform.");
			int num = input.nextInt();
			switch (num) {
			case 1:
				CreateFileEandSforExam(connection, idExa, idRep);
				break;
			case 2:
				Exam.deleteExam(connection, idExa);
			case 0:
				break;
			default:
				System.out.println("Invalid option");
				break;
			}
		}
	}

	public static void CreateFileEandSforExam(Connection connection, int idExa, int idRep)
			throws FileNotFoundException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT * FROM Exam WHERE Exam_ID = ? AND Repository_ID = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idExa);
			preparedStatement.setInt(2, idRep);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				if (resultSet.getString("Exam_Type").equals("Manual")) {
					FileManualExam.createFileE(connection, idExa, idRep);
					FileManualExam.createFileS(connection, idExa, idRep);
				} else if (resultSet.getString("Exam_Type").equals("Automatic")) {
					FileAutomaticExam.createFileE(connection, idExa, idRep);
					FileAutomaticExam.createFileS(connection, idExa, idRep);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionToDataBase.closePreparedStatement(preparedStatement);
			ConnectionToDataBase.closeResultSet(resultSet);
		}
	}
}