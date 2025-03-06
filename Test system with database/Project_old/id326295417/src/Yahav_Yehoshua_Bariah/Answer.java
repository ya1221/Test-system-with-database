package Yahav_Yehoshua_Bariah;

import java.io.Serializable;
import java.util.Objects;

public class Answer implements Serializable {
	private String content;

	public Answer(String content) {
		this.content = content;
	}

	public String getContent() {
		// Output: The function returns the content of the answer.
		return this.content;
	}

	public boolean equals(Object other) {
		// Input: Object
		// Output: The function returns true if the objects are identical, else it
		// returns false.
		if (!(other instanceof Answer))
			return false;
		Answer ans = (Answer) other;
		return this.content.equals(ans.content);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.content);
	}

	public String toString() {
		// Output: The function returns the content of the answer.
		StringBuffer res = new StringBuffer(this.content);
		return res.toString();
	}
}
