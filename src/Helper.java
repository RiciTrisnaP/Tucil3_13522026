package src;

import java.util.ArrayList;
import java.util.List;

public class Helper{

    // Mencari semua kata beda sehuruf yang terdaftar dalam dictionary
    public static List<String> findAdjacentWords(String seed){

        char alphabet[] = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'}; // list alphabet

        List<String> result = new ArrayList<String>(); // penampung kata

        for(int i = 0; i < seed.length(); i++){

            StringBuilder strb = new StringBuilder(seed);

            // mencari semua kombinasi kata beda 1 huruf
            for(char letter: alphabet){
                strb.setCharAt(i,letter);
                String test = strb.toString();

                // validasi kata
                if(Dictionary.dictionary.contains(test) && !test.equals(seed)){
                    result.add(test);
                }

            }

        }
        return result;
    }

    // Mencari jumlah beda huruf dari dua string
    public static int findDifference(String s1, String s2){
        int result = 0;
        for(int i = 0; i < s1.length(); i++){
            if(s1.charAt(i) != s2.charAt(i)){
                result++;
            }
        }
        return result;
    }
}