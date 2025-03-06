package exceptions;

public class MoreThanMaxQuestionsException extends Exception {
	public MoreThanMaxQuestionsException(int max) {
		super("The exem can have up to " + max + " questions.");
	}
}