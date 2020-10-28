package game;

public class Armor extends Item{
    private String name;
    private String string;
    private int room;
    private int serial;
    public Armor(String _name){
        name = _name;
        System.out.println("Armor (Armor), name = " + name);
    }

    public void setName(String _string){
        string = _string;
        System.out.println("Armor (setName) " + string);
    }

    public void setID(int _room, int _serial){
        room = _room;
        serial = _serial;
        System.out.println("Armor (setID), room = " + room + ", serial = " + serial);
    }
}
