package Yahav_Yehoshua_Bariah;

import java.util.ArrayList;

import exceptions.LessThanMinAnswerException;
import exceptions.MoreThanMaxQuestionsException;

public class Check {
	private final int maxOfQuestions = 10;
	private final int MinOfAnswers = 4;

	public int getMinOfAnswers() {
		return this.MinOfAnswers;
	}

	public void checkIfMoreThanMaxQuestions(int num) throws MoreThanMaxQuestionsException {
		if (num > this.maxOfQuestions)
			throw new MoreThanMaxQuestionsException(this.maxOfQuestions);
	}

	public void checkAmountAnswerInQuestion(Question que) throws LessThanMinAnswerException {
		if (que instanceof CloseQuestions) {
			CloseQuestions temp = (CloseQuestions) que;
			if (temp.getArrAns().size() < this.MinOfAnswers)
				throw new LessThanMinAnswerException(this.MinOfAnswers);
		}
	}

	public boolean checkIfFound(Repository r1, ArrayList<Question> arr, int num) {
		// Input: Repository object, Arraylist of Question, number of question.
		// Output: The function returns true if the number of the question appears in
		// the array, else it returns false.
		for (int i = 0; i < arr.size(); i++)
			if (r1.getArrQuestion().get(num).equals(arr.get(i)))
				return true;
		return false;
	}
}
