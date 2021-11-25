package GraphGUI;
import api.DirectedWeightedGraph;

import javax.swing.*;
import java.awt.*;

class GUI{
    public GUI(){
    }
    public void runGUI(DirectedWeightedGraph g){
        JFrame frame = new JFrame("GraphGUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)size.width;
        int height = (int)size.height;
        frame.setSize(width/3,height/3);
        JButton button1 = new JButton("Fuck You Sappir");
        frame.getContentPane().add(button1);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        GUI a = new GUI();
        a.runGUI(null);
    }
}
