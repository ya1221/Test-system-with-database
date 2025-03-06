package exceptions;

public class LessThanMinAnswerException extends Exception {
	public LessThanMinAnswerException(int min) {
		super("You cannot add a closed question that has less than " + min + " answers.");
	}
}
