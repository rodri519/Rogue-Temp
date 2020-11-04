package game;

public abstract class Item extends Displayable{
    private Creature owner;
    public String name;
    public int room;
    public int serial;

    public Item(){

    }

    public void setName(String _name){
        name = _name;
        System.out.println("Item (Item), name = " + name);
    }

    public void setID(int _room, int _serial){
        room = _room;
        serial = _serial;
        System.out.println("Item (setID), room = " + room + ", serial = " + serial);
    }
    public void setOwner(Creature _owner){
        owner = _owner;
        System.out.println("Item (setOwner)");
    }
}
