package johnny.oshea.nc.protected_animals.animal;

import johnny.oshea.nc.protected_animals.animal.AnimalClass.AnimalClassType;

public class AnimalClass {
	public enum AnimalClassType {
		MAMMALIA, AVES, REPTILIA, AMPHIBIA, ACTINOPTERYGII, BIVALVIA, CRUSTACEA, GASTROPODA
	}

	private static final AnimalClass MAMMAL = new AnimalClass(AnimalClassType.MAMMALIA, "Mammalia");
	private static final AnimalClass BIRD = new AnimalClass(AnimalClassType.AVES, "Aves");
	private static final AnimalClass REPTILE = new AnimalClass(AnimalClassType.REPTILIA, "Reptilia");
	private static final AnimalClass AMPHIBIAN = new AnimalClass(AnimalClassType.AMPHIBIA, "Amphibia");
	private static final AnimalClass FISH = new AnimalClass(AnimalClassType.ACTINOPTERYGII, "Actinopterygii");
	private static final AnimalClass INVERTEBRATE = new AnimalClass(AnimalClassType.BIVALVIA, "Bivalvia");
	private static final AnimalClass SHELLFISH = new AnimalClass(AnimalClassType.CRUSTACEA, "Crustacea");
	private static final AnimalClass CRAYFISH = new AnimalClass(AnimalClassType.GASTROPODA, "Gastropoda");

	private AnimalClassType animalClass;
	private String name;

	private AnimalClass(AnimalClassType animalClass, String name) {
		this.animalClass = animalClass;
		this.name = name;
	}

	public AnimalClassType getAnimalClass() {
		return animalClass;
	}

	public String getName() {
		return name;
	}

	public static AnimalClass fromString(String classString) {
	    if (classString == null || classString.equals("")) {
	        throw new IllegalArgumentException("The class cannot be null or empty");
	    }
	    for (AnimalClassType animalClassType : AnimalClassType.values()) {
	        if (animalClassType.name().equalsIgnoreCase(classString.trim())) {
	            return new AnimalClass(animalClassType, animalClassType.name());
	        }
	    }
	    throw new IllegalArgumentException("Invalid class string: " + classString);
	}


	public static AnimalClass getBird() {
		return BIRD;
	}

	public static AnimalClass getMammal() {
		return MAMMAL;
	}

	public static AnimalClass getReptile() {
		return REPTILE;
	}

	public static AnimalClass getAmphibian() {
		return AMPHIBIAN;
	}

	public static AnimalClass getFish() {
		return FISH;
	}

	public static AnimalClass getInvertebrate() {
		return INVERTEBRATE;
	}

	public static AnimalClass getShellfish() {
		return SHELLFISH;
	}
	
	public static AnimalClass getCrawfish() {
		return CRAYFISH;
	}
	
	public static AnimalClassType getEnumValue(String animalClass) {
	    switch(animalClass) {
	        case "Mammals":
	            return AnimalClassType.MAMMALIA;
	        case "Birds":
	            return AnimalClassType.AVES;
	        case "Reptiles":
	            return AnimalClassType.REPTILIA;
	        case "Amphibians":
	            return AnimalClassType.AMPHIBIA;
	        case "Fish":
	            return AnimalClassType.ACTINOPTERYGII;
	        case "Mollusks":
	            return AnimalClassType.BIVALVIA;
	        case "Crayfish":
	            return AnimalClassType.CRUSTACEA;
	        case "Gastropods":
	            return AnimalClassType.GASTROPODA;
	        default:
	            throw new IllegalArgumentException("Invalid animal class: " + animalClass);
	    }
	}
	
	@Override
	public String toString() {
		String s = getName().substring(0, 1) + getName().substring(1).toLowerCase();
		return s;
	}
}

