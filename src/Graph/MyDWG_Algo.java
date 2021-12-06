package Graph;

import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;


public class MyDWG_Algo implements DirectedWeightedGraphAlgorithms {
    MyDWG g;

    @Override
    public void init(DirectedWeightedGraph g) {
        this.g = (MyDWG) g;
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


    public void BFS(DirectedWeightedGraph g, int node, HashMap<Integer, Boolean> visited) throws Exception {
        LinkedList<Integer> queue = new LinkedList<Integer>();
        visited.put(node, true);
        queue.add(node);
        while (!queue.isEmpty()) {
            node = queue.poll();
            Iterator<EdgeData> iter = g.edgeIter(node);
            while (iter.hasNext()) {
                EdgeData n = iter.next();
                if (!visited.get(n.getDest())) {
                    visited.put(n.getDest(), true);
                    queue.add(n.getDest());
                }
            }
        }
    }

    @Override
    public boolean isConnected() throws Exception {
        Iterator<NodeData> it = this.getGraph().nodeIter();
        NodeData v = it.next();
        HashMap<Integer, Boolean> visited = new HashMap<>();
        Iterator<NodeData> init = this.getGraph().nodeIter();
        while (init.hasNext()) {
            visited.put(init.next().getKey(), false);
        }
        //boolean[] visited = new boolean[this.getGraph().nodeSize()];
        BFS(this.getGraph(), v.getKey(), visited);
        Iterator<NodeData> checkfalse = this.getGraph().nodeIter();
        while (checkfalse.hasNext()) {
            if (!visited.get(checkfalse.next().getKey())) {
                return false;
            }
        }
        Iterator<NodeData> init2 = this.getGraph().nodeIter();
        while (init2.hasNext()) {
            visited.put(init2.next().getKey(), false);
        }
        MyDWG reversedgraph = (MyDWG) this.copy();
        Iterator<EdgeData> edgeiterator = this.getGraph().edgeIter();
        while (edgeiterator.hasNext()) {
            EdgeData originalEdge = edgeiterator.next();
            MyEdge reversedEdge = new MyEdge(originalEdge.getDest(), originalEdge.getWeight(), originalEdge.getSrc());
            boolean condition1 = reversedgraph.E.containsValue(originalEdge);
            ArrayList<Integer> key = new ArrayList<Integer>();
            key.add(reversedEdge.getSrc());
            key.add(reversedEdge.getDest());
            boolean condition2 = reversedgraph.E.containsKey(key);
            if (condition1 || condition2) {
                continue;
            }
            reversedgraph.removeEdge(originalEdge.getSrc(), originalEdge.getDest());
            reversedgraph.connect(originalEdge.getDest(), originalEdge.getSrc(), originalEdge.getWeight());
        }
        BFS(reversedgraph, v.getKey(), visited);
        Iterator<NodeData> checkfalse2 = this.getGraph().nodeIter();
        while (checkfalse2.hasNext()) {
            if (!visited.get(checkfalse2.next().getKey())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        HashMap<Integer, Double> distance = new HashMap<Integer, Double>();
        HashMap<Integer, Integer> prev = new HashMap<Integer, Integer>();
        PriorityQueue<Integer> nodesQueue = new PriorityQueue<Integer>();
        List<NodeData> path = new ArrayList<NodeData>();

        for (Map.Entry<Integer, MyNode> node : this.g.V.entrySet()) {
            if (node.getKey() == src) {
                distance.put(node.getKey(), 0.0);
                nodesQueue.add(node.getKey());
            } else {
                distance.put(node.getKey(), Double.MAX_VALUE);
            }
            prev.put(node.getKey(), null);
        }

        while (!nodesQueue.isEmpty()) {
            int smallest = nodesQueue.poll();
            // check for breaking the loop, IF we got to the destination.
            if (smallest == dest) {
                while (prev.get(smallest) != null) {
                    path.add(this.g.V.get(smallest));
                    smallest = prev.get(smallest);
                }
                path.add(this.g.V.get(smallest));

            } else if (distance.get(smallest) == Double.MAX_VALUE) {
                break;
            } else {
                for (int i = 0; i < this.g.V.get(smallest).getEdgeOutList().size(); i++) {
                    ArrayList<Integer> tmpKey = new ArrayList<Integer>(2);
                    tmpKey.add(smallest);
                    tmpKey.add(this.g.V.get(this.g.V.get(smallest).getEdgeOutList().get(i)).getKey());
                    EdgeData neighborEdge = this.g.E.get(tmpKey);
                    double dis = distance.get(smallest) + neighborEdge.getWeight();
                    if (dis < distance.get(neighborEdge.getDest())) {
                        distance.put(neighborEdge.getDest(), dis);
                        prev.put(neighborEdge.getDest(), smallest);

                        if (!nodesQueue.contains(neighborEdge.getDest())) {
                            nodesQueue.add(neighborEdge.getDest());
                        }
                    }
                }
            }
        }
        if (distance.get(dest) == Double.MAX_VALUE) {
            return -1;
        } else {
            return distance.get(dest);
        }


    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        HashMap<Integer, Double> distance = new HashMap<Integer, Double>();
        HashMap<Integer, Integer> prev = new HashMap<Integer, Integer>();
        PriorityQueue<Integer> nodesQueue = new PriorityQueue<Integer>();
        List<NodeData> path = new ArrayList<NodeData>();

        for (Map.Entry<Integer, MyNode> node : this.g.V.entrySet()) {
            if (node.getKey() == src) {
                distance.put(node.getKey(), 0.0);
                nodesQueue.add(node.getKey());
            } else {
                distance.put(node.getKey(), Double.MAX_VALUE);
            }
            prev.put(node.getKey(), null);
        }

        while (!nodesQueue.isEmpty()) {
            int smallest = nodesQueue.poll();
            // check for breaking the loop, IF we got to the destination.
            if (smallest == dest) {
                while (prev.get(smallest) != null) {
                    path.add(this.g.V.get(smallest));
                    smallest = prev.get(smallest);
                }
                path.add(this.g.V.get(smallest));
                Collections.reverse(path);

            } else if (distance.get(smallest) == Double.MAX_VALUE) {
                break;
            } else {
                for (int i = 0; i < this.g.V.get(smallest).getEdgeOutList().size(); i++) {
                    ArrayList<Integer> tmpKey = new ArrayList<Integer>(2);
                    tmpKey.add(smallest);
                    tmpKey.add(this.g.V.get(smallest).getEdgeOutList().get(i));
                    MyEdge neighborEdge = this.g.E.get(tmpKey);
                    double dis = distance.get(smallest) + neighborEdge.getWeight();
                    if (dis < distance.get(neighborEdge.getDest())) {
                        distance.put(neighborEdge.getDest(), dis);
                        prev.put(neighborEdge.getDest(), smallest);

                        if (!nodesQueue.contains(neighborEdge.getDest())) {
                            nodesQueue.add(neighborEdge.getDest());
                        }
                    }
                }
            }
        }
        if (path.size() > 0) {
            return path;
        } else {
            return null;
        }

    }

    private HashMap<Integer, Double> shortestPathMap(int src) {
        HashMap<Integer, Double> distance = new HashMap<Integer, Double>();
        HashMap<Integer, Integer> prev = new HashMap<Integer, Integer>();
        PriorityQueue<Integer> nodesQueue = new PriorityQueue<Integer>(Comparator.comparingDouble(distance::get));
        HashSet<Integer> queueset = new HashSet<Integer>();
        //List<NodeData> path = new ArrayList<NodeData>();

        for (Map.Entry<Integer, MyNode> node : this.g.V.entrySet()) {
            if (node.getKey() == src) {
                distance.put(node.getKey(), 0.0);
                nodesQueue.add(node.getKey());
            } else {
                distance.put(node.getKey(), Double.MAX_VALUE);
            }
            prev.put(node.getKey(), null);
        }


        while (!nodesQueue.isEmpty()) {
            int smallest = nodesQueue.poll();
            queueset.remove(smallest);
            if (distance.get(smallest) == Double.MAX_VALUE) {
                break;
            } else {
                for (int i = 0; i < this.g.V.get(smallest).getEdgeOutList().size(); i++) {
                    ArrayList<Integer> tmpKey = new ArrayList<Integer>(2);
                    tmpKey.add(smallest);
                    tmpKey.add(this.g.V.get(this.g.V.get(smallest).getEdgeOutList().get(i)).getKey());
                    EdgeData neighborEdge = this.g.E.get(tmpKey);
                    double dis = distance.get(smallest) + neighborEdge.getWeight();
                    if (dis < distance.get(neighborEdge.getDest())) {
                        distance.put(neighborEdge.getDest(), dis);
                        prev.put(neighborEdge.getDest(), smallest);

                        if (!queueset.contains(neighborEdge.getDest())) {
                            nodesQueue.add(neighborEdge.getDest());
                            queueset.add(neighborEdge.getDest());
                        }
                    }
                }
            }
        }

        return distance;


    }

    @Override
    public NodeData center() throws Exception {
        Iterator<NodeData> it1 = this.g.nodeIter();
        int count = 0;
        double eccentricity = 0;
        double dist = 0;
        ArrayList<double[]> sumofdistance = new ArrayList<>();
        while (it1.hasNext()) {
            NodeData a = it1.next();
            HashMap<Integer, Double> distance = shortestPathMap(a.getKey());
            Iterator<NodeData> it2 = this.g.nodeIter();
            eccentricity = 0;
            while (it2.hasNext()) {
                NodeData b = it2.next();
                if (a.getKey() == b.getKey()) {
                    continue;
                }
                dist = distance.get(b.getKey());
                if (dist > eccentricity) {
                    eccentricity = dist;
                }
            }
            double[] arr = {eccentricity, a.getKey()};
            sumofdistance.add(arr);
        }
        double min = Double.MAX_VALUE;
        int key = Integer.MAX_VALUE;
        for (int i = 0; i < sumofdistance.size(); i++) {
            if (sumofdistance.get(i)[0] < min) {
                min = sumofdistance.get(i)[0];
                key = (int) sumofdistance.get(i)[1];
            }
        }
        if (!this.isConnected()) {
            return null;
        } else {
            return this.getGraph().getNode(key);
        }
    }


    @Override
    public List<NodeData> tsp(List<NodeData> cities) {

        /**
         * Implementing with Greedy Algorithm.
         */
        List<NodeData> rightOrder = new ArrayList<NodeData>();
        int curr = 0, tmp = 0, counter = 0;
        double[] distance = new double[cities.size()];
        NodeData ptr = cities.get(curr);    ///__ FOR NOW WE START WITH 0, NEXT WE WILL FIND THE OPTIMAL STARTING POINT__
        rightOrder.add(ptr);

        while (counter < cities.size()) {
            ptr = cities.get(curr);
            tmp = curr;
            for (int i = 0; i < cities.size(); i++) {
                if (ptr.getKey() != cities.get(i).getKey()) {
                    distance[i] = shortestPathDist(ptr.getKey(), cities.get(i).getKey());
                } else {
                    distance[i] = Double.MAX_VALUE;
                }
            }
            while (curr == tmp) {
                int smallest = smallestDist(distance); /// smallest is the index of the smallest number in the array.
                if (!rightOrder.contains(cities.get(smallest))) {
                    rightOrder.add(cities.get(smallest));
                    curr = smallest;
                    counter++;
                } else if (rightOrder.size() != cities.size()) {
                    distance[smallest] = Double.MAX_VALUE;
                } else {
                    break;
                }
            }
            if (rightOrder.size() == cities.size()) {
                break;
            }
        }
        return rightOrder;
    }

    /**
     * Helper for tcp, return the smallest number (index) from array.
     *
     * @param arr - array
     * @return index of the smallest number.
     */
    private int smallestDist(double[] arr) {
        int smallest = 0;
        double check = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < check) {
                check = arr[i];
                smallest = i;
            }
        }
        return smallest;
    }

    @Override
    public boolean save(String file) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        fromJsonToGraph graphJson = new fromJsonToGraph(this.g);
        String json = gson.toJson(graphJson);
        try {
            FileWriter fw = new FileWriter("" + file);
            fw.write(gson.toJson(graphJson));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean load(String file) {
        try {
            Gson gson = new Gson();
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            ;
            fromJsonToGraph graph = gson.fromJson(bufferedReader, fromJsonToGraph.class);
            MyDWG myGraph = new MyDWG(graph);
            init(new MyDWG(graph));
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    public MyDWG generateGraph(int nodes,int seed) {
        MyDWG g = new MyDWG();
        Random ra = new Random(seed);
        for (int i = 0; i < nodes; i++) {
            Point3D p = new Point3D( ra.nextInt(nodes),  ra.nextInt(nodes),  ra.nextInt(nodes));
            int key = (ra.nextInt(nodes));
            while (g.V.containsKey(key)) {
                key = (ra.nextInt(nodes));
            }
            MyNode n = new MyNode(p, key);
            g.addNode(n);
        }
        for (int i = 0; i < nodes; i++) {
            MyNode a = g.V.get(i);
            for (int j = 0; j < 9; j++) {
                ArrayList<Integer> key = new ArrayList<>(2);
                key.add(a.getKey());
                int id = g.V.get(ra.nextInt(nodes)).getKey();
                key.add(id);
                while (g.E.containsKey(key) || a.getKey() == id) {
                    key.remove(1);
                    id = g.V.get(ra.nextInt(nodes)).getKey();
                    key.add(id);
                }
                g.connect(a.getKey(), id, ra.nextDouble(1000));
            }

        }
        return g;
    }
}
// Random r = new Random(seed) (we can revisit a random sequence)