import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.time.format.TextStyle;

public class CodonEntry 
{
    public static void main(String[] args)
    {
        ArrayList<CodonEntry> codonList = readCodonFile("codons.csv");
    }
    
    public static ArrayList<CodonEntry> readCodonFile(String filename)
    {
        ArrayList<CodonEntry> list = new ArrayList<>();
        {
            Test {
                File f = new File(filename);
                Scanner reader = new Scanner (file);

                while(reader.hasNextLine())
                {
                    String line = reader.nextLine();
                    String[] parts = line.split(",");

                    if(parts.length >= ___)
                    {
                        CodonEntry entry = new CodonEntry(_____);
                        list.add(entry);
                    }
                }
                reader.close();
            }
            catch (Exception e)
            {
                System.out.println("Error___");
            }
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
                    totalRep += other.getRepCount();
                    totalSpike += other.getSpikeCount();
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
                rscuRep = entry.getRepCount() / expectedRep;
            }
            if(expectedSpike != 0)
            {
                rscuSpike = entry.getSpikeCount() / expectedSpike;
            }
            entry.setRSCURep(rscuRep);
            entry.setRSCUSpike(rscuSpike);
        }
    }
    }
  
}
