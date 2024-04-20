import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.*;

public class Main {

	public static void main(String[] args) {

		// Load amino acids from file
		try {
			List<Aminoacid> aminoAcids = Aminoacid.loadFromFile("./genomeLabNeededFiles/aminoAcidTable.csv");

			// Print out the contents of the list to verify it's loaded correctly, hopefully
			for (Aminoacid aminoAcid : aminoAcids) {
				System.out.println("Full Name: " + aminoAcid.getFullName());
				System.out.println("Single Letter Abbreviation: " + aminoAcid.getSingleLetterAbbreviation());
				System.out.println("Codons: " + aminoAcid.getCodons());
				System.out.println();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try (BufferedReader reader = new BufferedReader(new FileReader("./genomeLabNeededFiles/covidSequenceRF1.csv"))) {
			String line = reader.readLine();
			// Remove the unused variable 'line'
		} catch (IOException e) {
			e.printStackTrace();
		}

		menuSelection();
	}

	public static void menuSelection() {
		System.out.println("Enter the reading frame you would like to analyze:");
		System.out.println("1. First reading frame");
		System.out.println("2. Second reading frame");
		System.out.println("3. Third reading frame");
		System.out.println("4. Exit the program");

		// Read user input
		try (Scanner scanner = new Scanner(System.in)) {
			int readingFrame = scanner.nextInt();

			// Call the appropriate method based on the user's selection
			switch (readingFrame) {
				case 1:
					// Call method for first reading frame
					System.out.println("Do you want to (1) generate a complete report or (2) analyze a single amino acid?");
					int option1 = scanner.nextInt();
					if (option1 == 1) {
						generateCodonBiasReport(readingFrame);
					} else if (option1 == 2) {
						System.out.println("Enter the single letter representation of the amino acid to analyze:");
						String aminoAcid = scanner.next();
						analyzeSingleAminoAcid(aminoAcid, readingFrame);
					} else {
						System.out.println("Invalid selection. Please enter 1 or 2.");
					}
					break;
				case 2:
					// Call method for second reading frame
					System.out.println("Do you want to (1) generate a complete report or (2) analyze a single amino acid?");
					int option2 = scanner.nextInt();
					if (option2 == 1) {
						generateCodonBiasReport(readingFrame);
					} else if (option2 == 2) {
						System.out.println("Enter the single letter representation of the amino acid to analyze:");
						String aminoAcid = scanner.next();
						analyzeSingleAminoAcid(aminoAcid, readingFrame);
					} else {
						System.out.println("Invalid selection. Please enter 1 or 2.");
					}
					break;
				case 3:
					// Call method for third reading frame
					System.out.println("Do you want to (1) generate a complete report or (2) analyze a single amino acid?");
					int option3 = scanner.nextInt();
					if (option3 == 1) {
						generateCodonBiasReport(readingFrame);
					} else if (option3 == 2) {
						System.out.println("Enter the single letter representation of the amino acid to analyze:");
						String aminoAcid = scanner.next();
						analyzeSingleAminoAcid(aminoAcid, readingFrame);
					} else {
						System.out.println("Invalid selection. Please enter 1 or 2.");
					}
					break;
				case 4:
					// Exit the program
					System.exit(0);
					break;
				default:
					System.out.println("Invalid selection. Please enter a number between 1 and 4.");
					menuSelection();
					break;
			}
		}
	}

	public static void generateGeneReport(int readingFrame) {
		// Implement this method
	}

	public static void generateCodonBiasReport(int readingFrame) {
		// Implement this method
	}

	public static void analyzeSingleAminoAcid(String aminoAcid, int readingFrame) {
		// Implement this method
	}
	
}
