import api.EdgeData;

import java.util.Vector;

public class MyEdge implements EdgeData {
    int Src;
    int Dest;
    double Weight;
    String Info;
    int Tag;
    Vector<Integer> key;

    public MyEdge(){
        this.Src=0;
        this.Dest=0;
        this.Weight=0;
        this.Info="";
        this.key=null;
    }

    public MyEdge(int Src,double Weight,int Dest){
        this.Src=Src;
        this.Weight=Weight;
        this.Dest=Dest;
        key=new Vector<Integer>(2);
        int x = (int) (Math.pow(Src,2) + Math.pow(Dest,2));
        int y= 2*Src*Dest;
        key.add(x);
        key.add(y);
        this.key=key;
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

    public String toString() {
        return "["+"Source: "+this.Src+", Destination: "+this.Dest+", Weight: "+this.Weight+"]";
    }
}
