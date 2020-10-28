package game;

public class Player extends Creature{
    private Item sword;
    private Item armor;
    public Player() {
        System.out.println("Player (Player)");
    }

    public void setWeapon(Item _sword){
        sword = _sword;
        System.out.println("Player (setWeapon)");
    }

    public void setArmor(Item _armor){
        armor = _armor;
        System.out.println("Player (setArmor)");
    }

}
