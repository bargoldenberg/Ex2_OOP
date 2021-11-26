import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;

import java.util.Iterator;
import java.util.List;

public class MyDWG_Algo implements DirectedWeightedGraphAlgorithms {
    MyDWG g;
    @Override
    public void init(DirectedWeightedGraph g) {
        this.g = new MyDWG((MyDWG)g);
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
    private void DFS (DirectedWeightedGraph g,int node, boolean[] visited) throws Exception {
        visited[node] =true;
        Iterator<EdgeData> it = g.edgeIter(node);
        while (it.hasNext()){
            EdgeData e  = it.next();
            if (!visited[e.getDest()]) {
                DFS(g,e.getDest(),visited);
            }

        }
    }
    @Override
    public boolean isConnected() throws Exception {
        Iterator<NodeData> it = this.getGraph().nodeIter();
        while(it.hasNext()) {
            NodeData v= it.next();
            boolean[] visited = new boolean[this.getGraph().nodeSize()];
            DFS(this.getGraph(),v.getKey(),visited);
            for(int i=0;i< visited.length;i++){
                if(!visited[i]){
                    return false;
                }
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
