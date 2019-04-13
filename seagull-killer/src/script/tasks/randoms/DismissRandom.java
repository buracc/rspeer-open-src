package script.tasks.randoms;

import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;

public class DismissRandom extends Task {

    Npc random;

    @Override
    public boolean validate() {
        random = Npcs.getNearest(npc -> npc.containsAction("Dismiss") && npc.getTargetIndex() == Players.getLocal().getIndex());
        return random != null;
    }

    @Override
    public int execute() {
        random.interact("Dismiss");
        return 3000;
    }

    @Override
    public String toString() {
        return "Random";
    }
}
