import api.EdgeData;
import api.GeoLocation;
import api.NodeData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class MyNode implements NodeData {
    private Point3D Location;
    private int Key;
    private double Weight;
    private String Info;
    private int Tag;
    private HashMap<Vector<Integer>,MyEdge> edgeInList;
    private HashMap<Vector<Integer>,MyEdge> edgeOutList;


    public MyNode(){
        this.Location= null;
        this.Key = 0;
        this.Weight=0;
        this.Info="";
        this.Tag = 0;
        this.edgeInList = new HashMap<Vector<Integer>,MyEdge>();
        this.edgeOutList = new HashMap<Vector<Integer>,MyEdge>();
    }

    public MyNode(Point3D Location, int Key){
        this.Location= Location;
        this.Key = Key;
        this.Weight=0;
        this.Info="";
        this.Tag = 0;
        this.edgeInList = new HashMap<Vector<Integer>,MyEdge>();
        this.edgeOutList = new HashMap<Vector<Integer>,MyEdge>();
    }

    public MyNode(NodeData n){
        this.Location= (Point3D)n.getLocation();
        this.Key = n.getKey();
    }

    @Override
    public int getKey() {
        return this.Key;
    }

    @Override
    public GeoLocation getLocation() {
        return this.Location;
    }

    @Override
    public void setLocation(GeoLocation p) {
        this.Location = (Point3D)p;
    }

    @Override
    public double getWeight() {
        return this.Weight;
    }

    @Override
    public void setWeight(double w) {
        this.Weight=w;
    }

    @Override
    public String getInfo() {
        return this.Info;
    }

    @Override
    public void setInfo(String s) {
        this.Info=s;
    }

    @Override
    public int getTag() {
        return this.Tag;
    }

    @Override
    public void setTag(int t) {
        this.Tag=t;
    }

    public HashMap<Vector<Integer>,MyEdge> getEdgeInList() {
        return this.edgeInList;
    }

    public HashMap<Vector<Integer>,MyEdge> getEdgeOutList() {
        return this.edgeOutList;
    }

    public boolean addEdgelist(MyEdge edge) {
        if(edge.Src == this.Key){
            Vector<Integer> key = new Vector<Integer>();
            key.add(edge.Src);
            key.add(edge.Dest);
            this.edgeOutList.put(key,edge);
            return true;
        }
        else if(edge.Dest == this.Key){
            Vector<Integer> key = new Vector<Integer>();
            key.add(edge.Src);
            key.add(edge.Dest);
            this.edgeInList.put(key,edge);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean removeEdgelist(MyEdge edge) {
        if(edge.Src == this.Key){
            Vector<Integer> key = new Vector<Integer>();
            key.add(edge.Src);
            key.add(edge.Dest);
            this.edgeOutList.remove(key);
            return true;
        }
        else if(edge.Dest == this.Key){
            Vector<Integer> key = new Vector<Integer>();
            key.add(edge.Src);
            key.add(edge.Dest);
            this.edgeInList.remove(key);
            return true;
        }
        else{
            return false;
        }
    }

    public String toString(){
        return "["+"Location: "+this.Location+", "+
                "Weight: "+this.Weight+", "+"Key: "+
                this.Key+", "+"Info: "+this.Info+", "
                +"Tag: "+this.Tag+"]";
    }
}
