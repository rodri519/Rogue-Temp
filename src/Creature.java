public abstract class Creature extends Displayable{

    public Creature(){
        System.out.println("Creature (Creature)");
    }

    public void setHp(int h){
        System.out.println("Creature (setHp) " + h);
    }

    public void setHpMove(int hpm){
        System.out.println("Creature (setHpMove) " + hpm);
    }

    public void setDeathAction(CreatureAction da){
        System.out.println("Creature (setDeathAction) " + da);
    }

    public void setHitAction(CreatureAction ha){
        System.out.println("Creature (setHitAction) " + ha);
    }

    public void setName(String string){
        System.out.println("Creature (setName) " + string);
    }

    public void setID(int room, int serial){
        System.out.println("Creature (setID), room = " + room + ", serial = " + serial);
    }
}
