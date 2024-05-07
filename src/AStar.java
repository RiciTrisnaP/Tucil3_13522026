package src;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
public class AStar {

    public static int node_count;

     public static List<String> AStarFind(String start,String end){

        // reset node_count
        AStar.node_count = 0;

        // Map untuk menyimpan bobot terkecil untuk suatu kata
        Map<String, Integer> map = new HashMap<>();

        // Priority queue untuk simpul hidup
        Queue<AStarNode> prioqueue = new PriorityQueue<AStarNode>(new AstarNodeComparator());

        // List untuk menampung hasil rantaian kata
        List<String> result = new ArrayList<>();  

        // Menginisialisasi prioqueue dengan simpul yang berisi start word
        AStarNode root = new AStarNode(start);
        prioqueue.add(root);
        map.put(start, 0);


        while(prioqueue.size() > 0){
            // Simpul ekspan
            AStarNode currentNode = prioqueue.poll();
            String currentWord = currentNode.word;

            // apabila simpul dengan bobot terendah merupakan goal node
            if(currentWord.equals(end)){
                return AStarNode.findPath(currentNode); // solusi
            }

            // Cari semua kata yang beda satu huruf dengan simpul ekspan
            List<String> adjacentWords = Helper.findAdjacentWords(currentWord);
            AStar.node_count += adjacentWords.size();

            // Untuk setiap kata beda sehuruf diproses
            for(String word : adjacentWords){
                // Membuat simpul untuk tiap adjacent word dan memasukkan ke prioqueue
                int cost_from_root = currentNode.cost_from_root + Helper.findDifference(currentWord, word); // Hitung h(n)
                int cost_to_goal = Helper.findDifference(end, word);
                int total_cost = cost_from_root + cost_to_goal; // Hitung cost total (h(n) + g(n))
                if(!map.containsKey(word) || map.get(word) > total_cost){
                    AStarNode newNode = new AStarNode(cost_from_root, total_cost, word, currentNode);
                    prioqueue.add(newNode);
                    map.put(word, total_cost);
                }
            } 
        }

        // Tidak ditemukan solusi
        result.add("No Solution");
        return result;
    }
}

class AstarNodeComparator implements Comparator<AStarNode>{
    // Comparator untuk fungsi add priority queue secara ascending
    public int compare(AStarNode as1, AStarNode as2){
        if(as1.total_cost > as2.total_cost){
            return 1;
        } else if (as1.total_cost < as2.total_cost){
            return -1;
        }
        return 0;
    }
}

class AStarNode{

    public int cost_from_root;
    public int total_cost;
    public String word;
    public AStarNode parent;

    // Constructor
    public AStarNode(String word){
        this.cost_from_root = 0;
        this.total_cost = 0;
        this.word = word;
        this.parent = null;
    }

    public AStarNode(int cost_from_root, int total_cost, String word, AStarNode parent){
        this.cost_from_root = cost_from_root;
        this.total_cost = total_cost;
        this.word = word;
        this.parent = parent;
    }


    // Mencari path dari root ke goal node
    public static List<String> findPath(AStarNode node){
        List<String> result = new ArrayList<>();
        while(node != null){
            result.add(node.word);
            node = node.parent;
        }
        result = result.reversed();
        return result;
    }
}