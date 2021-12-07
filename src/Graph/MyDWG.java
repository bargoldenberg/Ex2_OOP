package Graph;

import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;

import java.util.*;


public class MyDWG implements DirectedWeightedGraph {
    HashMap<Integer,MyNode> V;
    HashMap<ArrayList<Integer>,MyEdge> E;
    int nodeiter;
    int edgeiter;
    int MC;

    public MyDWG(){
        V = new HashMap<Integer,MyNode>();
        E = new HashMap<ArrayList<Integer>,MyEdge>();
        this.MC=0;
    }

    public  MyDWG(MyDWG g){
        this.E = new HashMap<>();
        this.V = new HashMap<>();
        for (Map.Entry<ArrayList<Integer>, MyEdge> entry: g.E.entrySet()) {
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

    @Override
    public Iterator<NodeData> nodeIter(){
        int modecounter = this.MC;
        HashMap<Integer, NodeData> a = (HashMap<Integer, NodeData>) this.V.clone();
        Iterator<NodeData> itclone = a.values().iterator();
        Iterator<NodeData> it = new Iterator<NodeData>() {
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
                    return itclone.next();
                }
                throw new RuntimeException();
            }
        };
        return it;
    }

    @Override
    public Iterator<EdgeData> edgeIter() throws Exception {
        int modecounter = this.MC;
        HashMap<ArrayList<Integer>, EdgeData> a = (HashMap<ArrayList<Integer>, EdgeData>) this.E.clone();
        Iterator<EdgeData> itclone = a.values().iterator();
        Iterator<EdgeData> it = new Iterator<EdgeData>() {
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
                    return itclone.next();
                }
                throw new RuntimeException();
            }
        };
        return it;
    }

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
        ArrayList<Integer> key=new ArrayList<Integer>(2);
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

    public HashMap<ArrayList<Integer>,MyEdge> getE(){
        return this.E;
    }

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
