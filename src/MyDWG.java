import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;

import java.lang.reflect.Type;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.stream.Collectors;


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

    public  MyDWG(MyDWG g){
        this.E = new HashMap<>();
        this.V = new HashMap<>();
        for (Map.Entry<Vector<Integer>, MyEdge> entry: g.E.entrySet()) {
            this.E.put(entry.getKey(), new MyEdge(entry.getValue()));
        }
        for (Map.Entry<Integer, MyNode> entry: g.V.entrySet()) {
            this.V.put(entry.getKey(), new MyNode(entry.getValue()));
            for(int i=0; i<g.V.get(entry.getKey()).getEdgeInList().size(); i++){
                this.V.get(entry.getKey()).getEdgeInList().add(g.V.get(entry.getKey()).getEdgeInList().get(i));
            }
            for(int i=0; i<g.V.get(entry.getKey()).getEdgeOutList().size(); i++){
                this.V.get(entry.getKey()).getEdgeOutList().add(g.V.get(entry.getKey()).getEdgeOutList().get(i));
            }
//            this.V.get(entry.getKey()).getEdgeOutList().putAll(g.V.get(entry.getKey()).getEdgeOutList());
//            this.V.get(entry.getKey()).getEdgeInList().putAll(g.V.get(entry.getKey()).getEdgeInList());
        }
        this.edgeiter = g.edgeiter;
        this.nodeiter =g.nodeiter;
        this.MC = g.MC;
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
        this.nodeiter = this.MC;
        HashMap<Integer,NodeData> a = (HashMap<Integer,NodeData> ) this.V.clone();
        Iterator<NodeData> it = a.values().iterator();
        return it;
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
        HashMap<Vector<Integer>, EdgeData> a = new HashMap<Vector<Integer>, EdgeData>();
        for (int i=0;i<this.V.get(node_id).getEdgeOutList().size();i++){
            Vector<Integer> key = new Vector<Integer>(2);
            key.add(node_id);
            key.add(this.V.get(node_id).getEdgeOutList().get(i));
            a.put(key,this.E.get(key));
        }
        Iterator<EdgeData> it = a.values().iterator();
        return it;
    }

    @Override
    public NodeData removeNode(int key) {
        for(int i=0; i<V.get(key).getEdgeOutList().size();i++){
            removeEdge(key,V.get(key).getEdgeOutList().get(i));
        }
        for(int i=0; i<V.get(key).getEdgeInList().size();i++){
            removeEdge(V.get(key).getEdgeInList().get(i),key);
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
        V.get(src).removeEdgelist(src,dest);
        V.get(dest).removeEdgelist(src,dest);
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
