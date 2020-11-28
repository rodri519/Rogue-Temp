package game;

public class Hallucinate {
    private Player owner;
    public int startMoves;
    public int duration;
    public Hallucinate(Player _owner, ItemAction ia) {
        owner = _owner;
        startMoves = _owner.totalMoves;
        duration = ia.actionIntValue;
        System.out.println("Hallucinate (Hallucinate)");
    }
}
