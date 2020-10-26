public class Dungeon {
    //static variable declarations?
    private String name;
    private int width;
    private int gameHeight;
    public Dungeon(String _name, int _width, int _gameHeight){
        name = _name;
        width = _width;
        gameHeight = _gameHeight;
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
