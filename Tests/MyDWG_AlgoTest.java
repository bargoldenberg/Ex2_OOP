import Graph.*;
import api.DirectedWeightedGraph;
import api.NodeData;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MyDWG_AlgoTest {


    @Test
    /**
    Testing the init() function A.K.A loading the graph to the program. (checking to see that we dont raise exceptions)
     */
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
    /**
    getGraph() test, using init() to load the graph to the program and getGraph() to download graph from program.
     */
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
        assertEquals(g.toString(),ga.getGraph().toString());
    }

    /**
     * Testing the copy() function which creates a Deep copy of the graph.
     */
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
        assertNotEquals(g1,ga.getGraph());//this is to test the graphs are not in the same memory space.

    }

    /**
     * Testing isConnected() function which checks if graph is Strongly connected.
     * @throws Exception
     */
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
        boolean a2 = ga.isConnected();
        ga.getGraph().removeEdge(1,0);
        boolean b2 = ga.isConnected();

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
//        g1.load("/home/bar/Desktop/Ex2_OOP/Ex2_OOP/10000Nodes.json");
//        System.out.println(g1.isConnected());
    }

    /**
     * testing shortestPathDist() function
     * which returns the distance of the shortest path between two given nodes.
     */
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

    /**
     * Tests the shortestPath() method.
     */
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
        /**
         * Add Nodes to graph.
         */
        g.addNode(n0);
        g.addNode(n1);
        g.addNode(n2);
        g.addNode(n3);
        g.addNode(n4);
        g.addNode(n5);
        g.addNode(n6);
        /**
         * Create Edges.
         */
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

    /**
     * center of the graph - the Node whose max distance from any other node is minimal.
     * testing the center() method.
     * @throws Exception
     */
    @Test
    void center() throws Exception {
        Point3D a = new Point3D(0,+
                0,0);
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
        /**
         * Test 1 for TSP:
         *      this test is for a small group (3 Nodes).
         *      best case is 3, worst case is 8, because it's a small numbers i will check if the path TSP will bring
         *      back is no bigger than 3*1.5.
         */
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
        //testGraphAlgo.save("TestTSP1.json");
        List<NodeData> subGraph1 = new ArrayList<NodeData>();
        subGraph1.add(n3);
        subGraph1.add(n4);
        subGraph1.add(n2);
        ArrayList<NodeData> subGraphTSP1 = (ArrayList<NodeData>) testGraphAlgo.tsp(subGraph1);
        double pathW = 0.0;
        for(int i=0; i<=subGraphTSP1.size()-2; i++){
            pathW += testGraphAlgo.shortestPathDist(subGraphTSP1.get(i).getKey(),subGraphTSP1.get(i+1).getKey());
        }
        assertTrue(pathW <= 3*1.5);
        assertFalse(pathW <= 2); // because 3 is the Best case, it will never be smaller then that.

        /**
         * Test 2 for TSP:
         *      this test is for 4 nodes group.
         *      best case: d(2->3->1->5) = 20.74.
         *      worst case: d(3->1->2->5) = 36.7.
         *      I will check if the path the algorithm.
         */
        Point3D P0 = new Point3D(0.0,0.0,0.0);
        Point3D P1 = new Point3D(1.0,1.0,0.0);
        Point3D P2 = new Point3D(1.0,5.0,0.0);
        Point3D P3 = new Point3D(3.0,2.0,0.0);
        Point3D P4 = new Point3D(4.0,1.0,0.0);
        Point3D P5 = new Point3D(6.0,2.0,0.0);
        MyDWG G = new MyDWG();
        MyNode N0 = new MyNode(P0,0);
        MyNode N1 = new MyNode(P1,1);
        MyNode N2 = new MyNode(P2,2);
        MyNode N3 = new MyNode(P3,3);
        MyNode N4 = new MyNode(P4,4);
        MyNode N5 = new MyNode(P5,5);
        G.addNode(N0);
        G.addNode(N1);
        G.addNode(N2);
        G.addNode(N3);
        G.addNode(N4);
        G.addNode(N5);
        G.connect(N0.getKey(),N1.getKey(),1.0);
        G.connect(N1.getKey(),N2.getKey(),10.23);
        G.connect(N2.getKey(),N3.getKey(),8.91);
        G.connect(N3.getKey(),N1.getKey(),5.73);
        G.connect(N1.getKey(),N5.getKey(),6.1);
        G.connect(N5.getKey(),N1.getKey(),7.0);
        G.connect(N5.getKey(),N3.getKey(),0.5);
        G.connect(N3.getKey(),N4.getKey(),5.2);
        G.connect(N4.getKey(),N5.getKey(),4.7);
        MyDWG_Algo gAlgo =new MyDWG_Algo();
        gAlgo.init(G);
        //gAlgo.save("TestTSP2.json");
        List<NodeData> subGraph2 = new ArrayList<NodeData>();
        subGraph2.add(N1);
        subGraph2.add(N2);
        subGraph2.add(N3);
        subGraph2.add(N5);
        ArrayList<NodeData> subGraphTSP2 = (ArrayList<NodeData>) gAlgo.tsp(subGraph2);
        pathW = 0.0;
        for(int i=0; i<=subGraphTSP2.size()-2; i++){
            pathW += testGraphAlgo.shortestPathDist(subGraphTSP2.get(i).getKey(),subGraphTSP2.get(i+1).getKey());
        }
        assertTrue(pathW <= 20.74*1.25); // see if my algorithm path is at least With a deviation of 25% from the best path.
        assertFalse(pathW >= 36.7); // because 3 is the Best case, it will never be smaller then that.
        /**
         * Test for G1.json
         *
         */
        List<NodeData>nodes=new ArrayList<NodeData>();
        MyDWG_Algo givenJson = new MyDWG_Algo();
        givenJson.load("/home/bar/Desktop/Projects for Informatica/Ex2_OOP/data/G1.json");
        nodes.add(givenJson.getGraph().getNode(1));
        nodes.add(givenJson.getGraph().getNode(2));
        nodes.add(givenJson.getGraph().getNode(3));
        nodes.add(givenJson.getGraph().getNode(4));
        nodes.add(givenJson.getGraph().getNode(5));
        ArrayList<NodeData> G1 = (ArrayList<NodeData>) givenJson.tsp(nodes);
        pathW = 0.0;
        for(int i=0; i<=G1.size()-2; i++){
            pathW += givenJson.shortestPathDist(G1.get(i).getKey(), G1.get(i+1).getKey());
        }
        System.out.println("G1 Test tsp: "+pathW);
        /**
         * Test for G3.json,
         * looking for path between 20 points (from total 47 points).
         *
         */
        nodes.clear();
        MyDWG_Algo givenJson3 = new MyDWG_Algo();
        givenJson.load("/home/bar/Desktop/Projects for Informatica/Ex2_OOP/data/G3.json");
        nodes.add(givenJson.getGraph().getNode(1));
        nodes.add(givenJson.getGraph().getNode(2));
        nodes.add(givenJson.getGraph().getNode(3));
        nodes.add(givenJson.getGraph().getNode(4));
        nodes.add(givenJson.getGraph().getNode(5));
        nodes.add(givenJson.getGraph().getNode(6));
        nodes.add(givenJson.getGraph().getNode(7));
        nodes.add(givenJson.getGraph().getNode(8));
        nodes.add(givenJson.getGraph().getNode(9));
        nodes.add(givenJson.getGraph().getNode(10));
        nodes.add(givenJson.getGraph().getNode(11));
        nodes.add(givenJson.getGraph().getNode(12));
        nodes.add(givenJson.getGraph().getNode(13));
        nodes.add(givenJson.getGraph().getNode(14));
        nodes.add(givenJson.getGraph().getNode(15));
        nodes.add(givenJson.getGraph().getNode(16));
        nodes.add(givenJson.getGraph().getNode(17));
        nodes.add(givenJson.getGraph().getNode(20));
        nodes.add(givenJson.getGraph().getNode(30));
        nodes.add(givenJson.getGraph().getNode(40));
        ArrayList<NodeData> G2 = (ArrayList<NodeData>) givenJson.tsp(nodes);
        pathW = 0.0;
        for(int i=0; i<=G2.size()-2; i++){
            pathW += givenJson.shortestPathDist(G2.get(i).getKey(), G2.get(i+1).getKey());
        }
        System.out.println("G2 Test tsp: "+pathW);


     }

    /**
     * Saves Graphs to JSON files.
     * @throws Exception
     */
    @Test
    void save() throws Exception {
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

    }

    /**
     * Testing loading Graph's from JSON files.
     * @throws Exception
     */
    @Test
    void load() throws Exception {

        MyDWG_Algo testGraphAlgo1 = new MyDWG_Algo();
        assertTrue(testGraphAlgo1.load("/home/bar/Desktop/Projects for Informatica/Ex2_OOP/src/Graph/1000Nodes.json"));
        /**
         *   Second Test is for big json - that has been giving to as.
         */
        MyDWG graph2 = new MyDWG();
        MyDWG_Algo testGraphAlgo2 = new MyDWG_Algo();
        testGraphAlgo2.init(graph2);


        /**
         * For Last Test, we will make a new graph, save it and reload it(The save and make of the new graph will be done in SaveTest).
         */
        MyDWG graph3 = new MyDWG();
        MyDWG_Algo testGraphAlgo3 = new MyDWG_Algo();
        testGraphAlgo2.init(graph3);
        assertEquals(false,testGraphAlgo3.load("dontExist.json"));
        assertEquals(true,testGraphAlgo3.load("/home/bar/Desktop/Projects for Informatica/Ex2_OOP/src/Graph/1000Nodes.json"));
        System.out.println(testGraphAlgo3.getGraph().toString());
    }
    @Test
    void generateGraph() throws Exception {
        MyDWG a = new MyDWG();
        MyDWG_Algo at = new MyDWG_Algo();
        at.generateGraph(10000,5);
        assertNotEquals(at.getGraph(),null);
    }


}