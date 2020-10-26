public class Room extends Structure{
    private int room;
    private Creature Monster;
    public Room(){
        System.out.println("Room (Room)");
    }

    public void setID(int _room){
        room = _room;
        System.out.println("Room (setID)" + room);
    }

    public void setCreature(Creature _Monster){
        Monster = _Monster;
        System.out.println("Room (setCreature)");
    }
}
