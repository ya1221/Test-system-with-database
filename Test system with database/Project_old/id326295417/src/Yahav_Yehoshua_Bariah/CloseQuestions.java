package Yahav_Yehoshua_Bariah;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import exceptions.LessThanMinAnswerException;

public class CloseQuestions extends Question implements Serializable {
	private ArrayList<AnswerToQuestion> arrAns;
	private final int MaxOfAnswers = 10;
	private int countCorrectAnswer;

	public CloseQuestions(String content, eDifficulty difficulty) {
		super(content, difficulty);
		this.arrAns = new ArrayList<>();
		this.countCorrectAnswer = 0;
	}

	public CloseQuestions(Question que) {
		super(que);
		this.arrAns = new ArrayList<>();
		this.countCorrectAnswer = 0;
	}

	public ArrayList<AnswerToQuestion> getArrAns() {
		// Output: The function returns the ArrayList of the AnswerToQuestion.
		return this.arrAns;
	}

	public int getMaxOfAnswers() {
		// Output: The function returns the maximum number of answers that can be in the
		// question.
		return this.MaxOfAnswers;
	}

	public int getCountCorrectAnswer() {
		// Output: The function returns the number of correct answers.
		return this.countCorrectAnswer;
	}

	public boolean addAnswerToQuestion(AnswerToQuestion ans) {
		// Input: Object of relation type of answer to the question.
		// Output: The function adds the answer to the array of answers of the question.
		if ((this.arrAns.size() != this.MaxOfAnswers) && !this.arrAns.contains(ans)) {
			this.arrAns.add(ans);
			if (ans.getCorrectAns())
				this.countCorrectAnswer++;
			return true;
		}
		return false;
	}

	public boolean deleteAns(int numAns) {
		// Input: number of answer.
		// Output: The function deletes the answer from the array of the answers.
		if (1 <= numAns && numAns <= this.arrAns.size()) {
			this.arrAns.remove(numAns - 1);
			return true;
		}
		return false;
	}

	public boolean equals(Object other) {
		// Input: Object
		// Output: The function returns true if the objects are identical, else it
		// returns false.
		if (!(other instanceof CloseQuestions))
			return false;
		return super.equals(other);
	}

	public String showCorrectAnswer() {
		// Output: The function returns the content of the correct answer of the
		// question.
		StringBuffer res = new StringBuffer();
		Iterator<?> it = this.arrAns.iterator();
		AnswerToQuestion ans;
		while (it.hasNext()) {
			ans = (AnswerToQuestion) it.next();
			if (ans.getCorrectAns())
				res.append("\t" + ans.getAnswer().getContent() + "\n");
		}
		return res.toString();
	}

	public String listAnswersForExem() {
		// Output: The function returns the content of the question and all the answers
		// of the question.
		StringBuffer res = new StringBuffer(super.listAnswersForExem());
		Iterator<?> it = this.arrAns.iterator();
		int i = 1;
		AnswerToQuestion ans;
		while (it.hasNext()) {
			ans = (AnswerToQuestion) it.next();
			res.append("\t" + i + ") " + ans.getAnswer().getContent() + "\n");
			i++;
		}
		return res.toString();
	}

	public String toString() {
		// Output: The function returns the question details and all the answers to the
		// question.
		StringBuffer res = new StringBuffer(super.toString());
		Iterator<?> it = this.arrAns.iterator();
		int i = 1;
		while (it.hasNext()) {
			res.append("\t" + i + ") " + it.next() + "\n");
			i++;
		}
		return res.toString();
	}
}
