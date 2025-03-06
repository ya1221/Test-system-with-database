package Yahav_Yehoshua_Bariah;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Executable;
import java.util.Scanner;

public class Program {
	private static Scanner input = new Scanner(System.in);

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {

		ObjectInputStream inFile = new ObjectInputStream(new FileInputStream("SystemExem.dat"));
		SystemRepository s1 = (SystemRepository) inFile.readObject();
		s1.counterValue();
		int numFun;
		do {
			System.out.println(s1.toString() + "\nEnter the number of the function you want to perform."
					+ "\n1 - Create a test in a profession from the list." + "\n2 - Add a new profession to the list."
					+ "\n0 - Exit.");
			numFun = input.nextInt();
			switch (numFun) {
			case 1:
				RepositoryFunction(s1);
				break;
			case 2:
				addRepository(s1);
				break;
			case 0:
				System.out.println("Thank you, good day!");
				break;
			default:
				System.out.println("Invalid option");
				break;
			}
		} while (numFun != 0);

		ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream("SystemExem.dat"));
		outFile.writeObject(s1);
		outFile.close();
		inFile.close();
	}

	public static void RepositoryFunction(SystemRepository s1) throws FileNotFoundException {
		if (!s1.getArrRepository().isEmpty()) {
			int numFun;
			Repository r1 = null;
			do {
				System.out.println(
						"Enter the profession's number from the list , or enter 0 to return to the main menu -");
				numFun = input.nextInt();
				if (numFun != 0) {
					r1 = s1.getRepository(numFun);
				}
			} while (numFun != 0 && r1 == null);

			if (numFun != 0) {

				do {
					System.out.println(
							"1 - Displaying all the questions in the repository and the answers entered to the questions.\n"
									+ "2 - Adding a new answer to the repository.\n"
									+ "3 - Adding an answer from the repository to an existing question.\n"
									+ "4 - Adding a new question to the repository.\n"
									+ "5 - Deleting an answer to a question from the repository.\n"
									+ "6 - Deleting a question from the repository.\n" + "7 - Creating a exam.\n"
									+ "0 - Exit.\n" + "Enter the number of the function you want to perform.");
					numFun = input.nextInt();
					switch (numFun) {
					case 1:
						System.out.println(r1.toString());
						break;
					case 2:
						addAnswerToRepository(r1);
						break;
					case 3:
						addAnswerToQuestion(r1);
						break;
					case 4:
						addQuestion(r1);
						break;
					case 5:
						deleteAnswerFromQuestion(r1);
						break;
					case 6:
						deleteQuestion(r1);
						break;
					case 7:
						createExam(r1);
						break;
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

	public static void addRepository(SystemRepository s1) {
		String newString;
		do {
			System.out.println("Enter the new profession -");
			do {
				newString = input.nextLine();
			} while (newString.isEmpty());
		} while (!s1.addRepositoryToSystemRepository(newString));
	}

	public static void addAnswerToRepository(Repository r1) {
		// Input: Repository object.
		// Output: The function adds the answer to the repository.
		boolean correct;
		String newString;
		do {
			System.out.println("Enter the new answer -");
			do {
				newString = input.nextLine();
			} while (newString.isEmpty());
			correct = r1.addAnswerToRepository(newString);
			if (!correct)
				System.out.println("This answer already exists in the repository.");
		} while (!correct);
	}

	public static void addAnswerToQuestion(Repository r1) {
		// Input: Repository object.
		// Output: The function adds the answer.
		if (!r1.getArrQuestion().isEmpty() && !r1.getArrAnswer().isEmpty()) {
			int numQue;
			boolean correct = true;
			do {
				System.out.println(
						r1.toString() + "\nEnter the number of the question, or enter 0 to return to the main menu.");
				numQue = input.nextInt();
				if (numQue != 0 && 1 <= numQue && numQue <= r1.getArrQuestion().size())
					correct = r1.addAnswerToQuestionInRepository(input, numQue);
			} while (!correct && numQue != 0);
		} else
			System.out.println("There are no questions or answers in the repository");
	}

	public static void addQuestion(Repository r1) {
		// Input: Repository object.
		// Output: The function adds the question to the repository (array).
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
				difficultyQueString = input.next(); // Receiving the difficulty level of the new question from the user.
				correct = difficultyQueString.equals("Hard") || difficultyQueString.equals("Medium")
						|| difficultyQueString.equals("Easy");
				if (!correct)
					System.out.println("The value is incorrect.");
			} while (!correct);
			difficultyQue = Question.eDifficulty.valueOf(difficultyQueString);
			System.out.println("The type of the new quetion is a close question <true/false>");
			typeQue = input.nextBoolean();
			correct = r1.addQuestion(newString, difficultyQue, typeQue);
			if (!correct)
				System.out.println("This question already exists in the repository.");
		} while (!correct);
	}

	public static void deleteAnswerFromQuestion(Repository r1) {
		// Input: Repository object.
		// Output: The function deletes the answer from the question.
		if (!r1.getArrQuestion().isEmpty() && !r1.getArrAnswer().isEmpty()) {
			int numQue, numAns;
			boolean correct = true;
			do {
				System.out.println(
						r1.toString() + "\nEnter the number of the question, or enter 0 to return to the main menu.");
				numQue = input.nextInt();
				if (numQue != 0) {
					System.out.println("Enter the number of the answer, or enter 0 to return to the main menu.");
					numAns = input.nextInt();
					if (numAns != 0) {
						correct = r1.deleteAnswerFromQuestion(numQue, numAns);
						if (!correct)
							System.out.println("One of the values is incorrect.");
					}
				}
			} while (!correct);
		} else
			System.out.println("There are no questions or answers in the repository");
	}

	public static void deleteQuestion(Repository r1) {
		// Input: Repository object.
		// Output: The function deletes the question from the Repository.
		if (!r1.getArrQuestion().isEmpty()) {
			boolean correct = true;
			int numQue;
			do {
				System.out.println(
						r1.toString() + "\nEnter the number of the question, or enter 0 to return to the main menu.");
				numQue = input.nextInt();
				if (numQue != 0) {
					correct = r1.deleteQuestion(numQue);
					if (!correct)
						System.out.println("The value is incorrect.");
				}
			} while (!correct);
		} else
			System.out.println("There are no questions in the repository.");
	}

	public static void createFileExam(Repository r1, Examable exam, FileExemable file) throws FileNotFoundException {
		exam.createExam(r1);
		file.createFileE(exam.getQuestionArr());
		file.createFileS(exam.getQuestionArr());
	}

	public static void createExam(Repository r1) throws FileNotFoundException {
		System.out.println("1) Manual exam\n" + "2) Automatic exam\n" + "0) Exit.\n"
				+ "Enter the number of the function you want to perform.");
		int num = input.nextInt();
		switch (num) {
		case 1:
			ManualExam ma = new ManualExam();
			FileManualExam fileMa = new FileManualExam();
			createFileExam(r1, ma, fileMa);
			break;
		case 2:
			AutomaticExam au = new AutomaticExam();
			FileAutomaticExam fileAu = new FileAutomaticExam();
			createFileExam(r1, au, fileAu);
			break;
		case 0:
			break;
		default:
			System.out.println("Invalid option");
			break;
		}
	}
}