abstract class Creature extends Displayable{

    public void Creature(){
        System.out.println("Creature: ");
    }

    public void setHp(int h){
        System.out.println("setHp: ");
    }

    public void setHpMove(int hpm){
        System.out.println("setHpMove: ");
    }

    public void setDeathAction(CreatureAction da){
        System.out.println("setDeathAction: ");
    }

    public void setHitAction(CreatureAction ha){
        System.out.println("setHitAction: ");
    }
}
