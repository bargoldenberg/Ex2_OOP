import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyDWG_AlgoTest {

    @Test
    void init() {
        Point3D p1 = new Point3D(1,2,0);
        Point3D p2 = new Point3D(2,1,0);
        Point3D p3 = new Point3D(3,4,0);
        MyDWG g= new MyDWG();
        MyNode n1 = new MyNode(p1,0);
        MyNode n2 = new MyNode(p2,1);
        MyNode n3 = new MyNode(p3,2);
        MyEdge e1 = new MyEdge(0,2,1);
        MyEdge e2 = new MyEdge(1,1,0);
        g.addNode(n1);
        g.addNode(n2);
        g.connect(n1.getKey(),n2.getKey(),2);
        g.connect(n2.getKey(),n1.getKey(),1);
        MyDWG_Algo ga = new MyDWG_Algo();
        ga.init(g);
        System.out.println(ga.getGraph());
        ga.getGraph().addNode(n3);
        System.out.println(ga.getGraph());
    }

    @Test
    void getGraph() {
    }

    @Test
    void copy() {
    }

    @Test
    void isConnected() throws Exception {
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
        MyDWG_Algo ga = new MyDWG_Algo();
        ga.init(g);
        assertTrue(ga.isConnected());
        ga.getGraph().removeEdge(1,0);
        assertFalse(ga.isConnected());
        ///
        Point3D a = new Point3D(0,0,0);
        Point3D b = new Point3D(1,3,0);
        Point3D c = new Point3D(4,2,0);
        Point3D d = new Point3D(6,1,0);
        MyDWG testGraph = new MyDWG();
        MyNode node1 = new MyNode(a,0);
        MyNode node2 = new MyNode(b,1);
        MyNode node3 = new MyNode(c,2);
        MyNode node4 = new MyNode(d,3);

        testGraph.addNode(node1);
        testGraph.addNode(node2);
        testGraph.addNode(node3);
//        testGraph.addNode(node1);        **FOR LATER TESTS***
        testGraph.addNode(node4);

        testGraph.connect(0,1,1);
        testGraph.connect(1,2,1);
        testGraph.connect(2,1,1);
        testGraph.connect(2,3,1);
        testGraph.connect(2,0,1);
        testGraph.connect(3,0,1);
//        testGraph.connect(0,0,1);      ******FOR TEST LATER **********
        MyDWG_Algo g1 = new MyDWG_Algo();
        g1.init(testGraph);
        assertTrue(g1.isConnected());
        testGraph.removeEdge(3,0);
        g1.init(testGraph);
        assertFalse(g1.isConnected());
    }

    @Test
    void shortestPathDist() {
    }

    @Test
    void shortestPath() {
    }

    @Test
    void center() {
    }

    @Test
    void tsp() {
    }

    @Test
    void save() {
    }

    @Test
    void load() {
    }
}