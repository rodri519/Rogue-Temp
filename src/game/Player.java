package game;

import java.util.ArrayList;
import java.util.List;

public class Player extends Creature{
    public Item sword;
    public Item armor;
    public int totalMoves;
    public Hallucinate hallucinate;
    public List<Item> pack = new ArrayList<Item>();
    public Player() {
        totalMoves = 0;
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

    public void addItem(Item item) {
        pack.add(item);
    }

}
