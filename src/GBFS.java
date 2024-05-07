package src;

import java.util.ArrayList;
import java.util.List;

public class GBFS{
    public static int node_count;

    public static List<String> GBFSfind(String start, String end){

        // reset node_count
        GBFS.node_count =  0;
        
        // Inisialisasi list penampung result
        List<String> result = new ArrayList<>();
        result.add(start);

        // boolean apakah terjadi loop
        boolean is_stuck = false;

        // cek apakah goal node
        if(start.equals(end)){
            return result;
        }

        // loop hingga stuck atau hasil ditemukan
        while(!is_stuck && !result.isEmpty()){
            String currentWord = result.getLast(); // simpul ekspan
            List<String> adjacentWords = Helper.findAdjacentWords(currentWord); // simpul-simpul hasil ekspan
            GBFS.node_count += adjacentWords.size();
            
            // temp variable untuk mencari kata dengan cost minimal
            int min_cost =  Helper.findDifference(end,adjacentWords.get(0));
            String min_cost_word = adjacentWords.get(0);
            
            // cari adjacent word dengan cost minimal
            for(String word: adjacentWords){
                int cost = Helper.findDifference(end, word);
                if(cost <= min_cost){
                    min_cost = cost;
                    min_cost_word = word;
                }
            }

            // Apabila kata yang pernah di ekspan muncul sebagai kata dengan cost minimal maka stuck (terjadi loop)
            if(result.contains(min_cost_word)){
                is_stuck = true;
            }
            result.add(min_cost_word);

            // cek apakah goal node
            if(min_cost_word.equals(end)){
                return result;
            }
        }

        // apabila stuck kirim no solution
        if(is_stuck){
            result = new ArrayList<>();
            result.add("No Solution");
        }
        return result;
    }   
    
}