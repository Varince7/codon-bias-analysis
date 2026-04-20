public class CodonEntry {
    private String CodonSequence;
    private String AminoAcid;
    private String Abbreviation;
    private int CodonReplicaseCount;
    private int CodonSpikeCount;
    private double ReplicaseRSCU;
    private double SpikeRSCU;
    private double RSCUDifference;

    public CodonEntry(String codonsequence, String aminoacid, String abbreviation) {
        CodonSequence = codonsequence;
        AminoAcid = aminoacid;
        Abbreviation = abbreviation;
    }

    public String getCodonSequence() {
        return CodonSequence;
    }

    public String getAminoAcid() {
        return AminoAcid;
    }

    public String getAbbreviation() {
        return Abbreviation;
    }

    public int getCodonReplicaseCount() {
        return CodonReplicaseCount;
    }

    public int getCodonSpikeCount() {
        return CodonSpikeCount;
    }

    public double getReplicaseRSCU() {
        return ReplicaseRSCU;
    }

    public double getSpikeRSCU() {
        return SpikeRSCU;
    }

    public void setReplicaseRSCU(double replicaseRSCU) {
        ReplicaseRSCU = replicaseRSCU;
    }

    public void setSpikeRSCU(double spikeRSCU) {
        SpikeRSCU = spikeRSCU;
    }

    public double getRSCUDifference() {
        return RSCUDifference;
    }

    public void setRSCUDifference(double RSCUDifference) {
        this.RSCUDifference = RSCUDifference;
    }

    public void incrementSpikeCount() {
        CodonSpikeCount++;
    }

    public void incrementReplicaseCount() {
        CodonReplicaseCount++;
    }

    public String toString() {
        return (CodonSequence+", "+AminoAcid+", "+Abbreviation+", "+ReplicaseRSCU+", "+SpikeRSCU+", "+RSCUDifference);
    }
}
