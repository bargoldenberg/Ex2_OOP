import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class MyDWGTest {

    @Test
    void getNode() {
    }

    @Test
    void getEdge() {
        Point3D p1 = new Point3D(1,2,0);
        Point3D p2 = new Point3D(2,1,0);
        MyDWG g= new MyDWG();
        MyNode n1 = new MyNode(p1,0);
        MyNode n2 = new MyNode(p2,1);
        MyEdge e1 = new MyEdge(0,2,1);
        g.addNode(n1);
        g.addNode(n2);
        g.connect(n1.getKey(),n2.getKey(),2);
        System.out.println(g.getEdge(n1.getKey(),n2.getKey()));
    }

    @Test
    void addNode() {
    }

    @Test
    void connect() {
        Point3D p1 = new Point3D(1,2,0);
        Point3D p2 = new Point3D(2,1,0);
        MyDWG g= new MyDWG();
        MyNode n1 = new MyNode(p1,0);
        MyNode n2 = new MyNode(p2,1);
        MyEdge e1 = new MyEdge(0,2,1);
        g.addNode(n1);
        g.addNode(n2);
        g.connect(n1.getKey(),n2.getKey(),2);
        assertEquals(g.E.get(e1.key).getSrc(),e1.Src);
        assertEquals(g.E.get(e1.key).getDest(),e1.Dest);
        assertEquals(g.E.get(e1.key).getWeight(),e1.Weight);
    }

    @Test
    void nodeIter() {
    }

    @Test
    void edgeIter() {
        Point3D p1 = new Point3D(1,2,0);
        Point3D p2 = new Point3D(2,1,0);
        MyDWG g= new MyDWG();
        MyNode n1 = new MyNode(p1,0);
        MyNode n2 = new MyNode(p2,1);
        MyEdge e1 = new MyEdge(0,2,1);
        MyEdge e2 = new MyEdge(1,1,0);
        g.addNode(n1);
        g.addNode(n2);
        g.connect(n1.getKey(),n2.getKey(),2);
        g.connect(n2.getKey(),n1.getKey(),1);
        try {
            Iterator it = g.edgeIter();
        assertEquals(it.next().toString(),e2.toString());
        assertEquals(it.next().toString(),e1.toString());
        }catch(Exception e){
            System.out.println("The Graph was changed");
        }


    }


    @Test
    void removeNode() {
    }

    @Test
    void removeEdge() {
    }

    @Test
    void nodeSize() {
    }

    @Test
    void edgeSize() {
    }

    @Test
    void getMC() {
    }

    @Test
    void testToString() {
    }
}