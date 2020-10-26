public class DropPack extends CreatureAction{
    private String name;
    private Creature owner;
    public DropPack(String _name, Creature _owner){
        name = _name;
        owner = _owner;
        System.out.println("DropPack (DropPack)");
    }
}
