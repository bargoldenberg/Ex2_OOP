import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyDWGTest {

    @Test
    void getNode() {
    }

    @Test
    void getEdge() {
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
        g.connect(n1.Key,n2.Key,2);
        assertEquals(g.E.get(e1.key).Src,e1.Src);
        assertEquals(g.E.get(e1.key).Dest,e1.Dest);
        assertEquals(g.E.get(e1.key).Weight,e1.Weight);
    }

    @Test
    void nodeIter() {
    }

    @Test
    void edgeIter() {
    }

    @Test
    void testEdgeIter() {
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