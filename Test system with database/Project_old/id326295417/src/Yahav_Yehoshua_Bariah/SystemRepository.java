package Yahav_Yehoshua_Bariah;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

@SuppressWarnings("serial")
public class SystemRepository implements Serializable {
	private ArrayList<Repository> arrRepositories;

	public SystemRepository() {
		this.arrRepositories = new ArrayList<>();
	}

	public ArrayList<Repository> getArrRepository() {
		// Output: The function returns the ArrayList of the Repository.
		return this.arrRepositories;
	}

	public void counterValue() {
		// Output: The function updates the static counter in the questions.
		int count = 0;
		int j = -1;
		for (int i = 0; i < this.arrRepositories.size(); i++)
			if ((this.arrRepositories.get(i).getArrQuestion().size() != 0) && (this.arrRepositories.get(i)
					.getArrQuestion().get(this.arrRepositories.get(i).getArrQuestion().size() - 1)
					.getCounterQuestion() > count)) { // if there is question in the repository and the value counter of
														// the last question in the repository bigger than the variable
														// "count"
				count = this.arrRepositories.get(i).getArrQuestion()
						.get(this.arrRepositories.get(i).getArrQuestion().size() - 1).getCounterQuestion();
				j = i;
			}
		if (j != -1)
			this.arrRepositories.get(j).getArrQuestion().get(this.arrRepositories.get(j).getArrQuestion().size() - 1)
					.setCounter(count);
	}

	public Repository getRepository(int numRep) {
		// Input: number of repository.
		// Output: The function returns repository.
		if (0 < numRep && numRep <= this.arrRepositories.size())
			return arrRepositories.get(numRep - 1);
		else
			return null;
	}

	public boolean addRepositoryToSystemRepository(String profession) {
		// Input: string of profession of repository.
		// Output: The function returns true if the new repository add to the ArrayList
		// of repository, else returns false.
		Repository newRepository = new Repository(profession);
		if (this.arrRepositories.contains(newRepository)) {
			newRepository = null;
			return false;
		}
		this.arrRepositories.add(newRepository);
		return true;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.arrRepositories.size());
	}

	public String toString() {
		// Output: The function returns the repository details.
		StringBuffer res = new StringBuffer("The list of the professions is -");
		for (int i = 0; i < this.arrRepositories.size(); i++)
			res.append("\n" + (i + 1) + ") " + this.arrRepositories.get(i).getProfession());
		return res.toString();
	}
}
