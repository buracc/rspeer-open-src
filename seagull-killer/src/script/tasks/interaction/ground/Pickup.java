package script.tasks.interaction.ground;

import org.rspeer.runetek.adapter.scene.Pickable;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.api.scene.Pickables;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import script.data.settings.ScriptSettings;

public class Pickup extends Task {

    private ScriptSettings s = ScriptSettings.getInstance();
    private Pickable groundBones;

    @Override
    public int execute() {
        if (groundBones.interact("Take")) {
            Time.sleepUntil(() -> Inventory.contains("Bones"), 3000);
        }
        return 1000;
    }

    @Override
    public boolean validate() {
        groundBones = Pickables.getNearest(x -> x.getName().equals("Bones") && x.distance(Players.getLocal()) < 20 && x.distance(s.getTrainArea().getArea().getCenter()) < 20);
        return s.isDoBury() && Skills.getLevel(Skill.PRAYER) < s.getTrainPrayer() && groundBones != null && Players.getLocal().getTargetIndex() == -1;
    }

    @Override
    public String toString() {
        return "Picking";
    }
}
