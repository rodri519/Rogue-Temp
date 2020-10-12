public class Room extends Structure{

    public Room(){
        System.out.println("Room (Room)");
    }

    public void setID(int room){
        System.out.println("Room (setID)" + room);
    }

    public void setCreature(Creature Monster){
        System.out.println("Room (setCreature)");
    }
}
