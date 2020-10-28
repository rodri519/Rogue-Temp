package game;

public class CreatureAction extends Action{
    private String name;
    private String type;
    public CreatureAction(){};
    public CreatureAction(String _name, String _type){
        name = _name;
        type = _type;
        System.out.println("CreatureAction (CreatureAction), name = " + name + ", type = " + type);
    }
}
