public class Action {
    private String msg;
    private int v;
    private char c;
    public Action() { System.out.println("Action (Action)");}

    public void setMessage(String _msg){
        msg = _msg;
        System.out.println("Action (setMessage) " + msg);
    }

    public void setIntValue(int _v){
        v = _v;
        System.out.println("Action (GetDungeon) " + v);
    }

    public void setCharValue(char _c){
        c = _c;
        System.out.println("Action (setCharValue) " + c);
    }


}
