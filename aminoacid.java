import java.util.*;
import java.io.*;

public class Aminoacid {
	private String fullName;
	private String singleLetterAbbreviation;
	private String threeLetterAbbreviation;
	private List<String> codons;

	public Aminoacid(String fullName, String singleLetterAbbreviation, String threeLetterAbbreviation, List<String> codons) {
		this.fullName = fullName;
		this.singleLetterAbbreviation = singleLetterAbbreviation;
		this.threeLetterAbbreviation = threeLetterAbbreviation;
		this.codons = codons;
	}
	public String getFullName() {
		return fullName;
	}

	public String getSingleLetterAbbreviation() {
		return singleLetterAbbreviation;
	}

	public String getThreeLetterAbbreviation() {
		return threeLetterAbbreviation;
	}

	public List<String> getCodons() {
		return codons;
	}

	public static List<Aminoacid> loadFromFile(String filename) throws IOException {
		List<Aminoacid> aminoAcids = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader("genomeLabNeededFiles/aminoAcidTable.csv"))) {
			String line;
			reader.readLine();
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				String fullName = parts[0];
				String singleLetterAbbreviation = parts[2];
				String threeLetterAbbreviation = parts[1];
				List<String> codons = new ArrayList<>();
				for (int i = 3; i < parts.length; i++) {
					codons.add(parts[i]);
				}
				// Create a new Aminoacid object and add it to the list
				aminoAcids.add(new Aminoacid(fullName, singleLetterAbbreviation, threeLetterAbbreviation, codons)); 
			}
		}
		return aminoAcids;
	}
}