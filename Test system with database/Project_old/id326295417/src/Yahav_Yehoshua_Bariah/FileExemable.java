package Yahav_Yehoshua_Bariah;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public interface FileExemable {
	public void createFileE(ArrayList<Question> arr) throws FileNotFoundException;

	public void createFileS(ArrayList<Question> arr) throws FileNotFoundException;
}
