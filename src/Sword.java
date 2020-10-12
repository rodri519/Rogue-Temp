public class Sword extends Item{

    public Sword(String name){
        System.out.println("Sword (Sword)" + name);
    }

    public void setID(int room, int serial){
        System.out.println("Sword (setID), room = " + room + ", serial = " + serial);
    }
}
