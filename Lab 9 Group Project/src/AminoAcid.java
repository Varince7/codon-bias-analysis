/**
 * This class represents an amino acid family and contains
 * information about the codons that code for the amino acid
 */

public class AminoAcid {
    private String name;
    // Number of codons that code for this amino acid in the spike
    private int spikeInstances = 0;
    // Number of codons that code for this amino acid in the replicase
    private int replicaseInstances = 0;
    // Number of synonymous codons
    private int numSynCodons = 1;


    public AminoAcid(String name){
        this.name = name;
    }

    public String toString(){
        return String.format("[Name: %s, spikeInstances: %d, replicaseInstances: %d, numberSynonymousCodons: %d]", name, spikeInstances, replicaseInstances, numSynCodons);
    }

    public String getName() {
        return name;
    }

    public int getReplicaseInstances() {
        return replicaseInstances;
    }

    public void setReplicaseInstances(int replicaseInstances) {
        this.replicaseInstances = replicaseInstances;
    }

    public int getSpikeInstances() {
        return spikeInstances;
    }

    public int getNumSynCodons() {
        return numSynCodons;
    }

    public void setNumSynCodons(int numSynCodons) {
        this.numSynCodons = numSynCodons;
    }

    public void setSpikeInstances(int spikeInstances) {
        this.spikeInstances = spikeInstances;
    }

    public void incrementNumSynCodons(){
        numSynCodons++;
    }

    /**
     * Calculates the expected codon frequency in the spike
     * @return the expected codon frequency in the spike
     */
    public double calculateSpikeExpected(){
        return (double) spikeInstances / numSynCodons;
    }

    /**
     * Calculates the expected codon frequency in the replicase
     * @return the expected codon frequency in the replicase
     */
    public double calculateReplicaseExpected(){
        return (double) replicaseInstances / numSynCodons;
    }
}
