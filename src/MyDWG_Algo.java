import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;

import java.util.*;

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
        HashMap<Integer,Double> distance = new HashMap<Integer,Double>();
        HashMap<Integer,Integer> prev = new HashMap<Integer,Integer>();
        PriorityQueue<Integer> nodesQueue = new PriorityQueue<Integer>();
        List<NodeData> path = new ArrayList<NodeData>();

        for(Map.Entry<Integer, MyNode> node: this.g.V.entrySet()){
            if(node.getKey() == src){
                distance.put(node.getKey(), 0.0);
                nodesQueue.add(node.getKey());
            }
            else{
                distance.put(node.getKey(), Double.MAX_VALUE);
            }
            prev.put(node.getKey(),null);
        }

        while(!nodesQueue.isEmpty()){
            int smallest = nodesQueue.poll();
            // check for breaking the loop, IF we got to the destination.
            if(smallest == dest){
                while(prev.get(smallest) != null){
                    path.add(this.g.V.get(smallest));
                    smallest = prev.get(smallest);
                }
                path.add(this.g.V.get(smallest));
                Collections.reverse(path);

            }
            else if(distance.get(smallest) == Double.MAX_VALUE){
                break;
            }
            else{
                for(Map.Entry<Vector<Integer>,MyEdge> neighborEdge:this.g.V.get(smallest).getEdgeOutList().entrySet()){
                    double dis = distance.get(smallest) + neighborEdge.getValue().getWeight();
                    if(dis < distance.get(neighborEdge.getValue().getDest())){
                        distance.put(neighborEdge.getValue().getDest(),dis);
                        prev.put(neighborEdge.getValue().getDest(),smallest);

                        if(!nodesQueue.contains(neighborEdge.getValue().getDest())){
                            nodesQueue.add(neighborEdge.getValue().getDest());
                        }
                    }
                }
            }
        }
        if(distance.get(dest) == Double.MAX_VALUE){
            return -1;
        }
        else{
            return distance.get(dest);
        }


    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        HashMap<Integer,Double> distance = new HashMap<Integer,Double>();
        HashMap<Integer,Integer> prev = new HashMap<Integer,Integer>();
        PriorityQueue<Integer> nodesQueue = new PriorityQueue<Integer>();
        List<NodeData> path = new ArrayList<NodeData>();

        for(Map.Entry<Integer, MyNode> node: this.g.V.entrySet()){
            if(node.getKey() == src){
                distance.put(node.getKey(), 0.0);
                nodesQueue.add(node.getKey());
            }
            else{
                distance.put(node.getKey(), Double.MAX_VALUE);
            }
            prev.put(node.getKey(),null);
        }

        while(!nodesQueue.isEmpty()){
            int smallest = nodesQueue.poll();
            // check for breaking the loop, IF we got to the destination.
            if(smallest == dest){
                while(prev.get(smallest) != null){
                    path.add(this.g.V.get(smallest));
                    smallest = prev.get(smallest);
                }
                path.add(this.g.V.get(smallest));
                Collections.reverse(path);

            }
            else if(distance.get(smallest) == Double.MAX_VALUE){
                break;
            }
            else{
                for(Map.Entry<Vector<Integer>,MyEdge> neighborEdge:this.g.V.get(smallest).getEdgeOutList().entrySet()){
                    double dis = distance.get(smallest) + neighborEdge.getValue().getWeight();
                    if(dis < distance.get(neighborEdge.getValue().getDest())){
                        distance.put(neighborEdge.getValue().getDest(),dis);
                        prev.put(neighborEdge.getValue().getDest(),smallest);

                        if(!nodesQueue.contains(neighborEdge.getValue().getDest())){
                            nodesQueue.add(neighborEdge.getValue().getDest());
                        }
                    }
                }
            }
        }
        if(path.size() > 0){
            return path;
        }
        else{
            return null;
        }

    }

    @Override
    public NodeData center() {
        return null;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        /**
         * Implementing with Greedy Algorithm.
         */
        List<NodeData> rightOrder = new ArrayList<NodeData>();
        int curr =0, tmp = 0,counter =0;
        double[] distance = new double[cities.size()];
        NodeData ptr = cities.get(curr);    ///__ FOR NOW WE START WITH 0, NEXT WE WILL FIND THE OPTIMAL STARTING POINT__
        rightOrder.add(ptr);

        while(counter < cities.size()){
            ptr = cities.get(curr);
            tmp = curr;
            for(int i=0;i<cities.size();i++){
                if(ptr.getKey() != cities.get(i).getKey()){
                    distance[i] = shortestPathDist(ptr.getKey(),cities.get(i).getKey());
                }
                else{
                    distance[i] = Double.MAX_VALUE;
                }
            }
            while(curr == tmp){
                int smallest = smallestDist(distance); /// smallest is the index of the smallest number in the array.
                if(!rightOrder.contains(cities.get(smallest))){
                    rightOrder.add(cities.get(smallest));
                    curr = smallest;
                    counter++;
                }
                else if(rightOrder.size() != cities.size()){
                    distance[smallest] = Double.MAX_VALUE;
                }
                else{
                    break;
                }
            }
            if(rightOrder.size() == cities.size()){
                break;
            }
        }
        return rightOrder;
    }

    /**
     * Helper for tcp, return the smallest number (index) from array.
     * @param arr - array
     * @return index of the smallest number.
     */
    private int smallestDist(double[] arr){
        int smallest =0;
        double check = arr[0];
        for(int i=1;i<arr.length;i++){
            if(arr[i]<check){
                check = arr[i];
                smallest = i;
            }
        }
        return smallest;
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
