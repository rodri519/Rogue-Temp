package game;

public class Hallucinate extends ItemAction{
    private Player owner;
    public int startMoves;
    public int duration;
    public Hallucinate(Player _owner, Action ia) {
        owner = _owner;
        startMoves = _owner.totalMoves;
        duration = ia.actionIntValue;
        System.out.println("Hallucinate (Hallucinate)");
    }
}
