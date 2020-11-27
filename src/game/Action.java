package game;

public class Action {
    public String msg;
    public int actionIntValue;
    public char actionCharValue;
    public String name;
    public String type;

    public Action(){}
    public Action(String _name, String _type){
        name = _name;
        type = _type;
        System.out.println("ItemAction (ItemAction), name = " + name + ", type = " + type);
    }

    public void setMessage(String _msg){
        msg = _msg;
        System.out.println("Action (setMessage) " + msg);
    }

    public void setIntValue(int _v){
        actionIntValue = _v;
        System.out.println("Action (GetDungeon) " + actionIntValue);
    }

    public void setCharValue(char _c){
        actionCharValue = _c;
        System.out.println("Action (setCharValue) " + actionCharValue);
    }


}
