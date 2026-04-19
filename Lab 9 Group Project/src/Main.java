import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

enum Region { SPIKE, REPLICASE }

public class Main {
    public static void main(String[] args) throws IOException
    {
        ArrayList<CodonEntry> codonList = readCodonFile("codons.csv");
        parseFasta(codonList, "h1n1-ha.fasta", Region.SPIKE);
        parseFasta(codonList, "h1n1-pb1.fasta", Region.REPLICASE);

        ArrayList<AminoAcid> aminoList = getAminoList(codonList);

        calculateRSCU(codonList);
    }

    public static ArrayList<CodonEntry> readCodonFile(String filename) throws IOException
    {
        ArrayList<CodonEntry> list = new ArrayList<>();

//        Test
        File f = new File(filename);
        Scanner reader = new Scanner(f);

        reader.nextLine(); // Gets rid of first line
        while (reader.hasNextLine())
        {
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

    public static void calculateRSCU(ArrayList<CodonEntry> list)
    {
        for(CodonEntry entry : list)
        {
            String aminoAcid = entry.getAminoAcid();

            int totalRep = 0;
            int totalSpike = 0;
            int countSyn = 0;

            for(CodonEntry other : list)
            {
                if(other.getAminoAcid().equals(aminoAcid))
                {
                    totalRep += other.getCodonReplicaseCount();
                    totalSpike += other.getCodonSpikeCount();
                    countSyn++;
                }
            }

            double expectedRep;
            double expectedSpike;

            if (countSyn == 0)
            {
                expectedRep = 0;
                expectedSpike = 0;
            }
            else
            {
                expectedRep = (double) totalRep / countSyn;
                expectedSpike = (double) totalSpike / countSyn;
            }

            double rscuRep = 0;
            double rscuSpike = 0;

            if(expectedRep != 0)
            {
                rscuRep = entry.getCodonReplicaseCount() / expectedRep;
            }
            if(expectedSpike != 0)
            {
                rscuSpike = entry.getCodonSpikeCount() / expectedSpike;
            }
            entry.setReplicaseRSCU(rscuRep);
            entry.setSpikeRSCU(rscuSpike);
        }
    }


    /**
     * Calculates and sets the RSU for a CodonEntry
     * @param entry codonEntry object to set RSCU for
     * @param amino AminoAcid object to get expected frequency value from
     * @param region Which region's RSCU to set (spike or replicase)
     */
    public static void setCodonRSCU(CodonEntry entry, AminoAcid amino, Region region)
    {
        if(region == Region.SPIKE) {
            double expected = amino.calculateSpikeExpected();
            double RSCU = entry.getCodonSpikeCount() / expected;
            entry.setSpikeRSCU(RSCU);
        }
        else if(region == Region.REPLICASE)
        {
            double expected = amino.calculateReplicaseExpected();
            double RSCU = entry.getCodonReplicaseCount() / expected;
            entry.setReplicaseRSCU(RSCU);
        }
    }

    /**
     * Finds the corresponding amino acid that a particular codon codes for
     * @param entry The codon to find the amino acid for,
     *              in the form of a CodonEntry object
     * @param aminoList An Arraylist of amino acids
     * @return The amino acid that the codon codes for
     */
    public static AminoAcid findCorrespondingAA(CodonEntry entry, ArrayList<AminoAcid> aminoList)
    {
        AminoAcid entryAmino = null;
        for (AminoAcid amino: aminoList)
        {
            if(entry.getAminoAcid().equals(amino.getName()))
            {
                entryAmino = amino;
                return amino;
            }
        }
        return entryAmino;
    }

    /**
     * Generates an ArrayList of AminoAcid objects with the information from
     * an ArrayList of CodonEntry objects. Sets the AminoAcid's spike/replicase counts,
     * and sets the number of synonymous codons.
     * @param codonList An Arraylist of CodonEntry objects to get counts from
     * @return An ArrayList of AminoAcid objects with spike/replicase and synonymous codon counts
     */
    public static ArrayList<AminoAcid> getAminoList(ArrayList<CodonEntry> codonList)
    {
        ArrayList<AminoAcid> aminoList = new ArrayList<>(21);

        for(CodonEntry entry: codonList)
        {
            String name = entry.getAminoAcid();
            boolean inList = false;
            // Check if amino acid in list
            for(AminoAcid AA: aminoList)
            {
                if(name.equals(AA.getName()))
                {
                    // Increase counts for amino acid already in list
                    inList = true;
                    AA.incrementNumSynCodons();
                    AA.setSpikeCount(AA.getSpikeCount() + entry.getCodonSpikeCount());
                    AA.setReplicaseCount(AA.getReplicaseCount() + entry.getCodonReplicaseCount());
                }
            }
            if(!inList)
            {
                // Add amino acid to list and set initial information
                AminoAcid AA = new AminoAcid(name);
                aminoList.add(AA);
                AA.setSpikeCount(entry.getCodonSpikeCount());
                AA.setReplicaseCount(entry.getCodonReplicaseCount());
            }
        }
        return aminoList;
    }

    /**
     * Parses spike/replicase fasta files and sets the counts for the CodonEntry objects
     * @param list The CodonEntry ArrayList to set counts for
     * @param path  The filepath of the fasta file to read from
     * @param region The fasta file's corresponding region of the virus
     * @throws IOException
     */
    public static void parseFasta(ArrayList<CodonEntry> list, String path, Region region) throws IOException
    {
        // Open file
        File f = new File(path);
        Scanner infile = new Scanner(f);

        while(infile.hasNext())
        {
            // Read a line and loop through it three characters at a time
            String line = infile.nextLine();
            for(int i = 0; i <= line.length() - 3; i += 3)
            {
                // Construct each codon string
                String codon = line.substring(i, i + 3);

                // Find codon match in arraylist
                for (CodonEntry entry : list)
                {
                    if (codon.equals(entry.getCodonSequence()))
                    {
                        switch (region)
                        {
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
}
