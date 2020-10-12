public abstract class Displayable {

    public Displayable(){
        System.out.println("Displayable (Displayable)");
    }

    public void setInvisible(){
        System.out.println("Displayable (setInvisible)");
    }

    public void setVisible(){
        System.out.println("Displayable (setVisible)");
    }

    public void setMaxHit(int maxHit){
        System.out.println("Displayable (setMaxHit)" + maxHit);
    }

    public void setHpMove(int hpMoves){
        System.out.println("Displayable (setHpMove)" + hpMoves);
    }

    public void setHP(int Hp){
        System.out.println("Displayable (setHP)" + Hp);
    }

    public void setType(char t){
        System.out.println("Displayable (setType)" + t);
    }

    public void setIntValue(int v){
        System.out.println("Displayable (setIntValue)" + v);
    }

    public void setPosX(int x){
        System.out.println("Displayable (setPosX)" + x);
    }

    public void setPosY(int y){
        System.out.println("Displayable (setPosY)" + y);
    }

    public void setWidth(int x){
        System.out.println("Displayable (setWidth)" + x);
    }

    public void setHeight(int y){
        System.out.println("Displayable (setHeight)" + y);
    }

}
