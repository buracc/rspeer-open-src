package script.tasks;

import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.script.task.Task;
import script.core.BaggedPlantsBuyer;
import script.states.State;

public class GoShop extends Task {

    @Override
    public int execute() {
        if (!Movement.isRunEnabled() && Movement.getRunEnergy() > Random.nextInt(10, 30)) {
            BaggedPlantsBuyer.action = "Enabling run";
            Movement.toggleRun(true);
        }
        BaggedPlantsBuyer.action = "Going to shop";
        if (Movement.walkToRandomized(BaggedPlantsBuyer.PLANTS_SHOP.getCenter())) {
            Time.sleepUntil(State::nearBuyArea, Random.mid(1162, 2612));
        }
        return 600;
    }

    @Override
    public boolean validate() {
        return !State.nearBuyArea() && !State.enoughPlants() && State.hasEnoughGold();
    }
}
