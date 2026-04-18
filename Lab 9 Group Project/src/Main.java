import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

enum FileType { SPIKE, REPLICASE }

public class Main {
    public static void main(String[] args) throws IOException{
        
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
            double RSCU = entry.getSpikeCount() / expected;
            entry.setRSCUSpike(RSCU);
        }
        else if(region == Region.REPLICASE)
        {
            double expected = amino.calculateReplicaseExpected();
            double RSCU = entry.getReplicaseCount() / expected;
            entry.setRSCUReplicase(RSCU);
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
            if(entry.getAminoName().equals(amino.getName()))
            {
                entryAmino = amino;
                return amino;
            }
        }
        return entryAmino;
    }

    public static ArrayList<AminoAcid> getAminoList(ArrayList<CodonEntry> codonList)
    {
        ArrayList<AminoAcid> aminoList = new ArrayList<>(21);

        for(CodonEntry entry: codonList)
        {
            String name = entry.getAminoName();
            boolean inList = false;
            // Check if amino acid in list
            for(AminoAcid AA: aminoList)
            {
                if(name.equals(AA.getName()))
                {
                    // Increase counts for amino acid already in list
                    inList = true;
                    AA.incrementNumSynCodons();
                    AA.setSpikeInstances(AA.getSpikeInstances() + entry.getSpikeCount());
                    AA.setReplicaseInstances(AA.getReplicaseInstances() + entry.getReplicaseCount());
                }
            }
            if(!inList)
            {
                // Add amino acid to list and set initial information
                AminoAcid AA = new AminoAcid(name);
                aminoList.add(AA);
                AA.setSpikeInstances(entry.getSpikeCount());
                AA.setReplicaseInstances(entry.getReplicaseCount());
            }
        }
        return aminoList;
    }
    
public static void parseFasta(ArrayList<CodonEntry> list, String path, FileType type) throws IOException
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
                // Loop to construct each codon string
                String codon = "";
                for(int k = i; k < i + 3; k++)
                {
                    codon += line.charAt(k);
                }
                // Find codon match in arraylist
                for (CodonEntry entry : list)
                {
                    if (codon.equals(entry.getCodonSequence()))
                    {
                        switch (type)
                        {
                            case SPIKE -> entry.incrementSpike();
                            case REPLICASE -> entry.incrementReplicase();
                        }
                    }
                }
            }
        }
        // Close file
        infile.close();
    }
}
