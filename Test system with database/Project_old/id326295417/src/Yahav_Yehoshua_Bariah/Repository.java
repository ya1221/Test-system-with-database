package Yahav_Yehoshua_Bariah;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Scanner;

import exceptions.LessThanMinAnswerException;

public class Repository implements Serializable {
	private String profession;
	private ArrayList<Question> arrQuestions;
	private ArrayList<Answer> arrAnswers;
	private final int maxOfQuestion = 10;

	public Repository(String profession) {
		this.profession = profession;
		this.arrQuestions = new ArrayList<>();
		this.arrAnswers = new ArrayList<>();
	}

	public ArrayList<Question> getArrQuestion() {
		// Output: The function returns the ArrayList of the Question.
		return this.arrQuestions;
	}

	public ArrayList<Answer> getArrAnswer() {
		// Output: The function returns the ArrayList of the Answer.
		return this.arrAnswers;
	}

	public String getProfession() {
		// Output: The function returns the profession of the repository.
		return this.profession;
	}

	public String getTypeOfQuestion(int numQue) {
		// Input: number of question.
		// Output: The function returns the question type (Open question/Close
		// questions).
		return this.arrQuestions.get(numQue - 1).getClass().getSimpleName();
	}

	public boolean addAnswerToRepository(String ans) {
		// Input: String of answers.
		// Output: The function adds the answer to the repository (array) and returns
		// true if the answer does not already exist, else it returns false.
		Answer newAnswer = new Answer(ans);
		if (this.arrAnswers.contains(newAnswer)) {
			newAnswer = null;
			return false;
		}
		this.arrAnswers.add(newAnswer);
		return true;

	}

	public String showAllAnswer() {
		// Output: The function returns all the answers in the repository.
		StringBuffer res = new StringBuffer("This is the list of all the answers that exist in the repository:\n");
		Iterator<?> it = this.arrAnswers.iterator();
		int i = 1;
		while (it.hasNext()) {
			res.append(i + ") " + it.next() + "\n");
			i++;
		}
		return res.toString();
	}

	public boolean addAnswerToQuestion(int numQue, int numAns, boolean correctAns) {
		// Input: Number of question, number of answer, the truth value of the answer.
		// Output: The function adds the answer to the question and returns true if
		// answer does not already exist, if all the entered values ​​are correct, else
		// it returns false.
		AnswerToQuestion answer = new AnswerToQuestion(this.arrAnswers.get(numAns - 1), correctAns);
		if (!this.arrQuestions.get(numQue - 1).addAnswerToQuestion(answer)) {
			answer = null;
			return false;
		}
		return true;
	}

	public boolean addAnswerToQuestionInRepository(Scanner input, int numQue) {
		// Input: Scanner object, number of question.
		// Output: The function adds the answer to the question and returns true if the
		// answer does not already exist, if there are not 10 answers to the question
		// and if all the entered values ​​are correct, else it returns false and
		// prints a message about it.
		int numAns;
		String correctAns;
		boolean correct = false;
		System.out.println(showAllAnswer()
				+ "\nEnter the answer number you want to add to the question, or enter 0 to return to the main menu.");
		numAns = input.nextInt();
		if (numAns == 0)
			correct = true;
		else if (1 <= numAns && numAns <= this.arrAnswers.size()) {
			correct = true;
			if (getTypeOfQuestion(numQue).equals("CloseQuestions")) {
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
			}
			correct = addAnswerToQuestion(numQue, numAns, correct);
			if (!correct)
				System.out.println(
						"An incorrect value was entered/ answer already exists in this question/ this question already has 10 answers associated with it.");
		} else
			System.out.println("The number entered is invalid.");
		return correct;
	}

	public boolean addQuestion(String que, Question.eDifficulty difficulty, boolean typeQue) {
		// Input: Number of question.
		// Output: The function adds the question to the repository (array) and returns
		// true if the answer does not already exist, else it returns false.
		/* Question newQuestion = new OpenQuestion(que); */
		Question newQuestion = new OpenQuestion(que);
		if (this.arrQuestions.contains(newQuestion)) {
			newQuestion = null;
			return false;
		}
		if (typeQue)
			newQuestion = new CloseQuestions(que, difficulty);
		else
			newQuestion = new OpenQuestion(que, difficulty);
		this.arrQuestions.add(newQuestion);
		return true;
	}

	public boolean deleteAnswerFromQuestion(int numQue, int numAns) {
		// Input: Number of question, number of answer.
		// Output: The function deletes the answer from the array of answers in the
		// question and returns true if the values ​​are correct, else it returns false.
		if (1 <= numQue && numQue <= this.arrQuestions.size())
			return this.arrQuestions.get(numQue - 1).deleteAns(numAns);
		return false;
	}

	public boolean deleteQuestion(int numQue) {
		// Input: Number of question.
		// Output: The function returns true if the number is correct and the question
		// has been deleted, else it returns false.
		if (1 <= numQue && numQue <= this.arrQuestions.size()) {
			this.arrQuestions.remove(numQue - 1);
			return true;
		}
		return false;
	}

	public boolean equals(Object other) {
		// Input: Object
		// Output: The function returns true if the objects are identical, else it
		// returns false.
		if (!(other instanceof Repository))
			return false;
		Repository rep = (Repository) other;
		return this.profession.equals(rep.profession);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.profession);
	}

	public String listQuestionAndAnswersForExem() {
		// Output: The function returns all the questions in the repository and the
		// answers entered to the questions.
		StringBuffer res = new StringBuffer(
				"This is the list of all the questions in the repository and the answers that belong to them: \n");
		Iterator<?> it = this.arrQuestions.iterator();
		int i = 1;
		Question que;
		while (it.hasNext()) {
			que = (Question) it.next();
			res.append(i + ") " + que.listAnswersForExem());
			i++;
		}
		return res.toString();
	}

	public String toString() {
		// Output: The function returns all the details of the questions in the
		// repository and all the answers to the question.
		StringBuffer res = new StringBuffer(
				"This is the list of all the questions in the repository and the answers that belong to them: \n");
		Iterator<?> it = this.arrQuestions.iterator();
		int i = 1;
		while (it.hasNext()) {
			res.append(i + ") " + it.next());
			i++;
		}
		return res.toString();
	}
}