import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class MyDWG implements DirectedWeightedGraph {
    HashMap<Integer,MyNode> V;
    HashMap<Vector<Integer>,MyEdge> E;

    public MyDWG(){
        V = new HashMap<Integer,MyNode>();
        E = new HashMap<Vector<Integer>,MyEdge>();
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
        int x = (int) (Math.pow(src,2) + Math.pow(dest,2));
        int y= 2*src*dest;
        Vector<Integer> key =new Vector<Integer>(2);
        key.add(x);
        key.add(y);
        return E.get(key);
    }

    /**
     *this function take a NodeData, convert it to MyNode object and add it to V(the node set).
     */
    @Override
    public void addNode(NodeData n) {
        MyNode nw = new MyNode(n);
        this.V.put(n.getKey(),nw);
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
        E.put(edge.key,edge);
    }


    @Override
    public Iterator<NodeData> nodeIter() {
        Set nodeSet = this.V.entrySet();
        Iterator it = nodeSet.iterator();
        return it;
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return null;
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return null;
    }

    @Override
    public NodeData removeNode(int key) {
        return V.remove(key);
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        Vector<Integer> key=new Vector<Integer>(2);
        int x = (int) (Math.pow(src,2) + Math.pow(dest,2));
        int y= 2*src*dest;
        key.add(x);
        key.add(y);
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
        return 0;
    }
}
