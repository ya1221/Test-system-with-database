package Yahav_Yehoshua_Bariah;

import java.io.Serializable;
import java.util.ArrayList;

public class OpenQuestion extends Question implements Serializable {
	private AnswerToQuestion answer;

	public OpenQuestion(String content, eDifficulty difficulty) {
		super(content, difficulty);
		this.answer = null;
	}

	public OpenQuestion(String content) {
		super(content);
		this.answer = null;
	}

	public OpenQuestion(Question que) {
		super(que);
		this.answer = null;
	}

	public AnswerToQuestion getAnswer() {
		// Output: The function returns the object of the question's answer.
		return this.answer;
	}

	public boolean addAnswerToQuestion(AnswerToQuestion ans) {
		// Input: Object of relation type of answer to the question.
		// Output: The function adds the answer to the question.
		this.answer = ans;
		return true;
	}

	public boolean deleteAns(int numAns) {
		// Input: number of answer.
		// Output: The function deletes the answer.
		if (numAns == 1) {
			this.answer = null;
			return true;
		}
		return false;
	}

	public boolean equals(Object other) {
		// Input: Object
		// Output: The function returns true if the objects are identical, else it
		// returns false.
		if (!(other instanceof OpenQuestion))
			return false;
		return super.equals(other);
	}

	public String showCorrectAnswer() {
		// Output: The function returns the content of the correct answer of the
		// question.
		StringBuffer res = new StringBuffer();
		if (this.answer != null)
			res.append("\t" + this.answer.getAnswer().getContent() + "\n");
		return res.toString();
	}

	public String toString() {
		// Output: The function returns the content of the question and all the answers
		// of the question.
		StringBuffer res = new StringBuffer(super.toString());
		if (this.answer != null)
			res.append("\t1) " + this.answer.toString() + "\n");
		return res.toString();
	}
}
