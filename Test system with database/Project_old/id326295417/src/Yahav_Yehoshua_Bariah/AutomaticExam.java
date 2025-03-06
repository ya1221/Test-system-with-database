package Yahav_Yehoshua_Bariah;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import exceptions.MoreThanMaxQuestionsException;

public class AutomaticExam implements Examable {
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
			System.out.println("How many questions will there be on the exam?");
			int amountOfQuestions = input.nextInt();
			try {
				Check check = new Check();
				check.checkIfMoreThanMaxQuestions(amountOfQuestions);
				if (-1 < amountOfQuestions && amountOfQuestions <= r1.getArrQuestion().size()) {
					Random rand = new Random();
					int numQue, numAns, countAns, correctNumRand;
					boolean haveTrue, correct, found;
					Question que;
					for (int i = 0; i < amountOfQuestions; i++) {
						do {
							numQue = rand.nextInt(r1.getArrQuestion().size());
							found = check.checkIfFound(r1, this.arrQuestionExem, numQue);
							if (!found) {
								if (r1.getArrQuestion().get(numQue) instanceof CloseQuestions) {
									que = new CloseQuestions(r1.getArrQuestion().get(numQue));
									haveTrue = false;
									countAns = 0;
									do {
										correct = false;
										numAns = rand.nextInt(r1.getArrAnswer().size());
										correctNumRand = rand.nextInt(2);// deceide if true
										if (correctNumRand == 1 && !haveTrue) {
											correct = true;
											haveTrue = true;
										}
										AnswerToQuestion answer = new AnswerToQuestion(r1.getArrAnswer().get(numAns),
												correct);
										if (que.addAnswerToQuestion(answer)) {
											countAns++;
											correct = true;
										} else
											correct = false;
									} while (!correct || countAns < check.getMinOfAnswers());
								} else
									que = new OpenQuestion(r1.getArrQuestion().get(numQue));
								this.arrQuestionExem.add(que);
							}
						} while (found);
					}
				} else
					System.out.println("The amount of questions is incorrect, there are " + r1.getArrQuestion().size()
							+ " questions in the repository, please add new questions to the repository or enter a valid number.");
			} catch (MoreThanMaxQuestionsException e) {
				System.out.println(e.getMessage());
			}
		} else
			System.out.println("There is no question in the repository");
	}
}
