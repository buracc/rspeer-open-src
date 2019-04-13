package script.tasks.interaction.combat;

import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Combat;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;
import script.data.settings.ScriptSettings;

public class Attack extends Task {

    private ScriptSettings s = ScriptSettings.getInstance();
    private int attkLvl;
    private int strLvl;
    private int defLvl;

    @Override
    public int execute() {
        if (Movement.getRunEnergy() > Random.nextInt(7, 13) && !Movement.isRunEnabled()) {
            Movement.toggleRun(true);
            return 1000;
        }

        if ((attkLvl >= s.getTrainAttack() && s.getTrainAttack() > 0) && (strLvl >= s.getTrainStrength() && s.getTrainStrength() > 0) && (defLvl >= s.getTrainDefence() && s.getTrainDefence() > 0)) {
            Log.severe("Stopped after reaching goal.");
            return -1;
        }

        Npc seagull = Npcs.getNearest(x -> x.getName().equals("Seagull") && (x.getTargetIndex() == -1 ||
                x.getTarget().equals(Players.getLocal())) && x.getHealthPercent() > 0 && s.getTrainArea().getArea().contains(x));

        if (Combat.getSelectedStyle() != selectStyle()) {
            Log.info("Setting combat style");
            Combat.select(selectStyle());
        }

        if (!Players.getLocal().isMoving() && seagull != null) {
            if (seagull.interact("Attack")) {
                Time.sleepUntil(() -> Players.getLocal().getTargetIndex() != -1, 5000);
            }
        }
        return 1000;
    }

    @Override
    public boolean validate() {
        attkLvl = Skills.getLevel(Skill.ATTACK);
        strLvl = Skills.getLevel(Skill.STRENGTH);
        defLvl = Skills.getLevel(Skill.DEFENCE);
        return Players.getLocal().getTargetIndex() == -1;
    }

    private int selectStyle() {
        if (s.getTrainStrength() > 0 && strLvl < s.getTrainStrength()) {
            return 1;
        } else if (s.getTrainAttack() > 0 && attkLvl < s.getTrainAttack()) {
            return 0;
        } else if (s.getTrainDefence() > 0 && defLvl < s.getTrainDefence()){
            return 3;
        } else {
            return Combat.getSelectedStyle();
        }
    }

    @Override
    public String toString() {
        return "Attacking";
    }
}
