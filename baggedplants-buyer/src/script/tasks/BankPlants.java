package script.tasks;

import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;
import script.core.BaggedPlantsBuyer;
import script.states.State;

public class BankPlants extends Task {


    @Override
    public int execute() {
        BaggedPlantsBuyer.action = "Banking plants";
        if (Bank.isOpen()) {
            Bank.depositAll(8431);
            State.setAmount(Bank.getCount(8431));
        } else {
            Time.sleepUntil(Bank::open, 2400, 4800);
        }
        if (!State.hasEnoughGold()) {
            Log.info("Not enough gold to continue.");
            return -1;
        }
        return 600;
    }

    @Override
    public boolean validate() {
        return State.enoughPlants();
    }
}
