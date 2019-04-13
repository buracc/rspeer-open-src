package script.tasks.traversing;

import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import script.data.information.PlayerState;
import script.data.settings.ScriptSettings;

public class MoveToDocks extends Task {

    private Npc seagull;
    private ScriptSettings s = ScriptSettings.getInstance();


    @Override
    public int execute() {
        if (Movement.getRunEnergy() > Random.nextInt(7, 13) && !Movement.isRunEnabled()) {
            Movement.toggleRun(true);
            return 1000;
        }

        if (!PlayerState.isTraversing()) {
            Movement.walkTo(s.getTrainArea().getArea().getCenter());
        }
        return 1000;
    }

    @Override
    public boolean validate() {
        seagull = Npcs.getNearest(x -> x.getName().equals("Seagull") && (x.getTargetIndex() == -1 || x.getTarget().equals(Players.getLocal())) && x.getHealthPercent() > 0);
        return seagull == null || Players.getLocal().distance(s.getTrainArea().getArea().getCenter()) > 15;
    }

    @Override
    public String toString() {
        return "Traversing";
    }
}
