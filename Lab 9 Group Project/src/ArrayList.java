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
    }
  
}
