package Yahav_Yehoshua_Bariah;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import exceptions.MoreThanMaxQuestionsException;

public class AutomaticExam {
	private static Scanner input = new Scanner(System.in);

	public static int createExam(Connection connection, int idRep) throws FileNotFoundException {
		// Output: The function creates a file of a test and a file of answers -
		// according to the questions and answers that are selected and their
		// connections to the questions.
		int idExa = -1;
		ArrayList<Integer> arrNumQueExem = new ArrayList<>();
		int countQue = Repository.countInRepository(connection, "Question", idRep);
		if (countQue != 0) {
			System.out.println("How many questions will there be on the exam?");
			int amountOfQuestions = input.nextInt();
			try {
				Check.checkIfMoreThanMaxQuestions(amountOfQuestions);
				if (-1 < amountOfQuestions && amountOfQuestions <= countQue) {
					idExa = Exam.addExam(connection, "Automatic", idRep);
					Random rand = new Random();
					int numQue, numAns, countAns, correctNumRand;
					boolean haveTrue, correct;
					for (int i = 0; i < amountOfQuestions; i++) {
						do {
							numQue = rand.nextInt(countQue) + 1;
						} while (arrNumQueExem.contains(numQue));
						if (Question.getTypeOfQuestion(connection, numQue, idRep).equals("Close_Question")) {
							haveTrue = false;
							countAns = 0;
							do {
								correct = false;
								numAns = rand.nextInt(Repository.countInRepository(connection, "Answer", idRep)) + 1;
								correctNumRand = rand.nextInt(2);// deceide if true
								if (correctNumRand == 1 && !haveTrue) {
									correct = true;
									haveTrue = true;
								}
								if (Exam.addQuestionToAutomatic(connection, idExa, numQue, numAns, correct, idRep)) {
									countAns++;
									correct = true;
								} else
									correct = false;
							} while (!correct || countAns < Check.getMinOfAnswers());
						} else {
							do {
								numAns = rand.nextInt(Repository.countInRepository(connection, "Answer", idRep)) + 1;
							} while (!Exam.addQuestionToAutomatic(connection, idExa, numQue, numAns, false, idRep));
						}
						arrNumQueExem.add(numQue);
					}
				} else
					System.out.println("The amount of questions is incorrect, there are " + countQue
							+ " questions in the repository, please add new questions to the repository or enter a valid number.");
			} catch (MoreThanMaxQuestionsException e) {
				System.out.println(e.getMessage());
			}
		} else
			System.out.println("There is no question in the repository");
		return idExa;
	}
}
