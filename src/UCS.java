package src;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class UCS {

    public static int node_count;

    public static List<String> UCSFind(String start,String end){

        // reset node_count
        UCS.node_count = 0;

        // Priority queue untuk simpul hidup
        Queue<UCSNode> prioqueue = new PriorityQueue<UCSNode>(new UCSNodeComparator());

        // HashSet untuk mencatat kata yang sudah pernah diekspan
        Set<String> expanded_word = new HashSet<String>();

        // List untuk menampung hasil rantaian kata
        List<String> result = new ArrayList<>();  

        // Menginisialisasi prioqueue dengan simpul yang berisi start word
        UCSNode root = new UCSNode(start);
        prioqueue.add(root);


        while(prioqueue.size() > 0){
            // Simpul ekspan
            UCSNode currentNode = prioqueue.poll();
            String currentWord = currentNode.word;

            // Cek apakah goal node
            if(currentWord.equals(end)){
                result = UCSNode.findPath(currentNode); // return path dari root ke goal node
                return result;
            }

            // Masukkan simpul ekspan ke dalam set
            expanded_word.add(currentWord);

            // Cari semua kata yang beda satu huruf dengan simpul ekspan
            List<String> adjacentWords = Helper.findAdjacentWords(currentWord);
            UCS.node_count += adjacentWords.size();

            // Untuk setiap kata beda sehuruf diproses
            for(String word : adjacentWords){
                // Membuat simpul untuk tiap adjacent word
                int cost = currentNode.cost + Helper.findDifference(currentWord, word); // Hitung cost g(n) dari simpul
                UCSNode newNode = new UCSNode(cost, word, currentNode);
                
                // jika sudah pernah di ekspan maka tidak ditambah ke prioqueue
                if(!expanded_word.contains(word)){
                    prioqueue.add(newNode);
                }
            }
        }

        // Tidak ditemukan solusi
        result.add("No Solution");
        return result;
    }
}

class UCSNodeComparator implements Comparator<UCSNode>{
    // Comparator untuk fungsi add priority queue
    public int compare(UCSNode ucs1, UCSNode ucs2){
        if(ucs1.cost > ucs2.cost){
            return 1;
        } else if (ucs1.cost < ucs2.cost){
            return -1;
        }
        return 0;
    }
}

class UCSNode{

    public int cost;
    public String word;
    public UCSNode parent;

    // Constructor
    public UCSNode(String word){
        this.cost = 0;
        this.word = word;
        this.parent = null;
    }

    public UCSNode(int cost, String word, UCSNode parent){
        this.cost = cost;
        this.word = word;
        this.parent = parent;
    }


    // Mencari path dari root ke goal node
    public static List<String> findPath(UCSNode node){
        List<String> result = new ArrayList<>();
        while(node != null){
            result.add(node.word);
            node = node.parent;
        }
        result = result.reversed();
        return result;
    }
}