package GraphGUI;

import Graph .*;
import api.EdgeData;
import api.NodeData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.LinkedList;



public class MyGraph extends JFrame {
    public MyGraph(MyDWG gr) throws Exception {
        this.add(new GraphP(gr));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)size.width;
        int height = (int)size.height;
        this.setSize(height/2,height/2);
//        this.add(new GraphP());
        this.setVisible(true);
    }

    public  class GraphP extends JPanel {

        MyDWG_Algo g1 = new MyDWG_Algo();
        double minx;
        double miny;
        double maxx;
        double maxy;
        double scalefactor=1;
        double scalefactor1 = 8;
        public GraphP(MyDWG gr) throws Exception {
            g1.init(gr);
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

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            double ABSx = Math.abs(minx-maxx)+1;
            double ABSy = Math.abs(miny-maxy)+1;
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
                    g.setColor(new Color(255, 0, 0));
                    double x =(n.getLocation().x()-minx)*scalex*0.97+30;
                    double y = (n.getLocation().y()-miny)*scaley*0.97+30;
                    x+=scalex/scalefactor;
                    y+=scaley/scalefactor;
                    String xs = ""+n.getLocation().x();
                    String ys = ""+n.getLocation().y();
                    String coord = "("+xs+","+ys+")"+", id:"+n.getKey();
                    g.fillOval((int)x-4,(int)y-4,20,20);
                    g.setColor(new Color(0, 0, 0));
                    //g.setPaintMode();
                    g.drawString(coord,(int)x,(int)y-(int)scaley/16);
                }
                Iterator<EdgeData> eiter = g1.getGraph().edgeIter();
                while(eiter.hasNext()){
                    EdgeData e = eiter.next();
                    String weight = ""+(int)e.getWeight();
                    double srcx = (g1.getGraph().getNode(e.getSrc()).getLocation().x()-minx)*scalex+30;
                    double srcy = (g1.getGraph().getNode(e.getSrc()).getLocation().y())*scaley+30;
                    double destx = (g1.getGraph().getNode(e.getDest()).getLocation().x())*scalex+30;
                    double desty = (g1.getGraph().getNode(e.getDest()).getLocation().y())*scaley+30;
                    srcx+=scalex/scalefactor;
                    srcy+=scaley/scalefactor;
                    destx+=scalex/scalefactor;
                    desty+=scaley/scalefactor;
                    g.setColor(new Color(0, 72, 255));
                    int x1 = (int)srcx;//+(int)(scalex/scalefactor1);
                    int y1 = (int)srcy;//+(int)(scaley/scalefactor1);
                    int x2 = (int)destx;//+(int)(scalex/scalefactor1);
                    int y2 = (int)desty;//+(int)(scaley/scalefactor1);
                    //g.drawLine(x1,y1,x2,y2);
                    g2.draw(new Line2D.Double(x1, y1, x2, y2));
                    theta = Math.atan2(y2 - y1, x2 - x1);
                    drawArrow(g2, theta, x2, y2);
                    x1 = (int)srcx+(int)(scalex/scalefactor1);
                    y1 = (int)srcy+(int)(scaley/scalefactor1);
                    x2 = (int)destx+(int)(scalex/scalefactor1);
                    y2 = (int)desty+(int)(scaley/scalefactor1);
                    g.drawString(weight, (int)((x1+x2)/2),(int)((y2+y1)/2));
                }
            } catch (Exception e) {
                e.printStackTrace();
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

        runGUI(g);
    }
}
