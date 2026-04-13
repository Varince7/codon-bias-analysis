import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;
//This method runs the codon trial
enum FileType { SPIKE, REPLICASE }

public class Main {
    public static void main(String[] args) throws IOException{

    }

    public static void parseFasta(String path, ArrayList<CodonEntry> list, FileType type) throws IOException{
        File f = new File(path);
        Scanner infile = new Scanner(f);

        while(infile.hasNext()){
            String line = infile.nextLine();
            for (int i = 0; i < line.length() - 3; i += 3){

                String codon = "";
                for(int k = i; k < i + 3; k++){
                    codon += line.charAt(k);
                }

                for(CodonEntry entry: list){
                    if(codon.equalsIgnoreCase(entry.getSequence())){
                        switch(type){
                            case SPIKE -> entry.incrementSpike();
                            case REPLICASE -> entry.incrementReplicase();
                        }
                    }
                }
            }
        }
    }
}
