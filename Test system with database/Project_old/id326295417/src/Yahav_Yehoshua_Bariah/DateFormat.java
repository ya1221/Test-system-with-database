package Yahav_Yehoshua_Bariah;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
	private final String format = "yyyy_MM_dd_hh_mm";

	public String dateFormat() {
		// Output: The function returns the current specific date.
		SimpleDateFormat dateF = new SimpleDateFormat(this.format);
		Date date = new Date();
		return dateF.format(date);
	}
}
