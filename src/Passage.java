public class Passage extends Structure{

    public Passage(){
        System.out.println("Passage (Passage)");
    }

    public void setName(String string){
        System.out.println("Passage (setName)" + string);
    }

    public void setID(int room1, int room2){
        System.out.println("Passage (setID), room1 = " + room1 + ", room2 = " + room2);
    }
}
