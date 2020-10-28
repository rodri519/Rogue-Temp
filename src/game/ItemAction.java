package game;

public class ItemAction extends Action{
    private String name;
    private String type;
    public ItemAction(){};
    public ItemAction(String _name, String _type){
        name = _name;
        type = _type;
        System.out.println("ItemAction (ItemAction), name = " + name + ", type = " + type);
    }
}
