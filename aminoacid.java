import java.io.*;
import java.util.*;

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
        return new ArrayList<>(codons); // Return a copy to preserve encapsulation
    }

    public static List<Aminoacid> loadFromFile(String filename) throws IOException {
        List<Aminoacid> aminoAcids = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            reader.readLine(); 
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String fullName = parts[0];
                String threeLetterAbbreviation = parts[1];
                String singleLetterAbbreviation = parts[2];
                List<String> codons = new ArrayList<>();
                for (int i = 3; i < parts.length; i++) {
                    codons.add(parts[i]);
                }
                aminoAcids.add(new Aminoacid(fullName, singleLetterAbbreviation, threeLetterAbbreviation, codons));
            }
        }
        return aminoAcids;
    }
}
