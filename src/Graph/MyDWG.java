package Graph;

import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;

import java.util.*;
import java.util.function.Consumer;


public class MyDWG implements DirectedWeightedGraph {
    HashMap<Integer,MyNode> V;
    HashMap<ArrayList<Integer>,MyEdge> E;
    int nodeiter;
    int edgeiter;
    int MC;

    /**
     * Constructor method for Directed Weighted Graph Class.
     */
    public MyDWG(){
        V = new HashMap<Integer,MyNode>();
        E = new HashMap<ArrayList<Integer>,MyEdge>();
        this.MC=0;
    }

    /**
     * Copy Constructor for Directed Weighted Graph Class.
     * @param g
     */
    public  MyDWG(MyDWG g){
        this.E = new HashMap<>();
        this.V = new HashMap<>();
        /**
         * Copy Edges.
         */
        for (Map.Entry<ArrayList<Integer>, MyEdge> entry: g.E.entrySet()) {
            this.E.put(entry.getKey(), new MyEdge(entry.getValue()));
        }
        /**
         * Copy Nodes, AND NODE NEIGHBORHOODS.
         */
        for (Map.Entry<Integer, MyNode> entry: g.V.entrySet()) {
            this.V.put(entry.getKey(), new MyNode(entry.getValue()));
            for(int i=0; i<g.V.get(entry.getKey()).getEdgeInList().size(); i++){
                this.V.get(entry.getKey()).getEdgeInList().add(g.V.get(entry.getKey()).getEdgeInList().get(i));
            }
            for(int i=0; i<g.V.get(entry.getKey()).getEdgeOutList().size(); i++){
                this.V.get(entry.getKey()).getEdgeOutList().add(g.V.get(entry.getKey()).getEdgeOutList().get(i));
            }
        }
        this.edgeiter = g.edgeiter;
        this.nodeiter =g.nodeiter;
        this.MC = g.MC;
    }

    /**
     * Copy constructor from JSON parsable class.
     * @param g
     */
    public MyDWG(fromJsonToGraph g){
        this.MC=0;
        this.nodeiter = this.edgeiter = 0;
        V = new HashMap<Integer,MyNode>();
        E = new HashMap<ArrayList<Integer>,MyEdge>();
        for(int i=0; i<g.Nodes.size();i++){
            V.put(g.Nodes.get(i).getID(),g.Nodes.get(i).getNode());
        }
        for(int i=0;i<g.Edges.size();i++){
            edgeConverter e = g.Edges.get(i);
            connect(e.getSrc(),e.getDest(),e.getW());
        }
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
        ArrayList<Integer> key =new ArrayList<Integer>(2);
        key.add(src);
        key.add(dest);
        return E.get(key);
    }

    /**
     *this function take a NodeData, convert it to Graph.MyNode object and add it to V(the node set).
     */
    @Override
    public void addNode(NodeData n) {
        this.V.put(n.getKey(),(MyNode)n);
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

    /**
     * Create edge given another graphs edge.
     * @param edge
     */
    public void connectinit(EdgeData edge){
        V.get(edge.getSrc()).addEdgelist(edge);
        V.get(edge.getDest()).addEdgelist(edge);
        ArrayList<Integer> key = new ArrayList<Integer>(2);
        key.add(edge.getSrc());
        key.add(edge.getDest());
        E.put(key, (MyEdge) edge);
        this.MC++;
    }
    /**
     * This method returns an Iterator for the
     * collection representing all the nodes in the graph.
     * Note: if the graph was changed since the iterator was constructed - a RuntimeException should be thrown.
     *
     * @return Iterator<node_data>
     */
    @Override
    public Iterator<NodeData> nodeIter(){

        int modecounter = this.MC;
        HashMap<Integer, NodeData> a = (HashMap<Integer, NodeData>) this.V.clone();
        Iterator<NodeData> itclone = a.values().iterator();
        Iterator<NodeData> it = new Iterator<NodeData>() {
            NodeData node;
            @Override
            public boolean hasNext() {
                return itclone.hasNext();
            }

            @Override
            public NodeData next() {
                if (modecounter != MC) {
                    Exception e = new RuntimeException();
                    try {
                        throw e;
                    } catch (Exception ex) {
                        System.out.println("Runtime Exception: the graph has been changed");

                    }
                } else {
                    node = itclone.next();
                    return node;
                }
                throw new RuntimeException();
            }
            public void remove() {
                int outsize = V.get(node.getKey()).getEdgeOutList().size();
                int insize = V.get(node.getKey()).getEdgeInList().size();
                for(int i=0; i<outsize;i++){
                    removeEdge(node.getKey(),V.get(node.getKey()).getEdgeOutList().get(0));
                }
                for(int i=0; i<insize;i++){
                    removeEdge(V.get(node.getKey()).getEdgeInList().get(0),node.getKey());
                }

            }
            public void forEachRemaining(Consumer<? super NodeData> action) {
                itclone.forEachRemaining(action);
            }
        };
        return it;
    }
    /**
     * This method returns an Iterator for all the edges in this graph.
     * Note: if any of the edges going out of this node were changed since the was constructed - a RuntimeException should be thrown.
     *
     * @return Iterator<EdgeData>
     */
    @Override
    public Iterator<EdgeData> edgeIter() throws Exception {
        int modecounter = this.MC;

        HashMap<ArrayList<Integer>, EdgeData> a = (HashMap<ArrayList<Integer>, EdgeData>) this.E.clone();
        Iterator<EdgeData> itclone = a.values().iterator();
        Iterator<EdgeData> it = new Iterator<EdgeData>() {
            EdgeData edge;
            @Override
            public boolean hasNext() {
                return itclone.hasNext();
            }

            @Override
            public EdgeData next() {
                if (modecounter != MC) {
                    Exception e = new RuntimeException();
                    try {
                        throw e;
                    } catch (Exception ex) {
                        System.out.println("Runtime Exception: the graph has been changed");

                    }
                } else {
                    edge = itclone.next();
                    return edge;
                }
                throw new RuntimeException();
            }
            @Override
            public void remove() {
                V.get(edge.getSrc()).removeEdgelist(edge.getSrc(),edge.getDest());
                V.get(edge.getDest()).removeEdgelist(edge.getSrc(),edge.getDest());
                ArrayList<Integer> key= new ArrayList<Integer>(2);
                key.add(edge.getSrc());
                key.add(edge.getDest());
                E.remove(key);
            }
            @Override
            public void forEachRemaining(Consumer<? super EdgeData> action) {
                itclone.forEachRemaining(action);
            }
        };
        return it;
    }
    /**
     * This method returns an Iterator for edges getting out of the given node (all the edges starting (source) at the given node).
     * Note: if the graph was changed since the iterator was constructed - a RuntimeException should be thrown.
     *
     * @return Iterator<EdgeData>
     */
    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        int modecounter = this.MC;
        HashMap<ArrayList<Integer>, EdgeData> a = new HashMap<ArrayList<Integer>, EdgeData>();
        for (int i=0;i<this.V.get(node_id).getEdgeOutList().size();i++){
            ArrayList<Integer> key = new ArrayList<Integer>(2);
            key.add(node_id);
            key.add(this.V.get(node_id).getEdgeOutList().get(i));
            a.put(key,this.E.get(key));
        }
        Iterator<EdgeData> ithash = a.values().iterator();
        Iterator<EdgeData> it = new Iterator<EdgeData>() {
            @Override
            public boolean hasNext() {
                return ithash.hasNext();
            }

            @Override
            public EdgeData next() {
                if (modecounter != MC) {
                    Exception e = new RuntimeException();
                    try {
                        throw e;
                    } catch (Exception ex) {
                        System.out.println("Runtime Exception: the graph has been changed");

                    }
                } else {
                    return ithash.next();
                }
                throw new RuntimeException();

            }
        };
        return it;
    }
    /**
     * Deletes the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(k), V.degree=k, as all the edges should be removed.
     *
     * @param key
     * @return the data of the removed node (null if none).
     */
    @Override
    public NodeData removeNode(int key) {
        int outsize = V.get(key).getEdgeOutList().size();
        int insize = V.get(key).getEdgeInList().size();
        for(int i=0; i<outsize;i++){
            removeEdge(key,V.get(key).getEdgeOutList().get(0));
        }
        for(int i=0; i<insize;i++){
            removeEdge(V.get(key).getEdgeInList().get(0),key);
        }
        this.MC++;
        return V.remove(key);
    }
    /**
     * Deletes the edge from the graph,
     * Note: this method should run in O(1) time.
     *
     * @param src
     * @param dest
     * @return the data of the removed edge (null if none).
     */
    @Override
    public EdgeData removeEdge(int src, int dest) {
        ArrayList<Integer> key=new ArrayList<Integer>(2);
        key.add(src);
        key.add(dest);
        this.MC++;
        V.get(src).removeEdgelist(src,dest);
        V.get(dest).removeEdgelist(src,dest);
        return E.remove(key);
    }
    /**
     * Returns the number of vertices (nodes) in the graph.
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int nodeSize() {
        return V.size();
    }
    /**
     * Returns the number of edges (assume directional graph).
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int edgeSize() {
        return E.size();
    }
    /**
     * Returns the Mode Count - for testing changes in the graph.
     *
     * @return
     */
    @Override
    public int getMC() {
        return this.MC;
    }

    /**
     * returns edge HashMap.
     * @return
     */
    public HashMap<ArrayList<Integer>,MyEdge> getE(){
        return this.E;
    }

    /**
     * To String method for printing graph.
     * @return
     */
    public String toString(){
        String vertices = "Vertecies: ";
        String edges ="Edges: ";
        for (Integer node: V.keySet()) {
            String value = V.get(node).toString();
            vertices += value+",";
        }
        vertices=vertices.substring(0,vertices.length()-1);
        for (ArrayList<Integer> edge: E.keySet()) {
            String value = E.get(edge).toString();
            edges += value+",";
        }
        edges=edges.substring(0,edges.length()-1);
        return "("+"{"+vertices+"}, {"+edges+"}"+")";
    }
}
