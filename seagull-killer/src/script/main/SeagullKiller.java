package script.main;

import org.rspeer.runetek.api.commons.StopWatch;
import org.rspeer.runetek.event.EventDispatcher;
import org.rspeer.script.ScriptCategory;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.Task;
import org.rspeer.script.task.TaskScript;
import script.rendering.paint.PaintRenderer;
import script.tasks.interaction.combat.Attack;
import script.tasks.interaction.ground.Pickup;
import script.tasks.interaction.interfaces.ContinueDialog;
import script.tasks.interaction.inventory.Bury;
import script.tasks.randoms.DismissRandom;
import script.tasks.traversing.MoveToDocks;
import script.userinterface.Gui;

import javax.swing.*;

@ScriptMeta(version = 0.02, name = "SeagullKiller", category = ScriptCategory.COMBAT, developer = "buracc", desc = "Kills seagulls")
public class SeagullKiller extends TaskScript {

    public static StopWatch time = null;
    public PaintRenderer paintRenderer;
    public EventDispatcher eventDispatcher;
    // public int startXp;

    Task[] tasks = {
            new DismissRandom(),
            new ContinueDialog(),
            new MoveToDocks(),
            new Bury(),
            new Pickup(),
            new Attack()
    };

    @Override
    public void onStart() {
        // startXp = Skills.getExperience(Skill.MAGIC);
        this.setPaused(true);
        SwingUtilities.invokeLater(() -> new Gui(this));
        submit(tasks);
    }

    @Override
    public void onStop() {
        eventDispatcher.deregister(paintRenderer);
    }
}