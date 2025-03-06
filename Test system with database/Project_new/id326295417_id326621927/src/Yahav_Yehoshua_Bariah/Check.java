package Yahav_Yehoshua_Bariah;

import exceptions.MoreThanMaxQuestionsException;

public class Check {
	public static final int maxOfQuestions = 10;
	public static final int MinOfAnswers = 4;

	public static int getMinOfAnswers() {
		return MinOfAnswers;
	}

	public static void checkIfMoreThanMaxQuestions(int num) throws MoreThanMaxQuestionsException {
		if (num > maxOfQuestions)
			throw new MoreThanMaxQuestionsException(maxOfQuestions);
	}

	public static String extractErrorMessage(String message) {
		int startIndex = message.indexOf("ERROR:") + "ERROR:".length();
		int endIndex = message.indexOf("Where:");
		if (endIndex == -1)
			endIndex = message.length();
		return message.substring(startIndex, endIndex).trim();
	}
}
