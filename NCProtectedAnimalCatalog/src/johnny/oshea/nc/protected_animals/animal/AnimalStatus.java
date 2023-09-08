package johnny.oshea.nc.protected_animals.animal;

import johnny.oshea.nc.protected_animals.animal.AnimalStatus.RedListStatus;

public class AnimalStatus {

	public enum RedListStatus {
		ENDANGERED, VULNERABLE, CRITICALLY_ENDANGERED, LEAST_CONCERN, NEAR_THREATENED, DATA_DEFICIENT, EXTINCT
	}

	public static AnimalStatus fromString(String statusString) {
	    switch (statusString) {
	        case "Endangered":
	            return new AnimalStatus(RedListStatus.ENDANGERED);
	        case "Vulnerable":
	            return new AnimalStatus(RedListStatus.VULNERABLE);
	        case "Critically Endangered":
	            return new AnimalStatus(RedListStatus.CRITICALLY_ENDANGERED);
	        case "Least Concern":
	            return new AnimalStatus(RedListStatus.LEAST_CONCERN);
	        case "Near Threatened":
	            return new AnimalStatus(RedListStatus.NEAR_THREATENED);
	        case "Data Deficient":
	            return new AnimalStatus(RedListStatus.DATA_DEFICIENT);
	        case "Extinct":
	            return new AnimalStatus(RedListStatus.EXTINCT);
	        default:
	            throw new IllegalArgumentException("Invalid status string: " + statusString);
	    }
	}

	private RedListStatus redListStatus;
	
	public AnimalStatus(RedListStatus redListStatus) {
		this.redListStatus = redListStatus;
	}

	public RedListStatus getRedListStatus() {
		return redListStatus;
	}

	public void setRedListStatus(RedListStatus redListStatus) {
		this.redListStatus = redListStatus;
	}
	
	@Override
	public String toString() {
		String[] words = getRedListStatus().name().toLowerCase().replace("_", " ").split(" ");
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].substring(0, 1).toUpperCase() + words[i].substring(1);
        }
        return String.join(" ", words);
	}
}
