public class CodonEntry {
    private String CodonSequence;
    private String AnimoAcid;
    private String Abbreviation;
    private int CodonReplicaseCount;
    private int CodonSpikeCount;
    private double ReplicaseRSCU;
    private double SpikeRSCU;

    public CodonEntry(String codonsequence, String aminoacid, String abbreviation, int codonreplicasecount, int codonspikecount, double replicaseRSCU, double spikeRSCU) {
        CodonSequence = codonsequence;
        AnimoAcid = aminoacid;
        Abbreviation = abbreviation;
        CodonReplicaseCount = codonreplicasecount;
        CodonSpikeCount = codonspikecount;
        ReplicaseRSCU = replicaseRSCU;
        SpikeRSCU = spikeRSCU;
    }

    public String getCodonSequence() {
        return CodonSequence;
    }

    public String getAnimoAcid() {
        return AnimoAcid;
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
}
