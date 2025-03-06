package Yahav_Yehoshua_Bariah;

import java.io.Serializable;
import java.util.Objects;

public class AnswerToQuestion implements Serializable {
	private Answer answer;
	private boolean correctAns;

	public AnswerToQuestion(Answer answer, boolean correctAns) {
		this.answer = answer;
		this.correctAns = correctAns;
	}

	public Answer getAnswer() {
		// Output: The function returns an object of type answer.
		return this.answer;
	}

	public boolean getCorrectAns() {
		// Output: The function returns the truth value of the answer in the context of
		// the question.
		return this.correctAns;
	}

	public boolean equals(Object other) {
		// Input: Object
		// Output: The function returns true if the objects are identical, else it
		// returns false.
		if (!(other instanceof AnswerToQuestion))
			return false;
		AnswerToQuestion ans = (AnswerToQuestion) other;
		return this.answer.equals(ans.answer) && this.correctAns == ans.correctAns;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.answer.getContent());
	}

	public String toString() {
		// Output: The function returns the content of the answer and the truth value of
		// the answer in the context of the question.
		StringBuffer res = new StringBuffer(this.answer.toString() + " -> " + this.correctAns);
		return res.toString();
	}
}
