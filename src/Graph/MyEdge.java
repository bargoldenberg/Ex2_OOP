package Graph;

import api.EdgeData;

import java.util.ArrayList;

public class MyEdge implements EdgeData {
    int Src;
    int Dest;
    double Weight;
    String Info;
    int Tag;
    ArrayList<Integer> key;

    /**
     * Default Constructor for MyEdge.
     */
    public MyEdge(){
        this.Src=0;
        this.Dest=0;
        this.Weight=0;
        this.Info="";
        this.key=null;
    }

    /**
     * Constructor for MyEdge.
     * @param Src
     * @param Weight
     * @param Dest
     */
    public MyEdge(int Src,double Weight,int Dest){
        this.Src=Src;
        this.Weight=Weight;
        this.Dest=Dest;
        key=new ArrayList<Integer>(2);
        key.add(Src);
        key.add(Dest);
        this.key=key;
    }

    /**
     * Copy Constructor for MyEdge.
     * @param ot
     */
    public MyEdge(MyEdge ot){
        this.Src=ot.Src;
        this.Weight=ot.Weight;
        this.Dest=ot.Dest;
        key =new ArrayList<Integer>(2);
        key.add(this.Src);
        key.add(this.Dest);
        this.key = key;

    }

    /**
     * GETTERS AND SETTERS.
     * @return
     */
    public ArrayList<Integer> getKey(){
        return this.key;
    }
    @Override
    public int getSrc() {
        return this.Src;
    }

    @Override
    public int getDest() {
        return this.Dest;
    }

    @Override
    public double getWeight() {
        return this.Weight;
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

    /**
     * toString() Function.
     * @return
     */
    public String toString() {
        return "["+"Source: "+this.Src+", Destination: "+
                this.Dest+", Weight: "+this.Weight+"]";
    }
}
