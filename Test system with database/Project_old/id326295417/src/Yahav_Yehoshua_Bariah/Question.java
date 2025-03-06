package Yahav_Yehoshua_Bariah;

import java.io.Serializable;
import java.util.Objects;

public abstract class Question implements Serializable {
	public enum eDifficulty {
		Hard, Medium, Easy
	}

	public static int counter;
	protected int counterQuestion;
	protected eDifficulty difficulty;
	protected String content;

	public Question(String content, eDifficulty difficulty) {
		this.counterQuestion = ++this.counter;
		this.difficulty = difficulty;
		this.content = content;
	}

	public Question(String content) {
		this.counterQuestion = 0;
		this.difficulty = null;
		this.content = content;
	}

	public Question(Question que) {
		this.counterQuestion = que.getCounterQuestion();
		this.difficulty = que.getDifficulty();
		this.content = que.getContent();
	}

	public void setCounter(int count) {
		// Input: The value of the counter of the question class.
		this.counter = count;
	}

	public int getCounterQuestion() {
		// Output: The function returns the amount of the question.
		return this.counterQuestion;
	}

	public String getContent() {
		// Output: The function returns the content of the question.
		return this.content;
	}

	public eDifficulty getDifficulty() {
		// Output: The function returns the difficulty level of the question.
		return this.difficulty;
	}

	public abstract boolean addAnswerToQuestion(AnswerToQuestion ans);
	// Input: Object of relation type of answer to the question.

	public abstract boolean deleteAns(int numAns);
	// Input: number of answer.

	public boolean equals(Object other) {
		// Input: Object
		// Output: The function returns true if the objects are identical, else it
		// returns false.
		if (!(other instanceof Question))
			return false;
		Question que = (Question) other;
		return this.content.equals(que.content);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.content);
	}

	public abstract String showCorrectAnswer();
	// Output: The function returns the content of the correct answers of the
	// question.

	public String listAnswersForExem() {
		// Output: The function returns the content of the question and all the answers
		// of the question.
		StringBuffer res = new StringBuffer(this.content + "\n");
		return res.toString();
	}

	public String toString() {
		// Output: The function returns the question details and all the answers to the
		// question.
		StringBuffer res = new StringBuffer(
				"Serial (" + this.counterQuestion + ") " + this.content + " - difficulty level -> "
						+ this.difficulty.name() + " - question type -> " + getClass().getSimpleName() + "\n");
		return res.toString();
	}
}
