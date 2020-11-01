package game;

import java.util.ArrayList;
import java.util.List;

public abstract class Displayable {
    public int visible;
    public int maxHit;
    public int hpMove;
    public char type;
    public int intValue;
    public int posX;
    public int posY;
    public int width;
    public int height;
    public Char aChar;
    public List<Integer> xCoords = new ArrayList<>();
    public List<Integer> yCoords = new ArrayList<>();

    public Displayable(){
        System.out.println("Displayable (Displayable)");
    }

    public void setInvisible(){
        visible = 0;
        System.out.println("Displayable (setInvisible)");
    }

    public void setVisible(){
        visible = 1;
        System.out.println("Displayable (setVisible)");
    }

    public void setMaxHit(int _maxHit){
        maxHit = _maxHit;
        System.out.println("Displayable (setMaxHit)" + maxHit);
    }

    public void setHpMove(int _hpMoves){
        hpMove = _hpMoves;
        System.out.println("Displayable (setHpMove)" + hpMove);
    }

    public void setType(char _t){
        type = _t;
        aChar = new Char(_t);
        System.out.println("Displayable (setType)" + type);
    }

    public void setIntValue(int _v){
        intValue = _v;
        System.out.println("Displayable (setIntValue)" + intValue);
    }

    public void setPosX(int _x){
        posX = _x;
        xCoords.add(_x);
        System.out.println("Displayable (setPosX)" + posX);
    }

    public void setPosY(int _y){
        posY = _y;
        yCoords.add(_y);
        System.out.println("Displayable (setPosY)" + posY);
    }

    public void setWidth(int _w){
        width = _w;
        System.out.println("Displayable (setWidth)" + width);
    }

    public void setHeight(int _h) {
        height = _h;
        System.out.println("Displayable (setHeight)" + height);
    }

    public void setChar(char _c){
        aChar = new Char(_c);
    }

}