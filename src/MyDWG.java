import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;
import java.util.*;

public class MyDWG implements DirectedWeightedGraph {
    HashMap<Integer,MyNode> V;
    HashMap<Vector<Integer>,MyEdge> E;
    int nodeiter;
    int edgeiter;
    int MC ;
    public MyDWG(){
        V = new HashMap<Integer,MyNode>();
        E = new HashMap<Vector<Integer>,MyEdge>();
        this.nodeiter=0;
        this.edgeiter=0;
        this.MC=0;
    }

    /**
     * This function simply returns a Node according to the i.d.(key);
     */
    @Override
    public NodeData getNode(int key) {
        if(V.containsKey(key) == false){
            return null;
        }
        else{
            return V.get(key);
        }
    }

    /**
     * This function uses the src and dest and runs it through a one to one multivariable function to receive a unique key for the hashmap.
     * @param src
     * @param dest
     * @return Edge
     */
    @Override
    public EdgeData getEdge(int src, int dest) {
        Vector<Integer> key =new Vector<Integer>(2);
        key.add(src);
        key.add(dest);
        return E.get(key);
    }

    /**
     *this function take a NodeData, convert it to MyNode object and add it to V(the node set).
     */
    @Override
    public void addNode(NodeData n) {
        MyNode nw = new MyNode(n);
        this.V.put(n.getKey(),nw);
        this.MC++;
    }

    /**
     * Creating new Edge, Then keep it in the EdgeSet(HashMap).
     * @param src  - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w    - positive weight representing the cost (aka time, price, etc) between src-->dest.
     */
    @Override
    public void connect(int src, int dest, double w) {
        MyEdge edge = new MyEdge(src,w,dest);
        V.get(src).addEdgelist(edge);
        V.get(dest).addEdgelist(edge);
        E.put(edge.key,edge);
        this.MC++;
    }

    @Override
    public Iterator<NodeData> nodeIter() throws Exception {
        if(this.nodeiter == 0){
            this.nodeiter = this.MC;
            HashMap<Integer,NodeData> a = (HashMap<Integer,NodeData> ) this.V.clone();
            Iterator<NodeData> it = a.values().iterator();
            return it;

        }
        else if(this.nodeiter != this.MC){
            Exception e = new RuntimeException();
            throw e;
        }else {
            HashMap<Integer,NodeData> a = (HashMap<Integer,NodeData> ) this.V.clone();
            Iterator<NodeData> it = a.values().iterator();
            return it;
        }
    }

    @Override
    public Iterator<EdgeData> edgeIter() throws Exception {
        if(this.edgeiter == 0){
            this.edgeiter = this.MC;
            HashMap<Vector<Integer>,EdgeData> a = (HashMap<Vector<Integer>,EdgeData> ) this.E.clone();
            Iterator<EdgeData> it = a.values().iterator();
            return it;
        }
        else if(this.MC != this.edgeiter){
            Exception e = new RuntimeException();
            throw e;
        }
        else{
            HashMap<Vector<Integer>,EdgeData> a = (HashMap<Vector<Integer>,EdgeData> ) this.E.clone();
            Iterator<EdgeData> it = a.values().iterator();
            return it;
        }

    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        this.edgeiter = this.MC;
        HashMap<Vector<Integer>, EdgeData> a = (HashMap<Vector<Integer>, EdgeData>) this.V.get(node_id).getEdgeOutList().clone();
        Iterator<EdgeData> it = a.values().iterator();
        return it;
    }

    @Override
    public NodeData removeNode(int key) {
        for(Map.Entry<Vector<Integer>,MyEdge> removableEdge: V.get(key).getEdgeOutList().entrySet()){
            removeEdge(removableEdge.getValue().getSrc(),removableEdge.getValue().getDest());
        }
        for(Map.Entry<Vector<Integer>,MyEdge> removableEdge: V.get(key).getEdgeInList().entrySet()){
            removeEdge(removableEdge.getValue().getSrc(),removableEdge.getValue().getDest());
        }
        this.MC++;
        return V.remove(key);
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        Vector<Integer> key=new Vector<Integer>(2);
        key.add(src);
        key.add(dest);
        this.MC++;
        V.get(src).removeEdgelist(E.get(key));
        V.get(dest).removeEdgelist(E.get(key));
        return E.remove(key);
    }

    @Override
    public int nodeSize() {
        return V.size();
    }

    @Override
    public int edgeSize() {
        return E.size();
    }

    @Override
    public int getMC() {
        return this.MC;
    }

    public String toString(){
        String vertices = "Vertecies: ";
        String edges ="Edges: ";
        for (Integer node: V.keySet()) {
            String value = V.get(node).toString();
            vertices += value+",";
        }
        vertices=vertices.substring(0,vertices.length()-1);
        for (Vector<Integer> edge: E.keySet()) {
            String value = E.get(edge).toString();
            edges += value+",";
        }
        edges=edges.substring(0,edges.length()-1);
        return "("+"{"+vertices+"}, {"+edges+"}"+")";
    }
}
