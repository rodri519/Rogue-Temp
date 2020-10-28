package game;

import java.util.ArrayList;
import java.util.List;

public class Room extends Structure{
    public List<Creature> creatures = new ArrayList<Creature>();
    public List<Item> items = new ArrayList<Item>();


    public Room(){
        System.out.println("Room (Room)");
    }

    public void setID(int room){
        System.out.println("Room (setID)" + room);
    }

    public void setCreature(Creature creature){
        System.out.println("Room (setCreature)");
        creatures.add(creature);
    }

    public void setItem(Item item){
        System.out.println("Room (setCreature)");
        items.add(item);
    }

}
