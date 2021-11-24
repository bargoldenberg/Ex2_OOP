import api.GeoLocation;
import api.NodeData;

public class MyNode implements NodeData {
    Point3D Location;
    int Key;
    double Weight;
    String Info;
    int Tag;

    public MyNode(){
        this.Location= null;
        this.Key = 0;
        this.Weight=0;
        this.Info="";
        this.Tag = 0;
    }
    public MyNode(Point3D Location, int Key){
        this.Location= Location;
        this.Key = Key;
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
}
