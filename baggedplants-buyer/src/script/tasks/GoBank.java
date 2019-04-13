package script.tasks;

import org.rspeer.runetek.api.commons.BankLocation;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.script.task.Task;
import script.core.BaggedPlantsBuyer;
import script.states.State;

public class GoBank extends Task {
    @Override
    public int execute() {
        if (!Movement.isRunEnabled() && Movement.getRunEnergy() > Random.nextInt(10, 30)) {
            BaggedPlantsBuyer.action = "Enabling run";
            Movement.toggleRun(true);
        }

        BaggedPlantsBuyer.action = "Going to bank";
        if (Movement.walkToRandomized(BankLocation.FALADOR_EAST.getPosition())) {
            Time.sleepUntil(State::nearBankArea, Random.mid(1516, 2123));
        }
        return 600;
    }

    @Override
    public boolean validate() {
        return !State.nearBankArea() && State.enoughPlants();
    }
}
