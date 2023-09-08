package johnny.oshea.nc.protected_animals.animal_reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.table.DefaultTableModel;

import johnny.oshea.nc.protected_animals.animal.Animal;
import johnny.oshea.nc.protected_animals.animal.AnimalClass;
import johnny.oshea.nc.protected_animals.animal.AnimalStatus;

public class AnimalList {
	private ArrayList<Animal> animalList;
	private AnimalClass.AnimalClassType currentAnimalClass;

	public AnimalList(String fileName) throws FileNotFoundException {
		animalList = new ArrayList<Animal>();

		File file = new File("test-files/All_Animals.txt");
		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] parts = line.split(",");
			String name = parts[0];
			AnimalStatus animalStatus = AnimalStatus.fromString(parts[1]);
			AnimalClass animalClass = AnimalClass.fromString(parts[2]);
			String estimatedRemain = parts[3];
			String additionalInfo = parts[4];
			String funFacts = parts[5];
			String animalPicture = parts[6];

			Animal animal = new Animal(name, animalClass, animalStatus, estimatedRemain, additionalInfo, funFacts, animalPicture);
			animalList.add(animal);
		}
		scanner.close();
	}

	public ArrayList<Animal> searchByName(String name) {
	    ArrayList<Animal> matchingAnimals = new ArrayList<>();
	    for (Animal animal : animalList) {
	        if (animal.getName().toLowerCase().contains(name.toLowerCase())) {
	            matchingAnimals.add(animal);
	        }
	    }
	    return matchingAnimals;
	}
	
	public ArrayList<Animal> getFilteredTableData(String filterByClass) {
		ArrayList<Animal> filteredList = new ArrayList<>();
		if (filterByClass.equals("All Animals")) {

			for (Animal animal : animalList) {
				filteredList.add(animal);
			}
		} else {
			AnimalClass.AnimalClassType classEnum = AnimalClass.getEnumValue(filterByClass.substring(0, filterByClass.indexOf('-') - 1));
			for (Animal animal : animalList) {
				if (animal.getAnimalClass().getAnimalClass().equals(classEnum)) {
					filteredList.add(animal);
				}
			}
		}
		return filteredList;
	}

	public DefaultTableModel getTableModel() {
		String[] columns = { "Name", "Class", "Red list status" };
		Object[][] data = new Object[animalList.size()][3];
		int row = 0;
		for (Animal animal : animalList) {
			if (currentAnimalClass != null && !animal.getAnimalClass().getAnimalClass().equals(currentAnimalClass)) {
				continue; // skip if animal doesn't match the selected class
			}
			data[row][0] = animal.getName();
			data[row][1] = animal.getAnimalClass().toString();
			data[row][2] = animal.getRedListStatus().toString();
			row++;
		}
		return new DefaultTableModel(data, columns);
	}

	public ArrayList<Animal> getAllAnimals() {
		return animalList;
	}

	public void resetList() {
		animalList.clear();
	}

	public void add(Animal animal) {
		animalList.add(animal);
	}

	public int size() {
		return animalList.size();
	}

	public Animal get(int index) {
		return animalList.get(index);
	}

	public void sortByName() {
        Collections.sort(animalList);
    }

}
