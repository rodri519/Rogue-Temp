
public class Action {
    public Action() { System.out.println("Action (Action)");}

    public void setMessage(String msg){
        System.out.println("Action (setMessage) " + msg);
    }

    public void setIntValue(int v){
        System.out.println("Action (GetDungeon) " + v);
    }

    public void setCharValue(char c){
        System.out.println("Action (setCharValue) " + c);
    }


}
