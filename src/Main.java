package src;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Main extends JFrame{

    public String selected;
    public List<String> result;
    public String start;
    public String end;
    public Long time;
    public int node_count;
    public int solution_length;

    public Main(){
        this.selected = "UCS";
        this.result = new ArrayList<>();
        this.start = "";
        this.end = "";
        this.time = 0L;
        this.node_count =  0;
    }

    public void Draw(){
        // Container
        this.setSize(1000,600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        
        // Component
        JLabel title = new JLabel("Word Ladder");
        title.setBounds(400, 50, 300, 50);
        title.setFont(new Font("Calibri", Font.PLAIN, 30));
        this.add(title);
        
        JLabel startLabel = new JLabel("Start :");
        startLabel.setBounds(20, 150, 200, 50);
        startLabel.setFont(new Font("Calibri", Font.PLAIN, 30));
        this.add(startLabel);
        
        JLabel endLabel = new JLabel("End :");
        endLabel.setBounds(20, 250, 200, 50);
        endLabel.setFont(new Font("Calibri", Font.PLAIN, 30));
        this.add(endLabel);
        
        JTextField startField = new JTextField();
        startField.setBounds(120, 150, 250, 50);
        startField.setFont(new Font("Calibri", Font.PLAIN, 30));
        this.add(startField);

        startField.setText(start);
        
        JTextField endField = new JTextField();
        endField.setBounds(120, 250, 250, 50);
        endField.setFont(new Font("Calibri", Font.PLAIN, 30));
        this.add(endField);

        endField.setText(end);
        
        JLabel methodLabel = new JLabel("Algorithm :");
        methodLabel.setBounds(20, 350, 200, 50);
        methodLabel.setFont(new Font("Calibri", Font.PLAIN, 30));
        this.add(methodLabel);
        
        String algo[] = {"UCS", "GBFS", "A*"};
        
        JComboBox<String> method = new JComboBox<>(algo);

        method.setSelectedItem(selected);
        
        ActionListener cbActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                String s = (String) method.getSelectedItem();
                switch (s) {
                    case "A*":
                        selected = "A*";
                        break;
                    case "GBFS":
                        selected = "GBFS";
                        break;
                    default:
                        selected = "UCS";
                        break;
                }
            }
        };

        method.addActionListener(cbActionListener);

        method.setBounds(200, 350, 170, 50);
        method.setFont(new Font("Calibri", Font.PLAIN, 25));
        this.add(method);
        
        JButton startButton = new JButton("Start");
        startButton.setBounds(100, 450, 170, 50);
        startButton.setFont(new Font("Calibri", Font.PLAIN, 25));
        this.add(startButton);

        ActionListener sbActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                
                start = startField.getText().toLowerCase();
                end = endField.getText().toLowerCase();
                    
                long startTime = System.currentTimeMillis();
                switch (selected) {
                    case "A*":
                        result = AStar.AStarFind(start,end);
                        time = System.currentTimeMillis() - startTime;
                        node_count = AStar.node_count;
                        solution_length = result.size();
                        getContentPane().removeAll();
                        Draw();
                        revalidate();
                        repaint();
                        break;
                    case "GBFS":
                        result = GBFS.GBFSfind(start,end);
                        time = System.currentTimeMillis() - startTime;
                        node_count = GBFS.node_count;
                        solution_length = result.size();
                        getContentPane().removeAll();
                        Draw();
                        revalidate();
                        repaint();
                        break;
                    default:
                        result = UCS.UCSFind(start,end);
                        time = System.currentTimeMillis() - startTime;
                        node_count = UCS.node_count;
                        solution_length = result.size();
                        getContentPane().removeAll();
                        Draw();
                        revalidate();
                        repaint();
                        break;
                } 
            }
        };

        startButton.addActionListener(sbActionListener);
        
        JPanel resultPane = new JPanel();
        resultPane.setLayout(null);
        resultPane.setBounds(0,0,400,250);
        
        JScrollPane wrapper = new JScrollPane(resultPane);
        wrapper.setBounds(500, 150, 400, 200);
        this.add(wrapper);
    
        
        int start = 20;
        int i = 0;
        for(String elm : result){
            JLabel temp = new JLabel(elm);
            resultPane.add(temp);
            temp.setBounds(start, 20 + 50 * i, 200, 50);
            temp.setFont(new Font("Calibri", Font.PLAIN, 30));
            i = i + 1;
        }
        resultPane.setPreferredSize(new Dimension(380,20 + 50 * i));

        JLabel lengthLabel = new JLabel("length : " + this.solution_length + " word");
        lengthLabel.setBounds(500, 370, 500, 50);
        lengthLabel.setFont(new Font("Calibri", Font.PLAIN, 30));
        this.add(lengthLabel);

        JLabel timeLabel = new JLabel("elapsed : " + this.time + " ms");
        timeLabel.setBounds(500, 420, 500, 50);
        timeLabel.setFont(new Font("Calibri", Font.PLAIN, 30));
        this.add(timeLabel);

        JLabel nodeLabel = new JLabel("visited node : " + this.node_count + " node");
        nodeLabel.setBounds(500, 470, 500, 50);
        nodeLabel.setFont(new Font("Calibri", Font.PLAIN, 30));
        this.add(nodeLabel);
        
        
        this.setVisible(true);
    }

    public static void main(String[] args) {

        Dictionary.readDictionary();
        Main main = new Main();
        main.Draw();
    }
}