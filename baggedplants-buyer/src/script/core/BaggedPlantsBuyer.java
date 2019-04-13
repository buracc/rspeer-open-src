package script.core;

import org.rspeer.runetek.api.commons.StopWatch;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptCategory;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;
import script.tasks.*;

import java.awt.*;

@ScriptMeta(name = "BaggedPlantsBuyer", developer = "buracc", desc = "Buys bagged plants for you",
        category = ScriptCategory.OTHER, version = 0.02)
public class BaggedPlantsBuyer extends Script implements RenderListener {

    public static final Area PLANTS_SHOP = Area.rectangular(3021, 3377, 3016, 3373);
    public static String action = "Idle";
    private String elapsedTime = "";
    private static StopWatch time = null;

    private final Task[] tasks = {
            new GoBank(),
            new BankPlants(),
            new GoShop(),
            new BuyPlants(),
            new Hop()
    };

    @Override
    public void onStart() {
        time = StopWatch.start();
    }

    @Override
    public int loop() {
        elapsedTime = time.toElapsedString();
        if (!script.states.State.hasEnoughGold()) {
            Log.info("No coins found in inventory - stopping script");
            this.setStopping(true);
        }

        for (Task task : tasks) {
            if (task.validate()) {
                return task.execute();
            }
        }
        return 600;
    }

    @Override
    public void notify(RenderEvent renderEvent) {
        Graphics g = renderEvent.getSource();
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.drawString("BaggedPlantsBuyer v0.02 - Burak", 2, 270);
        g.drawString("Bagged plants in bank: " + script.states.State.amount, 2, 290);
        g.drawString("Current action: " + action, 2, 310);
        g.drawString("Time ran: " + elapsedTime, 2, 330);
    }
}
