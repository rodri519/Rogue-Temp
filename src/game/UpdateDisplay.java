package game;

public class UpdateDisplay extends CreatureAction{
    private String name;
    private Creature owner;
    public UpdateDisplay(String _name, Creature _owner){
        name = _name;
        owner = _owner;
        System.out.println("Update Display (UpdateDisplay)");
    }
}
