package johnny.oshea.nc.protected_animals.animal;

import johnny.oshea.nc.protected_animals.animal_reader.AnimalList;

public class AnimalStatistics {
	private AnimalList animals;

	public AnimalStatistics(AnimalList animalList) {
		if (animalList == null) {
			throw new IllegalArgumentException("Animals cannot be null");
		}

		this.animals = animalList;
	}

	public int getTotalAnimals() {
		return animals.size();
	}

	public int getTotalEndangered() {
		return countAnimalsByStatus(AnimalStatus.RedListStatus.ENDANGERED);
	}

	public int getTotalVulnerable() {
		return countAnimalsByStatus(AnimalStatus.RedListStatus.VULNERABLE);
	}

	public int getTotalCriticallyEndangered() {
		return countAnimalsByStatus(AnimalStatus.RedListStatus.CRITICALLY_ENDANGERED);
	}

	public int getTotalLeastConcern() {
		return countAnimalsByStatus(AnimalStatus.RedListStatus.LEAST_CONCERN);
	}
	
	public int getTotalDataDeficient() {
		return countAnimalsByStatus(AnimalStatus.RedListStatus.DATA_DEFICIENT);
	}
	
	public int getTotalExtinct() {
		return countAnimalsByStatus(AnimalStatus.RedListStatus.EXTINCT);
	}
	
	public int getTotalNearThreatened() {
		return countAnimalsByStatus(AnimalStatus.RedListStatus.NEAR_THREATENED);
	}

	public double getPercentageEndangered() {
		return calculatePercentage(getTotalEndangered());
	}

	public double getPercentageVulnerable() {
		return calculatePercentage(getTotalVulnerable());
	}

	public double getPercentageCriticallyEndangered() {
		return calculatePercentage(getTotalCriticallyEndangered());
	}

	public double getPercentageLeastConcern() {
		return calculatePercentage(getTotalLeastConcern());
	}

	private int countAnimalsByStatus(AnimalStatus.RedListStatus status) {
		int count = 0;
		for (Animal animal : animals.getAllAnimals()) {
			if (animal.getRedListStatus().getRedListStatus() == status) {
				count++;
			}
		}
		return count;
	}

	private double calculatePercentage(int count) {
		return ((double) count / animals.size()) * 100;
	}
	
	public int getTotalBirds() {
		int count = 0;
        for (int i = 0; i < animals.getAllAnimals().size(); i++) {
        	Animal a = animals.get(i);
        	
        	if (a.getAnimalClass().getAnimalClass().equals(AnimalClass.AnimalClassType.AVES)) {
        		count++;
        	}
        }
        return count;
    }

    public int getTotalMammals() {
        int count = 0;
        for (int i = 0; i < animals.getAllAnimals().size(); i++) {
        	Animal a = animals.get(i);
        	
        	if (a.getAnimalClass().getAnimalClass().equals(AnimalClass.AnimalClassType.MAMMALIA)) {
        		count++;
        	}
        }
        return count;
    }

    public int getTotalReptiles() {
    	int count = 0;
        for (int i = 0; i < animals.getAllAnimals().size(); i++) {
        	Animal a = animals.get(i);
        	
        	if (a.getAnimalClass().getAnimalClass().equals(AnimalClass.AnimalClassType.REPTILIA)) {
        		count++;
        	}
        }
        return count;
    }
    
    public int getTotalAmphibians() {
    	int count = 0;
        for (int i = 0; i < animals.getAllAnimals().size(); i++) {
        	Animal a = animals.get(i);
        	
        	if (a.getAnimalClass().getAnimalClass().equals(AnimalClass.AnimalClassType.AMPHIBIA)) {
        		count++;
        	}
        }
        return count;
    }
    
    public int getTotalFish() {
    	int count = 0;
        for (int i = 0; i < animals.getAllAnimals().size(); i++) {
        	Animal a = animals.get(i);
        	
        	if (a.getAnimalClass().getAnimalClass().equals(AnimalClass.AnimalClassType.ACTINOPTERYGII)) {
        		count++;
        	}
        }
        return count;
    }
    
    public int getTotalInvertebrate() {
    	int count = 0;
        for (int i = 0; i < animals.getAllAnimals().size(); i++) {
        	Animal a = animals.get(i);
        	
        	if (a.getAnimalClass().getAnimalClass().equals(AnimalClass.AnimalClassType.BIVALVIA)) {
        		count++;
        	}
        }
        return count;
    }
    
    public int getTotalCrustacea() {
    	int count = 0;
        for (int i = 0; i < animals.getAllAnimals().size(); i++) {
        	Animal a = animals.get(i);
        	
        	if (a.getAnimalClass().getAnimalClass().equals(AnimalClass.AnimalClassType.CRUSTACEA)) {
        		count++;
        	}
        }
        return count;
    }
    
    public int getTotalGastropods() {
    	int count = 0;
        for (int i = 0; i < animals.getAllAnimals().size(); i++) {
        	Animal a = animals.get(i);
        	
        	if (a.getAnimalClass().getAnimalClass().equals(AnimalClass.AnimalClassType.GASTROPODA)) {
        		count++;
        	}
        }
        return count;
    }

	public double getPercentageByClass(AnimalClass animalClass) {
		int count = 0;
		for (Animal animal : animals.getAllAnimals()) {
			if (animal.getAnimalClass().equals(animalClass)) {
				count++;
			}
		}
		return calculatePercentage(count);
	}
}
