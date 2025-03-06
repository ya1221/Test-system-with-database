package Yahav_Yehoshua_Bariah;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public interface Examable {
	void createExam(Repository r1) throws FileNotFoundException;

	public ArrayList<Question> getQuestionArr();
}
