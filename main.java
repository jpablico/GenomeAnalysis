import java.io.*;
import java.util.*;

public class Main {

    private static List<Aminoacid> aminoAcids;
    private static List<Gene> genes = new ArrayList<>();

    public static void main(String[] args) {
        // Load amino acids from file
        try {
            aminoAcids = Aminoacid.loadFromFile("./genomeLabNeededFiles/aminoAcidTable.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        menuSelection();
    }

	private static List<String> readCodons(String filename) throws IOException {
        List<String> codons = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                codons.add(line.trim());
            }
        }
        return codons;
    }

    private static void findGenes(List<String> codons, int readingFrame) {
        StringBuilder aminoAcidSequence = new StringBuilder();
        int start = -1;
        boolean isGene = false;

        for (int i = 0; i < codons.size(); i++) {
            String aminoAcid = translateCodonToAminoAcid(codons.get(i));
            if ("Start".equals(aminoAcid) && !isGene) {
                start = i; // Store the start index of the gene
                isGene = true;
                aminoAcidSequence = new StringBuilder(); // Start a new sequence
            } else if ("Stop".equals(aminoAcid) && isGene) {
                if (aminoAcidSequence.length() >= 50) { // Check to ensure gene length is adequate
                    genes.add(new Gene(aminoAcidSequence.toString(), start * 3, i * 3));
                }
                isGene = false; // Reset for next possible gene
            } else if (isGene) {
                aminoAcidSequence.append(aminoAcid);
            }
        }
    }
    private static String translateCodonToAminoAcid(String codon) {
        if (codon.equals("ATG")) {
            return "Start"; // Start codon
        } else if (codon.equals("TAG") || codon.equals("TAA") || codon.equals("TGA")) {
            return "Stop"; // Stop codons
        }
        for (Aminoacid acid : aminoAcids) {
            if (acid.getCodons().contains(codon)) {
                return acid.getSingleLetterAbbreviation();
            }
        }
        return "X"; // if we get an X, something went wrong, but we'll just ignore it for now
    }

    private static void menuSelection() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nSelect an option:");
            System.out.println("1. Analyze Reading Frame 1");
            System.out.println("2. Analyze Reading Frame 2");
            System.out.println("3. Analyze Reading Frame 3");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                case 2:
                case 3:
                    handleReadingFrame(choice);
                    break;
                case 4:
                    System.out.println("Exiting program.");
                    break;
                default:
                    System.out.println("Invalid selection. Please enter a number between 1 and 4.");
                    break;
            }
        } while (choice != 4);
    }

	private static void handleReadingFrame(int readingFrame) {
        Scanner scanner = new Scanner(System.in);
        int action;
        String filename = String.format("./genomeLabNeededFiles/covidSequenceRF%d.csv", readingFrame);
        
        System.out.println("Processing Reading Frame " + readingFrame);
        System.out.println("1. Generate complete codon bias report");
        System.out.println("2. Conduct single amino acid analysis");
        System.out.println("3. Generate gene analysis report");
        System.out.println("4. Return to main menu");
        System.out.print("Enter your choice: ");
        action = scanner.nextInt();

        switch (action) {
            case 1:
                generateCompleteCodonBiasReport(readingFrame, filename);
                break;
            case 2:
                conductSingleAminoAcidAnalysis(scanner);
                break;
            case 3:
                generateGeneAnalysisReport(readingFrame, filename);
                break;
            case 4:
                return; //  break out of the method to return to the main menu
            default:
                System.out.println("Invalid choice. Please select a valid option.");
                handleReadingFrame(readingFrame); // Recursively call to handle mistakes
                break;
        }
    }

	private static void generateCompleteCodonBiasReport(int readingFrame, String filename) {
		try {
			List<String> codons = readCodons(filename);
			Map<String, Integer> codonCounts = new HashMap<>();
			for (String codon : codons) {
				codonCounts.put(codon, codonCounts.getOrDefault(codon, 0) + 1);
			}
	
			String reportFilename = "CodonBiasReport_RF" + readingFrame + ".txt";
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFilename))) {
				for (Map.Entry<String, Integer> entry : codonCounts.entrySet()) {
					String codon = entry.getKey();
					int count = entry.getValue();
					double percentage = 100.0 * count / codons.size();
					writer.write(String.format("%s: %d occurrences, %.2f%%\n", codon, count, percentage));
				}
				System.out.println("Generated codon bias report: " + reportFilename);
			}
		} catch (IOException e) {
			System.err.println("Error generating codon bias report: " + e.getMessage());
		}
	}
	
	private static void conductSingleAminoAcidAnalysis(Scanner scanner) {
		System.out.print("Enter the single letter representation of the amino acid: ");
		String aminoAcid = scanner.next().toUpperCase();
		Aminoacid acid = aminoAcids.stream()
				.filter(a -> a.getSingleLetterAbbreviation().equals(aminoAcid))
				.findFirst()
				.orElse(null);
	
		if (acid == null) {
			System.out.println("No such amino acid found.");
			return;
		}
	
		System.out.println("Amino Acid: " + acid.getFullName() + " (" + acid.getSingleLetterAbbreviation() + ")");
		List<String> codons = acid.getCodons();
		int totalCodons = codons.size();
		System.out.println("Total Codons: " + totalCodons);
		System.out.println("Codon Usage:");
	
		Map<String, Integer> codonCounts = new HashMap<>();
		for (String codon : codons) {
			codonCounts.put(codon, codonCounts.getOrDefault(codon, 0) + 1);
		}
	
		for (Map.Entry<String, Integer> entry : codonCounts.entrySet()) {
			String codon = entry.getKey();
			int count = entry.getValue();
			double percentage = (count / (double) totalCodons) * 100;
			System.out.printf("  Codon: %s, Count: %d, Percentage: %.2f%%\n", codon, count, percentage);
		}
	}
	
	private static void generateGeneAnalysisReport(int readingFrame, String filename) {
		try {
			List<String> codons = readCodons(filename);
			findGenes(codons, readingFrame);
	
			String reportFilename = "GeneAnalysisReport_RF" + readingFrame + ".txt";
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFilename))) {
				for (Gene gene : genes) {
					writer.write(String.format("Gene: Start %d, End %d, Length %d amino acids\n",
											   gene.getStartNucleotidePosition(),
											   gene.getEndNucleotidePosition(),
											   gene.getLength()));
					writer.write("Amino Acid Sequence: " + gene.getAminoAcidSequence() + "\n\n");
				}
				System.out.println("Generated gene analysis report: " + reportFilename);
			}
		} catch (IOException e) {
			System.err.println("Error generating gene analysis report: " + e.getMessage());
		}
	}
	
}
