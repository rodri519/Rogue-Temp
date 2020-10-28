package game;

public class Sword extends Item{
    private String name;
    private int room;
    private int serial;
    public Sword(String _name){
        name = _name;
        System.out.println("Sword (Sword)" + name);
    }

    public void setID(int _room, int _serial){
        room = _room;
        serial = _serial;
        System.out.println("Sword (setID), room = " + room + ", serial = " + serial);
    }
}
