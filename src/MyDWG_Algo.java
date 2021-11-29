import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;
import java.util.*;


public class MyDWG_Algo implements DirectedWeightedGraphAlgorithms {
    MyDWG g;

    @Override
    public void init(DirectedWeightedGraph g) {
        this.g =(MyDWG)g;
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


    public void BFS(DirectedWeightedGraph g,int node, boolean[] visited) throws Exception {
        LinkedList<Integer> queue = new LinkedList<Integer>();
        visited[node]=true;
        queue.add(node);
        while(!queue.isEmpty()){
            node = queue.poll();
            Iterator<EdgeData> iter = g.edgeIter(node);
            while (iter.hasNext()) {
                EdgeData n = iter.next();
                if (!visited[n.getDest()]) {
                    visited[n.getDest()]=true;
                    queue.add(n.getDest());
                }
            }
        }
    }

    @Override
    public boolean isConnected() throws Exception {
        Iterator<NodeData> it = this.getGraph().nodeIter();
        NodeData v = it.next();
        boolean[] visited = new boolean[this.getGraph().nodeSize()];
        BFS(this.getGraph(), v.getKey(), visited);
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
        BFS(reversedgraph, v.getKey(), visited);
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
                for(int i=0; i<this.g.V.get(smallest).getEdgeOutList().size(); i++){
                    Vector<Integer> tmpKey = new Vector<Integer>(2);
                    tmpKey.add(smallest);
                    tmpKey.add(this.g.V.get(this.g.V.get(smallest).getEdgeOutList().get(i)).getKey());
                    EdgeData neighborEdge = this.g.E.get(tmpKey);
                    double dis = distance.get(smallest) + neighborEdge.getWeight();
                    if(dis < distance.get(neighborEdge.getDest())){
                        distance.put(neighborEdge.getDest(),dis);
                        prev.put(neighborEdge.getDest(),smallest);

                        if(!nodesQueue.contains(neighborEdge.getDest())){
                            nodesQueue.add(neighborEdge.getDest());
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
                for(int i=0; i<this.g.V.get(smallest).getEdgeOutList().size();i++){
                    Vector<Integer> tmpKey = new Vector<Integer>(2);
                    tmpKey.add(smallest);
                    tmpKey.add(this.g.V.get(smallest).getEdgeOutList().get(i));
                    MyEdge neighborEdge = this.g.E.get(tmpKey);
                    double dis = distance.get(smallest) + neighborEdge.getWeight();
                    if(dis < distance.get(neighborEdge.getDest())){
                        distance.put(neighborEdge.getDest(),dis);
                        prev.put(neighborEdge.getDest(),smallest);

                        if(!nodesQueue.contains(neighborEdge.getDest())){
                            nodesQueue.add(neighborEdge.getDest());
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
    public NodeData center() throws Exception {
        Iterator<NodeData> it1 = this.g.nodeIter();

        double eccentricity=0;
        double dist = 0;
        ArrayList<double[]> sumofdistance = new ArrayList<>();
        while(it1.hasNext()){
            NodeData a = it1.next();
            Iterator<NodeData> it2 = this.g.nodeIter();
            eccentricity=0;
            while(it2.hasNext()){
                NodeData b = it2.next();
                if(a.getKey()==b.getKey()){
                    continue;
                }
                dist=this.shortestPathDist(a.getKey(),b.getKey());
                if(dist>eccentricity){
                    eccentricity=dist;
                }
            }
            double [] arr = {eccentricity,a.getKey()};
            sumofdistance.add(arr);
        }
        double min = Double.MAX_VALUE;
        int key = Integer.MAX_VALUE;
        for(int i =0;i<sumofdistance.size();i++){
            if(sumofdistance.get(i)[0]<min){
                min=sumofdistance.get(i)[0];
                key=(int)sumofdistance.get(i)[1];
            }
        }
        if(!this.isConnected()){
            return null;
        }else {
            return this.getGraph().getNode(key);
        }
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

    public MyDWG generateGraph(int nodes){
        MyDWG g = new MyDWG();
        for(int i=0;i<nodes;i++){
            Point3D p = new Point3D(Math.random()*10,Math.random()*10,Math.random()*10);
            int key = (int) ((Math.random() * (nodes)));
            while(g.V.containsKey(key)){
                key = (int) ((Math.random() * (nodes)));
            }
            MyNode n = new MyNode(p,key);
            g.addNode(n);
        }
        for(int i=0;i<nodes;i++){
            MyNode a = g.V.get(i);
            for(int j=0;j<10;j++){
                Vector<Integer> key =new Vector<>(2);
                key.add(a.getKey());
                int id = g.V.get((int)(Math.random() * (nodes))).getKey();
                key.add(id);
                while(g.E.containsKey(key)){
                    key.remove(1);
                    id = g.V.get((int)(Math.random() * (nodes))).getKey();
                    key.add(id);
                }
                g.connect(a.getKey(),id,Math.random()*1000);
            }

        }
        return g;
    }

}
