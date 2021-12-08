package GraphGUI;

import Graph .*;
import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class MyGraph extends JFrame implements ActionListener {

    JButton centerb;
    JButton shortestpathb;
    JButton removenode;
    JButton selectFile;
    JTextField src;
    JTextField dst;
    JTextField node;
    MyDWG_Algo g1;
    MyDWG_Algo og;
    int source;
    int destination;
    NodeData center;
    ArrayList<NodeData> path;
    int centercounter=0;


    public MyGraph(MyDWG gr) throws Exception {
        JPanel p = new JPanel(new BorderLayout());//3,1
        //this.setContentPane(p);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Ex2-Graph-GUI");
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)size.width;
        int height = (int)size.height;
        this.setSize(width/2,width/2);
        this.setResizable(true);
        centerb = new JButton();
        src = new JTextField();
        dst = new JTextField();
        node = new JTextField();
        removenode = new JButton();
        shortestpathb = new JButton();
        selectFile = new JButton("Select file"); // initialize the selectFile Bottom.
        selectFile.setBounds(800,150,150,25); // initialize selectFile size.
        src.setBounds(700,35,75,25);
        dst.setBounds(775,35,75,25);
        node.setBounds(800,120,150,25);
        shortestpathb.setBounds(700,10,150,25);
        removenode.setBounds(800,70,150,50);
        centerb.setBounds(850,10,100,50);
        shortestpathb.setText("Shortest Path");
        removenode.setText("Remove Node");
        centerb.setText("Center");
        selectFile.addActionListener(this); // Adding select button to actionPreformed.
        shortestpathb.addActionListener(this);
        centerb.addActionListener(this);
        removenode.addActionListener(this);
        this.add(p,BorderLayout.WEST);
        this.add(shortestpathb);
        this.add(src);
        this.add(dst);
        this.add(node);
        this.add(centerb);
        this.add(removenode);
        this.add(selectFile);
        this.add(new GraphP(gr)); //// FIX
        this.setVisible(true);
    }

    public  class GraphP extends JPanel {
        double minx;
        double miny;
        double maxx;
        double maxy;
        double scalefactor=1;
        double scalefactor1 = 8;
        public GraphP(MyDWG gr) throws Exception {
            g1 = new MyDWG_Algo();
            og = new MyDWG_Algo();

            g1.init(gr);
            og.init(g1.copy());
            setminxy();
            repaint();

        }

        public void setminxy() throws Exception {
            minx = Integer.MAX_VALUE;
            miny = Integer.MAX_VALUE;
            maxx = Integer.MIN_VALUE;
            maxy = Integer.MIN_VALUE;
            Iterator<NodeData> minxit = g1.getGraph().nodeIter();
            while(minxit.hasNext()){
                NodeData n = minxit.next();
                if(n.getLocation().x()<minx){
                    minx = n.getLocation().x();
                }
            }
            Iterator<NodeData> minyit = g1.getGraph().nodeIter();
            while(minyit.hasNext()){
                NodeData m = minyit.next();
                if(m.getLocation().y()<miny){
                    miny = m.getLocation().y();
                }
            }
            Iterator<NodeData> maxxit = g1.getGraph().nodeIter();
            while(maxxit.hasNext()){
                NodeData n = maxxit.next();
                if(n.getLocation().x()>maxx){
                    maxx = n.getLocation().x();
                }
            }
            Iterator<NodeData> maxyit = g1.getGraph().nodeIter();
            while(maxyit.hasNext()){
                NodeData m = maxyit.next();
                if(m.getLocation().y()>maxy){
                    maxy = m.getLocation().y();
                }
            }


        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            double ABSx = Math.abs(minx-maxx);
            double ABSy = Math.abs(miny-maxy);
            double scalex = (getWidth()/ABSx)*0.7;
            double scaley = (getHeight()/ABSy)*0.7;

            MyNode prev=null;
            try {
                Graphics2D g2 = (Graphics2D)g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                double theta;
                Iterator<NodeData> it = g1.getGraph().nodeIter();
                while(it.hasNext()){
                    NodeData n = it.next();
                    g.setColor(new Color(11, 148, 56, 255));
                    double x =(n.getLocation().x()-minx)*scalex*0.97+30;
                    double y = (n.getLocation().y()-miny)*scaley*0.97+30;
                    String xs = ""+n.getLocation().x();
                    String ys = ""+n.getLocation().y();
                    String coord = "("+xs+","+ys+")"+", id:"+n.getKey();
                    g.fillOval((int)x-2,(int)y-2,20,20);
                    g.setColor(new Color(0, 0, 0));
                    Font f = new Font("ariel", Font.BOLD, 16);
                    g.setFont(f);
                    g.drawString(n.getKey()+"",(int)x,(int)y-10);

                }
                if(center!=null){
                    double x = (center.getLocation().x()-minx)*scalex*0.97+30;
                    double y = (center.getLocation().y()-miny)*scaley*0.97+30;
                    g.setColor(new Color(255, 0, 243));
                    g.fillOval((int)x-2,(int)y-2,20,20);
                    g.setColor(new Color(0, 0, 0));
                    g.drawString("CENTER",(int)x-10,(int)y+50);
                }
                Iterator<EdgeData> eiter = g1.getGraph().edgeIter();
                while(eiter.hasNext()){
                    EdgeData e = eiter.next();
                    double srcx = (g1.getGraph().getNode(e.getSrc()).getLocation().x()-minx)*scalex+30;
                    double srcy = (g1.getGraph().getNode(e.getSrc()).getLocation().y()-miny)*scaley+30;
                    double destx = (g1.getGraph().getNode(e.getDest()).getLocation().x()-minx)*scalex+30;
                    double desty = (g1.getGraph().getNode(e.getDest()).getLocation().y()-miny)*scaley+30;
                    g.setColor(new Color(0, 0, 0));
                    int x1 = (int)srcx;
                    int y1 = (int)srcy;
                    int x2 = (int)destx;
                    int y2 = (int)desty;
                    g2.setStroke(new BasicStroke(1));
                    g2.draw(new Line2D.Double(x1, y1, x2, y2));
                    theta = Math.atan2(y2 - y1, x2 - x1);
                    g.setColor(new Color(127, 30, 30));
                    drawArrow(g2, theta, x2, y2);
                    x1 = (int)srcx+(int)(scalex/scalefactor1);
                    y1 = (int)srcy+(int)(scaley/scalefactor1);
                    x2 = (int)destx+(int)(scalex/scalefactor1);
                    y2 = (int)desty+(int)(scaley/scalefactor1);
                }
                if(path!=null){
                    for(int i =0;i<path.size()-1;i++){
                        System.out.println(path.size());
                        EdgeData curredge = g1.getGraph().getEdge(path.get(i).getKey(),path.get(i+1).getKey());
                        double srcx = (g1.getGraph().getNode(curredge.getSrc()).getLocation().x()-minx)*scalex+30;
                        double srcy = (g1.getGraph().getNode(curredge.getSrc()).getLocation().y()-miny)*scaley+30;
                        double destx = (g1.getGraph().getNode(curredge.getDest()).getLocation().x()-minx)*scalex+30;
                        double desty = (g1.getGraph().getNode(curredge.getDest()).getLocation().y()-miny)*scaley+30;
                        int x1 = (int)srcx;
                        int y1 = (int)srcy;
                        int x2 = (int)destx;
                        int y2 = (int)desty;
                        System.out.println(x1);
                        System.out.println(x2);
                        g.setColor(new Color(65, 255, 0));
                        g2.draw(new Line2D.Double(x1, y1, x2, y2));
                        g.setColor(new Color(0, 0, 0));
                        ((Graphics2D) g).setStroke(new BasicStroke(5));
                        g2.drawString(curredge.getWeight()+"",(x1+x2)/2,(y1+y2)/2);

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==centerb){
            try {
                if(centercounter%2==0) {
                    NodeData a = g1.center();
                    this.center = a;
                    centercounter++;
                    repaint();
                }else{
                    this.center=null;
                    centercounter++;
                    repaint();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }else if(e.getSource()==shortestpathb) {
            if (src.getText().length()==0|| dst.getText().length()==0) {
                path = null;
                System.out.println("hi");
                repaint();
            } else {
                source = Integer.parseInt(src.getText());
                destination = Integer.parseInt(dst.getText());
                path = (ArrayList<NodeData>) g1.shortestPath(source, destination);
                repaint();
            }
        }else if(e.getSource()==removenode){
            if(node.getText().length()==0){
                g1.init(og.copy());
                repaint();
            }else {
                int rmvnode = Integer.parseInt(node.getText());
                this.g1.getGraph().removeNode(rmvnode);
                repaint();
            }
        }else if(e.getSource()==selectFile){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            int response = fileChooser.showOpenDialog(null); // select file to Open.
            if(response == JFileChooser.APPROVE_OPTION){
                String jsonPath = fileChooser.getSelectedFile().getAbsolutePath();
                this.g1.load(jsonPath);
                try {
                    runGUI((MyDWG) this.g1.getGraph());
                    setVisible(false); //you can't see me!
                    dispose();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
//                System.out.println(jsonPath);  // For Test
            }
        }
    }

    // taken from https://coderanch.com/t/339505/java/drawing-arrows
    private void drawArrow(Graphics2D g2, double theta, double x0, double y0)
    {
        double barb =20;
        double phi = Math.PI/6;
        double x = x0 - barb * Math.cos(theta + phi);
        double y = y0 - barb * Math.sin(theta + phi);
        g2.setStroke(new BasicStroke(3));
        g2.draw(new Line2D.Double(x0, y0, x, y));
        x = x0 - barb * Math.cos(theta - phi);
        y = y0 - barb * Math.sin(theta - phi);
        g2.draw(new Line2D.Double(x0, y0, x, y));
    }
    public static void runGUI(MyDWG gr) throws Exception {
        new MyGraph(gr);
    }

    public static void main(String[] args) throws Exception {
        Point3D p0 = new Point3D(0,0,0);
        Point3D p1 = new Point3D(1,2,0);
        Point3D p2 = new Point3D(1,5,0);
        Point3D p3 = new Point3D(4,4,0);
        Point3D p4 = new Point3D(4,3,0);
        Point3D p5 = new Point3D(4,0,0);
        Point3D p6 = new Point3D(9,2,0);
        MyDWG g= new MyDWG();
        MyNode n0 = new MyNode(p0,0);
        MyNode n1 = new MyNode(p1,1);
        MyNode n2 = new MyNode(p2,2);
        MyNode n3 = new MyNode(p3,3);
        MyNode n4 = new MyNode(p4,4);
        MyNode n5 = new MyNode(p5,5);
        MyNode n6 = new MyNode(p6,6);

        g.addNode(n0);
        g.addNode(n1);
        g.addNode(n2);
        g.addNode(n3);
        g.addNode(n4);
        g.addNode(n5);
        g.addNode(n6);

        g.connect(n0.getKey(),n1.getKey(),1);
        g.connect(n1.getKey(),n2.getKey(),1);
        g.connect(n2.getKey(),n1.getKey(),2);
        g.connect(n2.getKey(),n3.getKey(),2);
        g.connect(n3.getKey(),n4.getKey(),1);
        g.connect(n4.getKey(),n3.getKey(),1);
        g.connect(n2.getKey(),n4.getKey(),4);
        g.connect(n4.getKey(),n2.getKey(),2);
        g.connect(n4.getKey(),n6.getKey(),5);
        g.connect(n0.getKey(),n6.getKey(),15);
        g.connect(n5.getKey(),n6.getKey(),12);

        runGUI(null);
    }
}
