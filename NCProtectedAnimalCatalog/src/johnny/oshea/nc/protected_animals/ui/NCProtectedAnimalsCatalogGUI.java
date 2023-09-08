package johnny.oshea.nc.protected_animals.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import johnny.oshea.nc.protected_animals.animal.Animal;
import johnny.oshea.nc.protected_animals.animal.AnimalStatistics;
import johnny.oshea.nc.protected_animals.animal_reader.AnimalList;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * This is the GUI for the animal search program. It contains the functionality
 * to make everything visible to the user. It also contains the functionality to
 * make user interaction possible through both actionPerformers and
 * keyListeners. It extends JFrame and implements action listener to make sure
 * everything works effectively.
 * 
 * @author Johnny O'Shea
 */
public class NCProtectedAnimalsCatalogGUI extends JFrame implements ActionListener {
	/** A general serialVersionIUD so that the entire program can compile */
	private static final long serialVersionUID = 1L;
	/** A JLabel object representing the "Search for an animal in the list" text */
	private JLabel nameLabel;
	/** A JTextField object that gives the client an area to enter text */
	private JTextField nameField;
	/** A JButton object that provides the ability to select learn more */
	private JButton learnMoreButton;
	/** A JButton object that provides the ability to reset the screen */
	private JButton resetButton;
	
	private JTextArea outputArea;
	
	private AnimalList animalList;
	
	private JTable animalTable;
	
	private JTextArea funFactsTextArea;
	
	private JPanel statsPanel;
	
	private JPanel inputPanel;
	
	private JComboBox<String> classComboBox;
	
	private JPanel filterPanel;
	
	private JLabel filterLabel;
	/** A static final Color object that appears as jungle green */
	private static final Color jungleGreen = new Color(87, 137, 84);
	/** A static final Color object that appears as a lighter version of jungle green */
	private static final Color lighterJungleGreen = jungleGreen.brighter();
	/** A static final Font object that determines the font of the items in the animal table */
	private static final Font tableFont = new Font(Font.SANS_SERIF, Font.PLAIN, 14);;
	/** A static final Font object that determines the font of the input areas */
	private static final Font inputFont = new Font(Font.SANS_SERIF, Font.BOLD, 14);
	/** A static final Font object that determines the font of the statistics area */
	private static final Font statsFont = new Font(Font.SANS_SERIF, Font.BOLD, 12);

	public NCProtectedAnimalsCatalogGUI(String fileName) throws FileNotFoundException {
		animalList = new AnimalList(fileName);
		inputPanel = new JPanel();
		inputPanel.setBackground(lighterJungleGreen);
		nameLabel = new JLabel("Search for an animal in the list:");
		nameLabel.setFont(inputFont);
		nameField = new JTextField(20);
		nameField.setBackground(Color.lightGray);
		learnMoreButton = new JButton("Learn More");
		learnMoreButton.setBackground(Color.lightGray);
		learnMoreButton.setFont(inputFont);
		learnMoreButton.addActionListener(this);
		resetButton = new JButton("Reset");
		resetButton.setBackground(Color.lightGray);
		resetButton.setFont(inputFont);
		resetButton.addActionListener(this);
		outputArea = new JTextArea();
		outputArea.setBackground(lighterJungleGreen);
		funFactsTextArea = new JTextArea();

		// Create a keyListener so that when the user selects enter they are taken to
		// see more information
		nameField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					search();
				}
			}
		});

		// Create a panel to hold the filter label and combo box
		filterPanel = new JPanel();
		filterPanel.setBackground(lighterJungleGreen);
		filterLabel = new JLabel("Filter by class:");
		filterLabel.setFont(inputFont);
		filterPanel.add(filterLabel);
		String[] classes = { "All Animals", "Birds - Aves", "Mammals - Mammalia", "Reptiles - Reptilia",
				"Amphibians - Amphibia", "Fish - Actinopterygii", "Crayfish - Crustacea", "Mollusks - Bilvalvia",
				"Gastropods - Gastropoda" };
		classComboBox = new JComboBox<>(classes);
		classComboBox.setBackground(Color.lightGray);
		classComboBox.setFont(inputFont);
		classComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedClass = (String) classComboBox.getSelectedItem();
				updateAnimalTable(selectedClass);
				// Get the column model
				TableColumnModel columnModel = animalTable.getColumnModel();

				// Set the custom cell renderer on each column
				for (int i = 0; i < columnModel.getColumnCount(); i++) {
					columnModel.getColumn(i).setCellRenderer(new CustomCellRenderer());
				}
			}
		});

		filterPanel.add(classComboBox);

		animalList.sortByName();
		DefaultTableModel tableModel = animalList.getTableModel();
		animalTable = new JTable(tableModel);
		animalTable.setFont(tableFont);

		// Set custom cell renderer for each column
		for (int i = 0; i < tableModel.getColumnCount(); i++) {
			TableColumn column = animalTable.getColumnModel().getColumn(i);
			column.setCellRenderer(new CustomCellRenderer());
		}

		// Get the column model
		TableColumnModel columnModel = animalTable.getColumnModel();

		// Set the custom cell renderer on each column
		for (int i = 0; i < columnModel.getColumnCount(); i++) {
			columnModel.getColumn(i).setCellRenderer(new CustomCellRenderer());
		}

		// Add a scroll pane to the table
		JScrollPane scrollPane = new JScrollPane(animalTable);
		outputArea.setLayout(new BorderLayout());
		outputArea.add(scrollPane, BorderLayout.CENTER);

		inputPanel.add(nameLabel);
		inputPanel.add(nameField);
		inputPanel.add(learnMoreButton);
		inputPanel.add(resetButton);
		inputPanel.add(filterPanel);

		add(inputPanel, BorderLayout.NORTH);
		add(outputArea, BorderLayout.CENTER);

		// add the statistics panel to the main frame
		outputArea.add(createStatsPanel(), BorderLayout.SOUTH);
		outputArea.setBackground(lighterJungleGreen);

		setTitle("Protected Animals of North Carolina");
		setBackground(new Color(192, 255, 203));
		setLocation(0, 0);
		setSize(JFrame.MAXIMIZED_HORIZ, JFrame.MAXIMIZED_VERT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	private JPanel createStatsPanel() {
		statsPanel = new JPanel(new GridLayout(0, 2)); // creates a panel with 2 columns for the labels and
		// values
		statsPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0)); // adds some padding to the panel

		// create labels for each statistic
		JLabel totalAnimalsLabel = new JLabel("Total animals in the list:");
		totalAnimalsLabel.setFont(statsFont);
		JLabel totalBirdsLabel = new JLabel("Of all the protected animals in NC, this many are birds: ");
		totalBirdsLabel.setFont(statsFont);
		JLabel totalMammalsLabel = new JLabel("Of all the protected animals in NC, this many are mammals: ");
		totalMammalsLabel.setFont(statsFont);
		JLabel totalReptilesLabel = new JLabel("Of all the protected animals in NC, this many are reptiles: ");
		totalReptilesLabel.setFont(statsFont);
		JLabel totalInvertebrateLabel = new JLabel("Of all the protected animals in NC, this many are Invertebrates: ");
		totalInvertebrateLabel.setFont(statsFont);
		JLabel totalFishLabel = new JLabel("Of all the protected animals in NC, this many are fish: ");
		totalFishLabel.setFont(statsFont);
		JLabel totalCrustaceansLabel = new JLabel("Of all the protected animals in NC, this many are crustaceans: ");
		totalCrustaceansLabel.setFont(statsFont);
		JLabel totalGastropodsLabel = new JLabel("Of all the protected animals in NC, this many are gastropods: ");
		totalGastropodsLabel.setFont(statsFont);
		JLabel totalAmphibiansLabel = new JLabel("Of all the protected animals in NC, this many are amphibians: ");
		totalAmphibiansLabel.setFont(statsFont);
		JLabel totalVulnerableLabel = new JLabel("This many are considered vulnerable on a global scale: ");
		totalVulnerableLabel.setFont(statsFont);
		JLabel totalLeastConcernLabel = new JLabel("This many are considered least concern on a global scale: ");
		totalLeastConcernLabel.setFont(statsFont);
		JLabel totalEndangeredLabel = new JLabel("This many are considered endangered on a global scale: ");
		totalEndangeredLabel.setFont(statsFont);
		JLabel totalCriticallyEndangeredLabel = new JLabel(
				"This many are considered critically endangered on a global scale: ");
		totalCriticallyEndangeredLabel.setFont(statsFont);
		JLabel totalNearThreatenedLabel = new JLabel("This many are considered near threatened on a global scale: ");
		totalNearThreatenedLabel.setFont(statsFont);
		JLabel totalExtinctLabel = new JLabel("This many are considered extinct on a global scale: ");
		totalExtinctLabel.setFont(statsFont);
		JLabel totalDataDeficientLabel = new JLabel("This many are considered data deficient on a global scale: ");
		totalDataDeficientLabel.setFont(statsFont);
		JLabel demographicMessage = new JLabel("Demographic of Each Class");
		demographicMessage.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		JLabel space = new JLabel(" ");

		// create an instance of AnimalStatistics
		AnimalStatistics stats = new AnimalStatistics(animalList);

		// get the values of each statistic
		int totalAnimals = animalList.size();
		int totalVulnerable = stats.getTotalVulnerable();
		int totalReptiles = stats.getTotalReptiles();
		int totalMammals = stats.getTotalMammals();
		int totalLeastConcern = stats.getTotalLeastConcern();
		int totalInvertebrate = stats.getTotalInvertebrate();
		int totalFish = stats.getTotalFish();
		int totalEndangered = stats.getTotalEndangered();
		int totalCriticallyEndangered = stats.getTotalCriticallyEndangered();
		int totalBirds = stats.getTotalBirds();
		int totalGastropods = stats.getTotalGastropods();
		int totalAmphibians = stats.getTotalAmphibians();
		int totalCrustaceans = stats.getTotalCrustacea();
		int totalDataDeficient = stats.getTotalDataDeficient();
		int totalNearThreatened = stats.getTotalNearThreatened();
		int totalExtinct = stats.getTotalExtinct();

		// calculate percentage for each statistic
		double percentBirds = ((double) totalBirds / totalAnimals) * 100;
		double percentMammals = ((double) totalMammals / totalAnimals) * 100;
		double percentReptiles = ((double) totalReptiles / totalAnimals) * 100;
		double percentInvertebrates = ((double) totalInvertebrate / totalAnimals) * 100;
		double percentFish = ((double) totalFish / totalAnimals) * 100;
		double percentCrustaceans = ((double) totalCrustaceans / totalAnimals) * 100;
		double percentGastropods = ((double) totalGastropods / totalAnimals) * 100;
		double percentAmphibians = ((double) totalAmphibians / totalAnimals) * 100;
		double percentVulnerable = ((double) totalVulnerable / totalAnimals) * 100;
		double percentLeastConcern = ((double) totalLeastConcern / totalAnimals) * 100;
		double percentEndangered = ((double) totalEndangered / totalAnimals) * 100;
		double percentCriticallyEndangered = ((double) totalCriticallyEndangered / totalAnimals) * 100;
		double percentNearThreatened = ((double) totalNearThreatened / totalAnimals) * 100;
		double percentExtinct = ((double) totalExtinct / totalAnimals) * 100;
		double percentDataDeficient = ((double) totalDataDeficient / totalAnimals) * 100;

		// create labels to display the values of each statistic
		JLabel totalAnimalsValue = new JLabel(String.valueOf(animalList.size()));
		totalAnimalsValue.setFont(statsFont);
		JLabel totalBirdsValue = new JLabel(String.valueOf(totalBirds) + " or about "
				+ (String.format("%.2f", percentBirds) + "%") + " of all the animals.");
		totalBirdsValue.setFont(statsFont);
		JLabel totalMammalsValue = new JLabel(String.valueOf(totalMammals) + " or about "
				+ (String.format("%.2f", percentMammals) + "%") + " of all the animals.");
		totalMammalsValue.setFont(statsFont);
		JLabel totalReptilesValue = new JLabel(String.valueOf(totalReptiles) + " or about "
				+ (String.format("%.2f", percentReptiles) + "%") + " of all the animals.");
		totalReptilesValue.setFont(statsFont);
		JLabel totalInvertebrateValue = new JLabel(String.valueOf(totalInvertebrate) + " or about "
				+ (String.format("%.2f", percentInvertebrates) + "%") + " of all the animals.");
		totalInvertebrateValue.setFont(statsFont);
		JLabel totalFishValue = new JLabel(String.valueOf(totalFish) + " or about "
				+ (String.format("%.2f", percentFish) + "%") + " of all the animals.");
		totalFishValue.setFont(statsFont);
		JLabel totalCrustaceansValue = new JLabel(String.valueOf(totalCrustaceans) + " or about "
				+ (String.format("%.2f", percentCrustaceans) + "%") + " of all the animals.");
		totalCrustaceansValue.setFont(statsFont);
		JLabel totalGastropodsValue = new JLabel(String.valueOf(totalGastropods) + " or about "
				+ (String.format("%.2f", percentGastropods) + "%") + " of all the animals.");
		totalGastropodsValue.setFont(statsFont);
		JLabel totalAmphibiansValue = new JLabel(String.valueOf(totalAmphibians) + " or about "
				+ (String.format("%.2f", percentAmphibians) + "%") + " of all the animals.");
		totalAmphibiansValue.setFont(statsFont);
		JLabel totalVulnerableValue = new JLabel(String.valueOf(totalVulnerable) + " or about "
				+ (String.format("%.2f", percentVulnerable) + "%") + " of all the animals.");
		totalVulnerableValue.setFont(statsFont);
		JLabel totalLeastConcernValue = new JLabel(String.valueOf(totalLeastConcern) + " or about "
				+ (String.format("%.2f", percentLeastConcern) + "%") + " of all the animals.");
		totalLeastConcernValue.setFont(statsFont);
		JLabel totalEndangeredValue = new JLabel(String.valueOf(totalEndangered) + " or about "
				+ (String.format("%.2f", percentEndangered) + "%") + " of all the animals.");
		totalEndangeredValue.setFont(statsFont);
		JLabel totalCriticallyEndangeredValue = new JLabel(String.valueOf(totalCriticallyEndangered) + " or about "
				+ (String.format("%.2f", percentCriticallyEndangered) + "%") + " of all the animals.");
		totalCriticallyEndangeredValue.setFont(statsFont);
		JLabel totalExtinctValue = new JLabel(String.valueOf(totalExtinct) + " or about "
				+ (String.format("%.2f", percentExtinct) + "%") + " of all the animals.");
		totalExtinctValue.setFont(statsFont);
		JLabel totalDataDeficientValue = new JLabel(String.valueOf(totalDataDeficient) + " or about "
				+ (String.format("%.2f", percentDataDeficient) + "%") + " of all the animals.");
		totalDataDeficientValue.setFont(statsFont);
		JLabel totalNearThreatenedValue = new JLabel(String.valueOf(totalNearThreatened) + " or about "
				+ (String.format("%.2f", percentNearThreatened) + "%") + " of all the animals.");
		totalNearThreatenedValue.setFont(statsFont);
		// create a nested panel for the red list statistics
		JPanel redListPanel = new JPanel(new GridLayout(0, 2));

		redListPanel.add(totalAnimalsLabel);
		redListPanel.add(totalAnimalsValue);
		redListPanel.add(totalLeastConcernLabel);
		redListPanel.add(totalLeastConcernValue);
		redListPanel.add(totalEndangeredLabel);
		redListPanel.add(totalEndangeredValue);
		redListPanel.add(totalVulnerableLabel);
		redListPanel.add(totalVulnerableValue);
		redListPanel.add(totalNearThreatenedLabel);
		redListPanel.add(totalNearThreatenedValue);
		redListPanel.add(totalDataDeficientLabel);
		redListPanel.add(totalDataDeficientValue);
		redListPanel.add(totalCriticallyEndangeredLabel);
		redListPanel.add(totalCriticallyEndangeredValue);
		redListPanel.add(totalExtinctLabel);
		redListPanel.add(totalExtinctValue);
		redListPanel.setBackground(Color.lightGray);
		redListPanel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

		// create a nested panel for the class statistics
		JPanel classPanel = new JPanel(new GridLayout(0, 2));
		classPanel.add(demographicMessage);
		classPanel.add(space);
		classPanel.add(totalInvertebrateLabel);
		classPanel.add(totalInvertebrateValue);
		classPanel.add(totalFishLabel);
		classPanel.add(totalFishValue);
		classPanel.add(totalBirdsLabel);
		classPanel.add(totalBirdsValue);
		classPanel.add(totalReptilesLabel);
		classPanel.add(totalReptilesValue);
		classPanel.add(totalAmphibiansLabel);
		classPanel.add(totalAmphibiansValue);
		classPanel.add(totalMammalsLabel);
		classPanel.add(totalMammalsValue);
		classPanel.add(totalGastropodsLabel);
		classPanel.add(totalGastropodsValue);
		classPanel.add(totalCrustaceansLabel);
		classPanel.add(totalCrustaceansValue);
		classPanel.setBackground(Color.lightGray);
		classPanel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

		// add the nested panels to the stats panel
		statsPanel.add(redListPanel);
		statsPanel.add(classPanel);
		statsPanel.setBackground(Color.lightGray);
		statsPanel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		return statsPanel;
	}

	private void updateAnimalTable(String selectedClass) {
		TableModel tableModel = null;
		if (selectedClass == null) {
			tableModel = animalList.getTableModel();
		} else {
			tableModel = getFilteredTableData(selectedClass);
		}
		animalTable.setModel(tableModel);
	}

	private TableModel getFilteredTableData(String selectedClass) {
		ArrayList<Animal> filteredAnimals = animalList.getFilteredTableData(selectedClass);
		String[] columns = { "Name", "Class", "Red list status" };
		Object[][] data = new Object[filteredAnimals.size()][columns.length];
		int row = 0;
		for (Animal animal : filteredAnimals) {
			data[row][0] = animal.getName();
			data[row][1] = animal.getAnimalClass().toString();
			data[row][2] = animal.getRedListStatus().toString();
			row++;
		}
		return new DefaultTableModel(data, columns);
	}

	private JPanel getImagePanel(Animal animal) {
		JPanel imagePanel = new JPanel(new BorderLayout());
		ImageIcon imageIcon = new ImageIcon(animal.getAnimalPicture());
		JLabel imageLabel = new JLabel(imageIcon);
		imagePanel.add(imageLabel, BorderLayout.CENTER);
		return imagePanel;
	}

	private void search() {
		String name = nameField.getText();
		Animal animal = null;
		if (name.isEmpty()) {
			if (animalTable.getSelectedRow() != -1) {
				name = animalTable.getValueAt(animalTable.getSelectedRow(), 0).toString();
				animal = animalList.get(animalTable.getSelectedRow());
				outputArea.setText(animal.toString());
				JPanel imagePanel = getImagePanel(animal);
				outputArea.add(imagePanel, BorderLayout.SOUTH);
				funFactsTextArea.setText(animal.getFormattedFunFactsList());
			} else {
				JOptionPane.showMessageDialog(this, "Please enter a name or select a row.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		ArrayList<Animal> searchResult = animalList.searchByName(name);
		if (!searchResult.isEmpty()) {
			outputArea.removeAll();

			// Create left panel with GridBagLayout
			JPanel leftPanel = new JPanel(new GridBagLayout());

			// Add image panel and animal details panel to left panel
			JPanel imagePanel = new JPanel(new BorderLayout());

			// Get the screen size
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			// Set the preferred size of the left panel
			// The width needs to be half the page and the height needs to be the full
			// height
			int panelWidth = screenSize.width / 2;
			int panelHeight = screenSize.height;
			leftPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));

			// set preferred size of image panel
			imagePanel.setPreferredSize(new Dimension(panelWidth, panelHeight / 2));
			JLabel imageLabel = new JLabel();
			// Add the imageLabel to the center of the imagePanel
			imagePanel.add(imageLabel, BorderLayout.CENTER);

			// Add the imagePanel to the leftPanel
			leftPanel.add(imagePanel);

			// Create the bottom half of the left panel
			JTextArea animalDetails = new JTextArea();
			// Make it so that people cannot edit the information
			animalDetails.setEditable(false);
			// Customize the font
			animalDetails.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
			// Customize the background
			animalDetails.setBackground(new Color(235, 156, 92).brighter());

			// Create a scroll pane for the information
			JScrollPane scrollPane = new JScrollPane(animalDetails);

			// Gets rid of the visible scroll bar but incorporates the scrolling
			// functionality
			JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
			scrollBar.setValue(0);
			scrollPane.add(scrollBar);

			// Add the scroll pane to a GridBagConstraints
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.weighty = 1.0;
			gbc.weightx = 1.0;
			gbc.fill = GridBagConstraints.BOTH;
			leftPanel.add(scrollPane, gbc);

			// Add left panel to outputArea panel
			outputArea.setLayout(new BorderLayout());
			outputArea.add(leftPanel, BorderLayout.WEST);

			// Create right panel with BorderLayout
			JPanel rightPanel = new JPanel(new BorderLayout());
			rightPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
			// Create the fun facts text area
			JTextArea funFacts = new JTextArea();
			// Make sure people cannot edit the fun facts
			funFacts.setEditable(false);
			// Create a custom Font
			funFacts.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
			// Customize the background
			funFacts.setBackground(new Color(83, 145, 126).brighter());
			// Add a scroll pane in case the fun facts go beyond the initial page
			JScrollPane scrollPane2 = new JScrollPane(funFacts);
			// Add everything to the Center
			rightPanel.add(scrollPane2, BorderLayout.CENTER);

			JScrollBar scrollBar2 = scrollPane2.getVerticalScrollBar();
			scrollBar2.setValue(0);

			scrollPane2.add(scrollBar2);

			// Handle what happens if the user searches for one specific animal
			if (searchResult.size() == 1) {
				// Get the animal that the user searched for
				animal = searchResult.get(0);
				// Get the information that will populate the panels in the GUI
				animalDetails.append(animal.toString());
				funFacts.append(animal.getFormattedFunFactsList());
				// Create an image using the animal picture for this animal.
				imageLabel.setIcon(new ImageIcon(animal.getAnimalPicture()));
				// Make sure that the image is properly scaled to be the width of the left panel
				// and exactly half the height of the left panel
				ImageIcon icon = new ImageIcon(animal.getAnimalPicture());
				Image image = icon.getImage().getScaledInstance(panelWidth, panelHeight / 2, Image.SCALE_SMOOTH);
				icon = new ImageIcon(image);
				imageLabel.setIcon(icon);
				// Show the image panel when the user searches for one animal
				imagePanel.setVisible(true);
			} else {
				animalDetails.append("Showing the first 5 results for \"" + name + "\":\n\n");
				funFacts.append("Here are some fun facts about the animals on the left! \n");
				int counter = 0; // initialize counter variable
				imagePanel.setVisible(false); // Hide the image panel
				// Start a for loop so that only 5 animals will every show up at a time at max
				for (int i = 0; i < animalList.size(); i++) {
					animal = animalList.get(i);
					if (searchResult.contains(animal)) { // check if animal name matches search text
						animalDetails.append(animal.toString());
						funFacts.append(animal.getFormattedFunFactsList());
						funFacts.append("\n");
						counter++; // increment counter variable
						if (counter >= 5) { // exit loop if we've added 5 animals
							break;
						}
					}
				}
			}
			// Create split pane and add left and center panels
			JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
			outputArea.add(splitPane, BorderLayout.CENTER);
			// Make sure that the divider for the splitPane is in the center every time
			splitPane.setDividerLocation(screenSize.width / 2);
			outputArea.revalidate();
			outputArea.repaint();
		} else {
			JOptionPane.showMessageDialog(this, "Animal is not in the list.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void reset() {
		nameField.setText("");
		outputArea.removeAll();
		classComboBox.setSelectedIndex(0);
		animalList.sortByName();
		DefaultTableModel tableModel = animalList.getTableModel();
		animalTable.setModel(tableModel);
		TableColumnModel columnModel = animalTable.getColumnModel();
		// Set the custom cell renderer on each column
		for (int i = 0; i < columnModel.getColumnCount(); i++) {
			columnModel.getColumn(i).setCellRenderer(new CustomCellRenderer());
		}
		animalTable.clearSelection();
		outputArea.add(new JScrollPane(animalTable), BorderLayout.CENTER);
		outputArea.add(createStatsPanel(), BorderLayout.SOUTH);
		outputArea.revalidate();
		outputArea.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == learnMoreButton || event.getSource() == nameField) {
			search();
		} else if (event.getSource() == resetButton) {
			reset();
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		new NCProtectedAnimalsCatalogGUI("test-files/All_Animals.txt");
	}

	private class CustomCellRenderer extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 1L;
		Color row1Color = new Color(200, 200, 255); // Lavender
		Color row2Color = new Color(192, 255, 203); // Bright green
		Color row3Color = new Color(90, 79, 207).brighter().brighter(); // blue Iris

		// Override the getTableCellRendererComponent method
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {

			// Call the super method to get the default renderer
			Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			// Change the background color of the cell based on the column
			if (column == 0) {
				c.setBackground(row1Color);
			} else if (column == 1) {
				c.setBackground(row2Color);
			} else {
				c.setBackground(row3Color);
			}

			// Change the font and color of the header based on the column
			if (table.getTableHeader() != null) {
				table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
				table.getTableHeader().setBackground(Color.lightGray);
			}

			return c;
		}
	}
}
