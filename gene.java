import java.util.ArrayList;
public class Gene {
    private String aminoAcidSequence;
    private int startNucleotidePosition;
    private int endNucleotidePosition;

    public Gene(String aminoAcidSequence, int startNucleotidePosition, int endNucleotidePosition) {
        this.aminoAcidSequence = aminoAcidSequence;
        this.startNucleotidePosition = startNucleotidePosition;
        this.endNucleotidePosition = endNucleotidePosition;
    }

    public String getAminoAcidSequence() {
        return aminoAcidSequence;
    }

    public int getStartNucleotidePosition() {
        return startNucleotidePosition;
    }

    public int getEndNucleotidePosition() {
        return endNucleotidePosition;
    }


    public int getLength() {
        return aminoAcidSequence.length();
    }

    public List<int[]> findSubSequencePositions(String subSequence) {
        List<int[]> positions = new ArrayList<>();
        int index = aminoAcidSequence.indexOf(subSequence);
        while (index >= 0) {
            // Calculate the nucleotide positions considering each amino acid translates to 3 nucleotides.
            int startPos = startNucleotidePosition + index * 3;
            int endPos = startPos + subSequence.length() * 3 - 1;
            positions.add(new int[] {startPos, endPos});

            // Move to the next possible start position
            index = aminoAcidSequence.indexOf(subSequence, index + 1);
        }
        return positions;
    }
        
    @Override
    public String toString() {
        return String.format("Gene: Start at %d, End at %d, Length: %d amino acids, Sequence: %s",
                             startNucleotidePosition, endNucleotidePosition, getLength(), aminoAcidSequence);
    }
}
