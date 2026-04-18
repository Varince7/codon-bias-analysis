public class CodonEntry {
    private String CodonSequence;
    private String AnimoAcid;
    private String Abbreviation;
    private int CodonReplicaseCount;
    private int CodonSpikeCount;
    private double ReplicaseRSCU;
    private double SpikeRSCU;

    public CodonEntry(String codonsequence, String aminoacid, String abbreviation) {
        CodonSequence = codonsequence;
        AnimoAcid = aminoacid;
        Abbreviation = abbreviation;
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

    public void incrementSpikeCount() {
        CodonSpikeCount++;
    }

    public void incrementReplicaseCount() {
        CodonReplicaseCount++;
    }
}
