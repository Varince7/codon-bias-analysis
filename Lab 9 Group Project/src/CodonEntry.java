public class CodonEntry {
    // This is the CodonEntry Class, where all the values needed to create files are stored.

    // Initiate Variables
    private String CodonSequence;
    private String AminoAcid;
    private String Abbreviation;
    private int CodonReplicaseCount;
    private int CodonSpikeCount;
    private double ReplicaseRSCU;
    private double SpikeRSCU;
    private double RSCUDifference;
    private double RSCUPercentageDifference;


    public CodonEntry(String codonsequence, String aminoacid, String abbreviation) {
        // The Constructor for CodonEntry.
        CodonSequence = codonsequence;
        AminoAcid = aminoacid;
        Abbreviation = abbreviation;
    }

    public String getCodonSequence() {
        return CodonSequence;
        // Method for retrieving the CodonSequence Variable.
    }

    public String getAminoAcid() {
        return AminoAcid;
        // Method for retrieving the AminoAcid Variable.
    }

    public String getAbbreviation() {
        return Abbreviation;
        // Method for retrieving the Abbreviation Variable.
    }

    public int getCodonReplicaseCount() {
        return CodonReplicaseCount;
        // Method for retrieving the CodonReplicaseCount Variable.
    }

    public int getCodonSpikeCount() {
        return CodonSpikeCount;
        // Method for retrieving the CodonSpikeCount Variable.
    }

    public double getReplicaseRSCU() {
        return ReplicaseRSCU;
        // Method for retrieving the ReplicaseRSCU Variable.
    }

    public double getSpikeRSCU() {
        return SpikeRSCU;
        // Method for retrieving the SpikeRSCU Variable.
    }

    public void setReplicaseRSCU(double replicaseRSCU) {
        ReplicaseRSCU = replicaseRSCU;
        // Method for setting the ReplicaseRSCU Variable.
    }

    public void setSpikeRSCU(double spikeRSCU) {
        SpikeRSCU = spikeRSCU;
        // Method for setting the CSpikeRSCU Variable.
    }

    public double getRSCUDifference() {
        return RSCUDifference;
        // Method for retrieving the RSCUDifference Variable.
    }

    public void setRSCUDifference(double RSCUDifference) {
        this.RSCUDifference = RSCUDifference;
        // Method for setting the RSCUDifference Variable.
    }

    public double getRSCUPercentageDifference() {
        return RSCUPercentageDifference;
        // Method for retrieving the RSCUPersentageDifference Variable.
    }

    public void setRSCUPercentageDifference(double RSCUPercentageDifference) {
        this.RSCUPercentageDifference = RSCUPercentageDifference;
    }

    public void incrementSpikeCount() {
        CodonSpikeCount++;
        // Increases CodonSpikeCount by 1.

    }

    public void incrementReplicaseCount() {
        CodonReplicaseCount++;
        // Increases CodonReplicaseCount by 1.
    }

    public String toString() {
        return (CodonSequence+", "+AminoAcid+", "+Abbreviation+", "+ReplicaseRSCU+", "+SpikeRSCU+", "+RSCUDifference);
        // The ToString method for CodonEntry.
    }
}