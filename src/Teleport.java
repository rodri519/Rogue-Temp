public class Teleport extends CreatureAction{
    private String name;
    private Creature owner;
    public Teleport(String _name, Creature _owner){
        name = _name;
        owner = _owner;
        System.out.println("Teleport (Teleport)");
    }
}
