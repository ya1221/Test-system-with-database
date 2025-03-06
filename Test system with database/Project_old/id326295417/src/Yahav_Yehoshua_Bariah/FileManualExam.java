package Yahav_Yehoshua_Bariah;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileManualExam implements FileExemable {
	public void createFileE(ArrayList<Question> arr) throws FileNotFoundException {
		// Input: Arraylist of Question for the test.
		// Output: The function writes to a file the content of all the questions, and
		// their answers and for close questions the answers - None of the above, There
		// is more than one correct answer.
		DateFormat date = new DateFormat();
		File fileE = new File("exam_" + date.dateFormat());
		PrintWriter pwE = new PrintWriter(fileE);
		CloseQuestions temp;
		for (int i = 0; i < arr.size(); i++) {
			if (arr.get(i) instanceof CloseQuestions) {
				temp = (CloseQuestions) arr.get(i);
				pwE.println(temp.listAnswersForExem() + "\t" + (temp.getArrAns().size() + 1) + ") None of the above\n\t"
						+ (temp.getArrAns().size() + 2) + ") There is more than one correct answer" + "\n");
			} else {
				pwE.println(arr.get(i).listAnswersForExem());
			}
		}

		pwE.close();
	}

	public void createFileS(ArrayList<Question> arr) throws FileNotFoundException {
		// Input: Arraylist of Question for the test.
		// Output: The function writes to the file the content of the questions and
		// their correct answer.
		DateFormat date = new DateFormat();
		File fileS = new File("solution_" + date.dateFormat());
		PrintWriter pwS = new PrintWriter(fileS);
		for (int i = 0; i < arr.size(); i++) {
			if (arr.get(i) instanceof CloseQuestions) {
				CloseQuestions temp = (CloseQuestions) arr.get(i);
				pwS.println(temp.getContent());
				if (temp.getCountCorrectAnswer() == 1)
					pwS.println(temp.showCorrectAnswer());
				else if (temp.getCountCorrectAnswer() == 0)
					pwS.println("\tNone of the above\n");
				else
					pwS.println("\tThere is more than one correct answer\n");
			} else {
				OpenQuestion temp = (OpenQuestion) arr.get(i);
				pwS.println(temp.getContent() + "\n" + temp.showCorrectAnswer());
			}
		}
		pwS.close();
	}
}
