package game;

import java.util.ArrayList;
import java.util.List;

public class Room extends Structure{
    public List<Monster> monsters = new ArrayList<Monster>();
    public List<Item> items = new ArrayList<Item>();
    public Player player;


    public Room(){
        System.out.println("Room (Room)");
    }

    public void setID(int room){
        System.out.println("Room (setID)" + room);
    }

    public void setMonster(Monster monster){
        System.out.println("Room (setCreature)");
        monsters.add(monster);
    }

    public void setPlayer(Player _player){
        System.out.println("Room (setCreature)");
        player = _player;
    }

    public void setItem(Item item){
        System.out.println("Room (setCreature)");
        items.add(item);
    }

}
