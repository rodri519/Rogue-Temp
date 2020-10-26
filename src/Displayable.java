public abstract class Displayable {
    private int invisible; //??
    private int visible; //??
    private int maxHit;
    private int hpMoves;
    private int Hp;
    private char t;
    private int v;
    private int x;
    private int y;
    private int w; //??
    private int h; //??
    public Displayable(){
        System.out.println("Displayable (Displayable)");
    }

    public void setInvisible(){
        System.out.println("Displayable (setInvisible)");
    }

    public void setVisible(){
        System.out.println("Displayable (setVisible)");
    }

    public void setMaxHit(int _maxHit){
        maxHit = _maxHit;
        System.out.println("Displayable (setMaxHit)" + maxHit);
    }

    public void setHpMove(int _hpMoves){
        hpMoves = _hpMoves;
        System.out.println("Displayable (setHpMove)" + hpMoves);
    }

    public void setHP(int _Hp){
        Hp = _Hp;
        System.out.println("Displayable (setHP)" + Hp);
    }

    public void setType(char _t){
        t = _t;
        System.out.println("Displayable (setType)" + t);
    }

    public void setIntValue(int _v){
        v = _v;
        System.out.println("Displayable (setIntValue)" + v);
    }

    public void setPosX(int _x){
        x = _x;
        System.out.println("Displayable (setPosX)" + x);
    }

    public void setPosY(int _y){
        y = _y;
        System.out.println("Displayable (setPosY)" + y);
    }

    public void setWidth(int _w){
        w = _w;
        System.out.println("Displayable (setWidth)" + x);
    }

    public void setHeight(int _h){
        h = _h;
        System.out.println("Displayable (setHeight)" + y);
    }

}
