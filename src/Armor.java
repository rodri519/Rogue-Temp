public class Armor extends Item{

    public Armor(String name){
        System.out.println("Armor (Armor), name = " + name);
    }

    public void setName(String string){
        System.out.println("Armor (setName) " + string);
    }

    public void setID(int room, int serial){
        System.out.println("Armor (setID), room = " + room + ", serial = " + serial);
    }
}
