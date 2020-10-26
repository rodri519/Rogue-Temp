public class Remove extends CreatureAction{
    private String name;
    private Creature owner;
    public Remove(String _name, Creature _owner){
        name = _name;
        owner = _owner;
        System.out.println("Remove (Remove)");
    }
}
