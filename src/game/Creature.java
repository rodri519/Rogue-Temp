package game;

public abstract class Creature extends Displayable{
    public int hpMoves;
    private CreatureAction da;
    private CreatureAction ha;
    public Creature(){
        System.out.println("Creature (Creature)");
    }


    public void setHpMove(int _hpm){
        hpMoves = _hpm;
        System.out.println("Creature (setHpMove) " + hpMoves);
    }

    public void setDeathAction(CreatureAction _da){
        da = _da;
        System.out.println("Creature (setDeathAction) " + da);
    }

    public void setHitAction(CreatureAction _ha){
        ha = _ha;
        System.out.println("Creature (setHitAction) " + ha);
    }

    public void setName(String string){
        System.out.println("Creature (setName) " + string);
    }

    public void setID(int room, int serial){
        System.out.println("Creature (setID), room = " + room + ", serial = " + serial);
    }
}
