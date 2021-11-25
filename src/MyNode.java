import api.EdgeData;
import api.GeoLocation;
import api.NodeData;

import java.util.ArrayList;

public class MyNode implements NodeData {
    private Point3D Location;
    private int Key;
    private double Weight;
    private String Info;
    private int Tag;
    private ArrayList<EdgeData> edgelist;

    public MyNode(){
        this.Location= null;
        this.Key = 0;
        this.Weight=0;
        this.Info="";
        this.Tag = 0;
        this.edgelist = new ArrayList<EdgeData>();
    }
    public MyNode(Point3D Location, int Key){
        this.Location= Location;
        this.Key = Key;
        this.Weight=0;
        this.Info="";
        this.Tag = 0;
        this.edgelist = new ArrayList<EdgeData>();
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

    public ArrayList<EdgeData> getEdgelist() {
        return this.edgelist;
    }

    public void addEdgelist(EdgeData edge) {
        this.edgelist.add(edge);
    }

    public void removeEdgelist(EdgeData edge) {
        this.edgelist.remove(edge);
    }

    public String toString(){
        return "["+"Location: "+this.Location+", "+
                "Weight: "+this.Weight+", "+"Key: "+
                this.Key+", "+"Info: "+this.Info+", "
                +"Tag: "+this.Tag+"]";
    }

}
