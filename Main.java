import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

enum FileType { SPIKE, REPLICASE }

public class Main {
    public static void main(String[] args) throws IOException{

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
