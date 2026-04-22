import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;


enum Region { SPIKE, REPLICASE }

public class Main {
    public static void main(String[] args) throws IOException {
        // Constructs CodonEntry arraylist with data from file
        ArrayList<CodonEntry> codonList = readCodonFile("codons.csv");
        // Sets spike/replicase counts for each codon
        parseFasta(codonList, "h1n1-ha.fasta", Region.SPIKE);
        parseFasta(codonList, "h1n1-pb1.fasta", Region.REPLICASE);

        // Calculate RSCU for codons
        calculateRSCU(codonList);
        // Sets differences between spike/replicase RSCU
        setRSCUDiff(codonList);

        writeRegionCSV(codonList, Region.SPIKE);
        writeRegionCSV(codonList, Region.REPLICASE);
        GenerateComparison(codonList,"replicase_rscu.csv","spike_rscu.csv");

        generateFavoredReport(codonList.get(0));
    }

    public static ArrayList<CodonEntry> readCodonFile(String filename) throws IOException {
        ArrayList<CodonEntry> list = new ArrayList<>();

//        Test
        File f = new File(filename);
        Scanner reader = new Scanner(f);

        reader.nextLine(); // Gets rid of first line
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            String[] parts = line.split(",");

//            if (parts.length >= ___)
//            {
            CodonEntry entry = new CodonEntry(parts[0], parts[1], parts[2]);
            list.add(entry);
//            }
        }
        reader.close();

//        catch(Exception e)
//        {
//            System.out.println("Error___");

        return list;

    }

    public static void calculateRSCU(ArrayList<CodonEntry> list) {
        for (CodonEntry entry : list) {
            String aminoAcid = entry.getAminoAcid();

            int totalRep = 0;
            int totalSpike = 0;
            int countSyn = 0;

            for (CodonEntry other : list) {
                if (other.getAminoAcid().equals(aminoAcid)) {
                    totalRep += other.getCodonReplicaseCount();
                    totalSpike += other.getCodonSpikeCount();
                    countSyn++;
                }
            }

            double expectedRep;
            double expectedSpike;

            if (countSyn == 0) {
                expectedRep = 0;
                expectedSpike = 0;
            } else {
                expectedRep = (double) totalRep / countSyn;
                expectedSpike = (double) totalSpike / countSyn;
            }

            double rscuRep = 0;
            double rscuSpike = 0;

            if (expectedRep != 0) {
                rscuRep = entry.getCodonReplicaseCount() / expectedRep;
            }
            if (expectedSpike != 0) {
                rscuSpike = entry.getCodonSpikeCount() / expectedSpike;
            }
            entry.setReplicaseRSCU(rscuRep);
            entry.setSpikeRSCU(rscuSpike);
        }
    }

    /**
     * Calculates and sets the RSCU difference between the spike and the replicase
     * for an ArrayList of CodonEntrys
     *
     * @param codonList An ArrayList of CodonEntrys to set RSCU differences for
     */
    public static void setRSCUDiff(ArrayList<CodonEntry> codonList) {
        for (CodonEntry entry : codonList) {
            double difference = entry.getSpikeRSCU() - entry.getReplicaseRSCU();
            entry.setRSCUDifference(difference);

            double percentageDiff;
            if (entry.getReplicaseRSCU() == 0)
            {
                percentageDiff = 0;
            }
            else
            {
                percentageDiff = (difference / entry.getReplicaseRSCU()) * 100;
            }
            entry.setRSCUPercentageDifference(percentageDiff);
        }
    }

    /**
     * Takes a RSCU value and returns a category based on its favorability
     *
     * @param RSCU RSCU value to determine category for
     * @return the category of the RSCU value
     */
    public static String determineRSCUCategory(double RSCU) {
        if (RSCU < 0.6) {
            return "Highly Unfavored";
        } else if (RSCU >= 0.6 && RSCU < 0.9) {
            return "Unfavored";
        } else if (RSCU >= 0.9 && RSCU <= 1.1) {
            return "Stable";
        } else if (RSCU > 1.1 && RSCU <= 1.6) {
            return "Favored";
        } else {
            return "Highly Favored";
        }
    }

    /**
     * Parses spike/replicase fasta files and sets the counts for the CodonEntry objects
     * @param list   The CodonEntry ArrayList to set counts for
     * @param path   The filepath of the fasta file to read from
     * @param region The fasta file's corresponding region of the virus
     */
    public static void parseFasta(ArrayList<CodonEntry> list, String path, Region region) throws IOException {
        // Open file
        File f = new File(path);
        Scanner infile = new Scanner(f);

        while (infile.hasNext()) {
            // Read a line and loop through it three characters at a time
            String line = infile.nextLine();
            for (int i = 0; i <= line.length() - 3; i += 3) {
                // Construct each codon string
                String codon = line.substring(i, i + 3);

                // Find codon match in arraylist and increment
                for (CodonEntry entry : list) {
                    if (codon.equals(entry.getCodonSequence())) {
                        switch (region) {
                            case SPIKE -> entry.incrementSpikeCount();
                            case REPLICASE -> entry.incrementReplicaseCount();
                        }
                    }
                }
            }
        }
        // Close file
        infile.close();
    }

    public static void writeRegionCSV(ArrayList<CodonEntry> list, Region region) throws FileNotFoundException {
        String filename = switch(region){
            case SPIKE -> "spike_rscu.csv";
            case REPLICASE -> "replicase_rscu.csv";
        };
        PrintWriter out = new PrintWriter(filename);

        out.println("Codon,AA_Name,AA_Code,Count,Total_AA_Count,Percent,RSCU");

        int count;
        double rscu;
        double percent;

        for (CodonEntry entry : list) {
            String aminoAcid = entry.getAminoAcid();
            int total = 0;

            for (CodonEntry other : list) {
                if (other.getAminoAcid().equals(aminoAcid)) {
                    switch(region) {
                       case REPLICASE -> total += other.getCodonReplicaseCount();
                       case SPIKE -> total += other.getCodonSpikeCount();
                    }
                }
            }

            switch(region) {
                case REPLICASE:
                    count = entry.getCodonReplicaseCount();
                    rscu = entry.getReplicaseRSCU();
                    break;
                case SPIKE:
                    count = entry.getCodonSpikeCount();
                    rscu = entry.getSpikeRSCU();
                    break;
                default:
                    count = 0;
                    rscu = 0;
            }

            if (total == 0) {
                percent = 0;
            } else {
                percent = (double) count / total * 100;
            }

            out.printf("%s,%s,%s,%d,%d,%.2f%%,%.2f\n", entry.getCodonSequence(),entry.getAminoAcid(),entry.getAbbreviation(),count,total,percent,rscu);
        }
        out.close();
    }

    public static CodonEntry getEntry(ArrayList<CodonEntry> codonList, String sequence)
    {
        for (CodonEntry entry: codonList)
        {
            if(entry.getCodonSequence().equals(sequence))
            {
                return entry;
            }
        }
        return null;
    }

    public static void GenerateComparison(ArrayList<CodonEntry> codonList, String replicaseFile, String spikeFile) throws IOException {

        PrintWriter rscuOutfile = new PrintWriter("rscu_comparison.csv");

        rscuOutfile.println("Codon,AA_Name,AA_Code,RSCU_Replicase,RSCU_Spike,RSCU_Diff,RSCU_Pct_Diff,Replicase_Category,Spike_Category,Category_Change");

        File Replicase = new File(replicaseFile);
        Scanner repReader = new Scanner(Replicase);

        File Spike = new File(spikeFile);
        Scanner spikeReader = new Scanner(Spike);

        String RepLine;
        String SpikeLine;

        repReader.nextLine();
        spikeReader.nextLine();

        while (repReader.hasNextLine()) {
            RepLine = repReader.nextLine();
            SpikeLine = spikeReader.nextLine();
            String[] RepParts = RepLine.split(",");
            String[] SpikeParts = SpikeLine.split(",");
            String sequence = RepParts[0];
            String aminoAcid = RepParts[1];
            String abbreviation = RepParts[2];
            Double repRSCU = Double.parseDouble(RepParts[6]);
            Double spikeRSCU = Double.parseDouble(SpikeParts[6]);
            CodonEntry entry = getEntry(codonList, sequence);
            rscuOutfile.printf("%s,%s,%s,%.2f,%.2f,%.2f,%.2f,%s,%s\n",
                    sequence, aminoAcid, abbreviation, repRSCU, spikeRSCU, entry.getRSCUDifference(), entry.getRSCUPercentageDifference(), determineRSCUCategory(repRSCU), determineRSCUCategory(spikeRSCU));
        }
        rscuOutfile.close();
        repReader.close();
        spikeReader.close();
    }
     public static int getFavored(double repRank)
    {
        if (repRank >= 1.6)
        {
            return 5;
        }
        else if (repRank >= 1.2)
        {
            return 4;
        }
        else if (repRank >= 0.8)
        {
            return 3;
        }
        else if (repRank >= 0.4)
        {
            return 2;
        }
        else
        {
            return 1;
        }
    }

    public static String upOrDown(CodonEntry c)
    {
        int repFavored = getFavored(c.getReplicaseRSCU());
        int spikeFavored = getFavored(c.getSpikeRSCU());

        if (spikeFavored > repFavored)
        {
            return "UP";
        }
        else if (spikeFavored < repFavored)
        {
            return "DOWN";
        }
        else
        {
            return " ";
        }
    }

    public static void generateFavoredReport(CodonEntry entry) throws IOException
    {
        PrintWriter outfile = new PrintWriter("spike_favored.txt");

        String dashedLine = "--------------------------------------------------------------------------";
        String heading = String.format("%s\n%-8s%-24s%-6s%-22s%-14s\n%s",
                dashedLine, "Codon", "Amino Acid", "AA", "Replicase Category", "Spike Category", dashedLine);

        outfile.print("==========================================================================\n" +
                      "CODON USAGE BIAS SHIFT REPORT — Replicase vs. Spike Protein\n" +
                      "==========================================================================\n");

        outfile.println("\n▲  INCREASED FAVORABILITY  (Spike more favored than Replicase)");
        outfile.println(heading);
        outfile.printf("%-8s%-24s%-6s%-22s%-14s\n", entry.getCodonSequence(), entry.getAminoAcid(), entry.getAbbreviation(), "Favored", "Stable");
        outfile.println(dashedLine);
        outfile.println("Total codons shifted UP:");

        outfile.println("\n▼  DECREASED FAVORABILITY  (Spike less favored than Replicase)");
        outfile.println(heading);
        outfile.printf("%-8s%-24s%-6s%-22s%-14s\n", entry.getCodonSequence(), entry.getAminoAcid(), entry.getAbbreviation(), "Favored", "Stable");
        outfile.println(dashedLine);
        outfile.println("Total codons shifted DOWN:");
        outfile.close();
    }
}
