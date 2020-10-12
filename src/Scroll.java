public class Scroll extends Item{

    public Scroll(String name){
        System.out.println("Scroll (Scroll)" + name);
    }

    public void setID(int room, int serial){
        System.out.println("Scroll (setID), room = " + room + ", serial = " + serial);
    }
}
