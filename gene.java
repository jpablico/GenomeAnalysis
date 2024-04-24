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

    @Override
    public String toString() {
        return String.format("Gene: Start at %d, End at %d, Length: %d amino acids, Sequence: %s",
                             startNucleotidePosition, endNucleotidePosition, getLength(), aminoAcidSequence);
    }
}
