public class Dungeon {
    //static variable declarations?
    public Dungeon(String name, int width, int gameHeight){
        System.out.println("Dungeon (Dungeon), name = " + name + ", width = " + width + ", gameHeight = " + gameHeight);
    }
    public void getDungeon(String name, int width, int gameHeight){
        System.out.println("Dungeon (getDungeon)");
    }

    public void addRoom(Room room){
        System.out.println("Dungeon (addRoom)");
    }

    public void addCreature(Creature creature){
        System.out.println("Dungeon (addCreature)");
    }

    public void addPassage(Passage passage){
        System.out.println("Dungeon (addPassage)");
    }

    public void addItem(Item creature){
        System.out.println("Dungeon (addItem)");
    }

}
