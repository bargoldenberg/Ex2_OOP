import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class MyDWG_Algo implements DirectedWeightedGraphAlgorithms {
    MyDWG g;

    @Override
    public void init(DirectedWeightedGraph g) {
        this.g = new MyDWG((MyDWG) g);
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.g;
    }

    @Override
    public DirectedWeightedGraph copy() {
        MyDWG cop = new MyDWG(this.g);
        return cop;
    }

    private void DFS(DirectedWeightedGraph g, int node, boolean[] visited) throws Exception {
        visited[node] = true;
        Iterator<EdgeData> it = g.edgeIter(node);
        while (it.hasNext()) {
            EdgeData e = it.next();
            if (!visited[e.getDest()]) {
                DFS(g, e.getDest(), visited);
            }

        }
    }

    @Override
    public boolean isConnected() throws Exception {
        Iterator<NodeData> it = this.getGraph().nodeIter();
        NodeData v = it.next();
        boolean[] visited = new boolean[this.getGraph().nodeSize()];
        DFS(this.getGraph(), v.getKey(), visited);
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                return false;
            }
        }
        for (int i = 0; i < visited.length; i++){
            visited[i]=false;
    }
        MyDWG reversedgraph = (MyDWG)this.copy();
        Iterator<EdgeData> edgeiterator = reversedgraph.edgeIter();
        while(edgeiterator.hasNext()){
            EdgeData originalEdge = edgeiterator.next();
            MyEdge reversedEdge = new MyEdge(originalEdge.getDest(),originalEdge.getWeight(), originalEdge.getSrc());
            boolean condition1 = reversedgraph.E.containsValue(originalEdge);
            Vector<Integer> key = new Vector<Integer>();
            key.add(reversedEdge.getSrc());
            key.add(reversedEdge.getDest());
            boolean condition2 = reversedgraph.E.containsKey(key);
            if(condition1&&condition2){
                continue;
            }
            reversedgraph.removeEdge(originalEdge.getSrc(), originalEdge.getDest());
            reversedgraph.connect(originalEdge.getDest(), originalEdge.getSrc(), originalEdge.getWeight());
        }
        DFS(reversedgraph, v.getKey(), visited);
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public NodeData center() {
        return null;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        return null;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }
}
