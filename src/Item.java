public abstract class Item extends Displayable{
    private Creature owner;
    public void setOwner(Creature _owner){
        owner = _owner;
        System.out.println("Item (setOwner)");
    }
}
