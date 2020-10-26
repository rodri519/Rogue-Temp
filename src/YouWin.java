public class YouWin extends CreatureAction{
    private String name;
    private Creature owner;
    public YouWin(String _name, Creature _owner){
        name = _name;
        owner = _owner;
        System.out.println("YouWin (YouWin)");
    }
}
