package src;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Dictionary {
    public static Set<String> dictionary;

    // Pembaca dictionary
    public static void readDictionary(){
        dictionary = new HashSet<String>(); // Menampung setiap kata dalam dictionary
        // Membaca dictionary
        try{
            FileInputStream fis = new FileInputStream("dictionary.txt");
            Scanner sc = new Scanner(fis);
            while(sc.hasNextLine()){  
                dictionary.add(sc.nextLine()); 
            }  
            sc.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
}
