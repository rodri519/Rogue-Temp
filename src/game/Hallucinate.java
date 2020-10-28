package game;

public class Hallucinate extends ItemAction{
    private Creature owner;
    public Hallucinate(Creature _owner) {
        owner = _owner;
        System.out.println("Hallucinate (Hallucinate)");
    }
}
