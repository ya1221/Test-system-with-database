package Yahav_Yehoshua_Bariah;

import java.sql.Connection;
import java.util.Scanner;

import exceptions.MoreThanMaxQuestionsException;

public class ManualExam {
	private static Scanner input = new Scanner(System.in);

	public static int createExam(Connection connection, int idRep) {
		// Output: The function creates a file of a test and a file of answers -
		// according to the questions and answers that are selected and their
		// connections to the questions.
		int idExa = -1;
		int countQue = Repository.countInRepository(connection, "Question", idRep);
		if (countQue != 0) {
			if (Repository.countInRepository(connection, "Answer", idRep) != 0) {
				System.out.println("How many questions will there be on the exam?");
				int amountOfQuestions = input.nextInt();
				try {
					Check.checkIfMoreThanMaxQuestions(amountOfQuestions);
					if (-1 < amountOfQuestions && amountOfQuestions <= countQue) {
						int numQue;
						String correctAns;
						boolean correct = false, found;
						idExa = Exam.addExam(connection, "Manual", idRep);
						for (int i = 0; i < amountOfQuestions; i++) {
							do {
								found = false;
								System.out.println(Repository.toStringRepository(connection, idRep)
										+ "\nEnter the number of the question you want to add to the exam-");
								numQue = input.nextInt();
								if (1 <= numQue && numQue <= countQue) {
									do {
										do {
											System.out.println(
													"Do you want to add an answer to the question? <true/false>");
											correctAns = input.next();
											if (correctAns.equals("true"))
												correct = true;
											else if (correctAns.equals("false"))
												correct = false;
											else
												System.out.println("The entered value is invalid.");
										} while (!correctAns.equals("true") && !correctAns.equals("false"));
										if (correct) {
											if (!Repository.addAnswerToQuestionInRepository(input, numQue, connection,
													idRep)) {
												correct = true;
												System.out.println("Can not add the answer to the question.");
											}
										}
									} while (correct);
									if (!Exam.addQuestion(connection, idExa, numQue, idRep))
										found = true;
								} else
									System.out.println("The number entered is invalid.");
							} while (found);
						}
					} else
						System.out.println("The amount of questions is incorrect, there are " + countQue
								+ " questions in the repository, please add new questions to the repository or enter a valid number.");
				} catch (MoreThanMaxQuestionsException e) {
					System.out.println(e.getMessage());
				}
			} else
				System.out.println("There are no answers in the repository.");
		} else
			System.out.println("There is no question in the repository");
		return idExa;
	}
}
