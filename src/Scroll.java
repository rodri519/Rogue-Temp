public class Scroll extends Item{
    private String name;
    private int room;
    private int serial;
    public Scroll(String _name){
        name = _name;
        System.out.println("Scroll (Scroll)" + name);
    }

    public void setID(int _room, int _serial){
        room = _room;
        serial = _serial;
        System.out.println("Scroll (setID), room = " + room + ", serial = " + serial);
    }
}
