package Yahav_Yehoshua_Bariah;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import exceptions.LessThanMinAnswerException;
import exceptions.MoreThanMaxQuestionsException;

public class ManualExam implements Examable {
	private static Scanner input = new Scanner(System.in);
	private ArrayList<Question> arrQuestionExem = new ArrayList<>();

	public ArrayList<Question> getQuestionArr() {
		return this.arrQuestionExem;
	}

	@Override
	public void createExam(Repository r1) throws FileNotFoundException {
		// Input: Repository object.
		// Output: The function creates a file of a test and a file of answers -
		// according to the questions and answers that are selected and their
		// connections to the questions.
		if (!r1.getArrQuestion().isEmpty()) {
			if (!r1.getArrAnswer().isEmpty()) {
				System.out.println("How many questions will there be on the exam?");
				int amountOfQuestions = input.nextInt();
				try {
					Check check = new Check();
					check.checkIfMoreThanMaxQuestions(amountOfQuestions);
					if (-1 < amountOfQuestions && amountOfQuestions <= r1.getArrQuestion().size()) {
						int numQue;
						String correctAns;
						boolean correct = false, found = true;
						Question que;
						for (int i = 0; i < amountOfQuestions; i++) {
							do {
								System.out.println(r1.toString()
										+ "\nEnter the number of the question you want to add to the exam-");
								numQue = input.nextInt();
								try {
									if (1 <= numQue && numQue <= r1.getArrQuestion().size()) {
										found = check.checkIfFound(r1, this.arrQuestionExem, numQue - 1);
										que = r1.getArrQuestion().get(numQue - 1);
										check.checkAmountAnswerInQuestion(que);
										if (!found) {
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
												if (correct)
													r1.addAnswerToQuestionInRepository(input, numQue);
											} while (correct);
											this.arrQuestionExem.add(que);
										} else
											System.out.println("This question already exists in the test.");
									} else
										System.out.println("The number entered is invalid.");
								} catch (LessThanMinAnswerException e) {
									found = true;
									System.out.println(e.getMessage());
								}
							} while (found);
						}
					} else
						System.out.println("The amount of questions is incorrect, there are "
								+ r1.getArrQuestion().size()
								+ " questions in the repository, please add new questions to the repository or enter a valid number.");
				} catch (MoreThanMaxQuestionsException e) {
					System.out.println(e.getMessage());
				}
			} else
				System.out.println("There are no answers in the repository.");
		} else
			System.out.println("There is no question in the repository");
	}
}
