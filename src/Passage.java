public class Passage extends Structure{
    private String string;
    private int room1;
    private int room2;
    public Passage(){
        System.out.println("Passage (Passage)");
    }

    public void setName(String _string){
        string = _string;
        System.out.println("Passage (setName)" + string);
    }

    public void setID(int _room1, int _room2){
        room1 = _room1;
        room2 = _room2;
        System.out.println("Passage (setID), room1 = " + room1 + ", room2 = " + room2);
    }
}
