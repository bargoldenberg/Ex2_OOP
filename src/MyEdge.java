import api.EdgeData;

public class MyEdge implements EdgeData {
    int Src;
    int Dest;
    int Weight;
    String Info;
    int Tag;
    public MyEdge(){
        this.Src=0;
        this.Dest=0;
        this.Weight=0;
        this.Info="";
    }
    public MyEdge(int Src,int Weight,int Dest){
        this.Src=Src;
        this.Weight=Weight;
        this.Dest=Dest;
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
}
