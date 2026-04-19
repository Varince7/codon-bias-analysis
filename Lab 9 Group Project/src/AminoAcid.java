/**
 * This class represents an amino acid family and contains
 * information about the codons that code for the amino acid
 */

public class AminoAcid {
    private String name;
    // Number of codons that code for this amino acid in the spike
    private int spikeCount = 0;
    // Number of codons that code for this amino acid in the replicase
    private int replicaseCount = 0;
    // Number of synonymous codons
    private int numSynCodons = 1;


    public AminoAcid(String name){
        this.name = name;
    }

    public String toString(){
        return String.format("[Name: %s, spikeCount: %d, replicaseCount: %d, numberSynonymousCodons: %d]", name, spikeCount, replicaseCount, numSynCodons);
    }

    public String getName() {
        return name;
    }

    public int getReplicaseCount() {
        return replicaseCount;
    }

    public void setReplicaseCount(int replicaseCount) {
        this.replicaseCount = replicaseCount;
    }

    public int getSpikeCount() {
        return spikeCount;
    }

    public int getNumSynCodons() {
        return numSynCodons;
    }

    public void setNumSynCodons(int numSynCodons) {
        this.numSynCodons = numSynCodons;
    }

    public void setSpikeCount(int spikeCount) {
        this.spikeCount = spikeCount;
    }

    public void incrementNumSynCodons(){
        numSynCodons++;
    }

    /**
     * Calculates the expected codon frequency in the spike
     * @return the expected codon frequency in the spike
     */
    public double calculateSpikeExpected(){
        return (double) spikeCount / numSynCodons;
    }

    /**
     * Calculates the expected codon frequency in the replicase
     * @return the expected codon frequency in the replicase
     */
    public double calculateReplicaseExpected(){
        return (double) replicaseCount / numSynCodons;
    }
}
