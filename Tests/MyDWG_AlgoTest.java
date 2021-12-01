import Graph.*;
import api.DirectedWeightedGraph;
import api.NodeData;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
        assertEquals(g,ga.getGraph());
    }

    @Test
    void copy() {
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
        DirectedWeightedGraph g1 = ga.copy();
        assertNotEquals(g1,ga.getGraph());

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

        MyDWG_Algo testGraphAlgo = new MyDWG_Algo();
        testGraphAlgo.init(g);
        assertEquals(3,testGraphAlgo.shortestPathDist(2,4));
        assertEquals(1,testGraphAlgo.shortestPathDist(0,1));
        assertEquals(10,testGraphAlgo.shortestPathDist(0,6));
        assertEquals(12,testGraphAlgo.shortestPathDist(5,6));
        assertEquals(-1,testGraphAlgo.shortestPathDist(0,5));
        assertEquals(-1,testGraphAlgo.shortestPathDist(2,0));
        assertEquals(-1,testGraphAlgo.shortestPathDist(1,0));
    }

    @Test
    void shortestPath() {
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

        MyDWG_Algo testGraphAlgo = new MyDWG_Algo();
        testGraphAlgo.init(g);
        List<NodeData> l1 = testGraphAlgo.shortestPath(2,4);
        List<NodeData> l2 = testGraphAlgo.shortestPath(0,6);
        List<NodeData> l3 = testGraphAlgo.shortestPath(2,0);
        List<NodeData> l4 = testGraphAlgo.shortestPath(0,5);

        assertEquals(2,l1.get(0).getKey());
        assertEquals(3,l1.get(1).getKey());
        assertEquals(4,l1.get(2).getKey());

        assertEquals(0,l2.get(0).getKey());
        assertEquals(1,l2.get(1).getKey());
        assertEquals(2,l2.get(2).getKey());
        assertEquals(3,l2.get(3).getKey());
        assertEquals(4,l2.get(4).getKey());
        assertEquals(6,l2.get(5).getKey());

        assertEquals(null,l3);
        assertEquals(null,l4);
    }

    @Test
    void center() throws Exception {
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
        assertEquals(g1.center().getKey(),node3.getKey());
    }

    @Test
    void tsp() {
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

        MyDWG_Algo testGraphAlgo = new MyDWG_Algo();
        testGraphAlgo.init(g);
        List<NodeData> subGraph1 = new ArrayList<NodeData>();
        subGraph1.add(n3);
        subGraph1.add(n4);
        subGraph1.add(n2);
        String t = subGraph1.toString();
        List<NodeData> subGraphTSP1 = testGraphAlgo.tsp(subGraph1);
        String t1 = subGraphTSP1.toString();
        assertEquals(t,t1);



    }

    @Test
    void save() {
    }

    @Test
    void load() {
    }
    @Test
    void generateGraph() throws Exception {
        MyDWG a = new MyDWG();
        MyDWG_Algo at = new MyDWG_Algo();
        at.init(at.generateGraph(1000));
        assertTrue(at.isConnected());
    }
    @Test
    void testGraphAlgo() throws Exception {
        MyDWG_Algo testGraphAlgo = new MyDWG_Algo();
        MyDWG g2 = testGraphAlgo.generateGraph(1000);
        testGraphAlgo.init(g2);
        //System.out.println(testGraphAlgo.center());
        System.out.println(testGraphAlgo.center());
    }
}