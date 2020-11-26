package game;

public class Action {
    private String msg;
    public int actionIntValue;
    public char actionCharValue;
    public Action() { System.out.println("Action (Action)");}

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
