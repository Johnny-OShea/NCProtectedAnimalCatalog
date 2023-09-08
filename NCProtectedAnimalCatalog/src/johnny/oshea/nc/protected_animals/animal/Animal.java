package johnny.oshea.nc.protected_animals.animal;

import johnny.oshea.nc.protected_animals.animal.Animal;

public class Animal implements Comparable<Animal> {

	private String name;
	private AnimalClass animalClass;
	private AnimalStatus animalStatus;
	private String estimatedRemain;
	private String information;
	private String funFacts;
	private String animalPicture;

	public Animal(String name, AnimalClass animalClass, AnimalStatus animalStatus, String estimatedRemain,
			String information, String funFacts, String animalPicture) {
		this.name = name;
		this.animalClass = animalClass;
		this.animalStatus = animalStatus;
		this.estimatedRemain = estimatedRemain;
		this.information = information;
		this.funFacts = funFacts;
		this.animalPicture = animalPicture;
	}

	public AnimalClass getAnimalClass() {
		return animalClass;
	}

	public void setAnimalClass(AnimalClass animalClass) {
		if (animalClass == null) {
			throw new IllegalArgumentException("Animal class cannot be null");
		}
		this.animalClass = animalClass;
	}

	/**
	 * This method gets the name for a specific animal.
	 * 
	 * @return name a String representing the name of an animal.
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null || name.equals("")) {
			throw new IllegalArgumentException("Name cannot be null or empty");
		}
		this.name = name;
	}

	public AnimalStatus getRedListStatus() {
		return animalStatus;
	}

	public void setRedListStatus(AnimalStatus animalStatus) {
		if (animalStatus == null) {
			throw new IllegalArgumentException("Red list status cannot be null or empty");
		}
		this.animalStatus = animalStatus;
	}

	public String getEstimatedRemain() {
		return estimatedRemain;
	}

	public void setEstimatedRemain(String estimatedRemain) {
		if (estimatedRemain == null || estimatedRemain.equals("")) {
			throw new IllegalArgumentException("Estimated remain cannot be null or empty");
		}
		this.estimatedRemain = estimatedRemain;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		if (information == null || information.equals("")) {
			throw new IllegalArgumentException("Information cannot be null or empty");
		}
		this.information = information;
	}

	public String getFunFacts() {
		return funFacts;
	}

	public void setFunFacts(String funFacts) {
		if (information == null || information.equals("")) {
			throw new IllegalArgumentException("Information cannot be null or empty");
		}
		this.funFacts = funFacts;
	}

	public String getFormattedFunFactsList() {
		String funFacts = getFunFacts();
		String[] facts = funFacts.split("!");
		String formattedFacts = "";
		for (int i = 0; i < facts.length; i++) {
			String[] words = facts[i].trim().split("\\s+");
			String formattedFact = (i + 1) + ") ";
			int charCount = 0;
			for (String word : words) {
				if (charCount + word.length() > 105) {
					formattedFact += "\n     " + word + " ";
					charCount = word.length() + 6;
				} else {
					formattedFact += word + " ";
					charCount += word.length() + 1;
				}
			}
			formattedFacts += formattedFact.trim() + "\n";
		}
		return "\n" + getName() + "\n" + formattedFacts.trim();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Name: ").append(name).append("\n");
		sb.append("Class: ").append(animalClass.toString()).append("\n");
		sb.append("Red List Status: ").append(animalStatus.toString()).append("\n");
		sb.append("Estimated Remain: ").append(estimatedRemain).append("\n");

		// Split the additional info into words
		String[] words = information.split(" ");
		int charCount = 0;
		for (String word : words) {
			int wordLen = word.length();
			// Insert new line if adding this word exceeds 110 characters
			if (charCount + wordLen > 110) {
				// Remove space before last word
				sb.deleteCharAt(sb.length() - 1);
				// start new line with next word
				sb.append("\n").append(word).append(" ");
				charCount = wordLen + 1;
			} else {
				sb.append(word).append(" ");
				charCount += wordLen + 1;
			}
		}
		sb.append("\n");
		return sb.toString() + "\n";
	}

	@Override
	public int compareTo(Animal other) {
		return this.name.compareTo(other.getName());
	}

	public String getAnimalPicture() {
		return animalPicture;
	}

	public void setAnimalPicture(String animalPicture) {
		if (animalPicture.equals("") || animalPicture == null) {
			throw new IllegalArgumentException("Invalid picture.");
		}
		this.animalPicture = animalPicture;
	}
}
