package script.tasks;

import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Shop;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.script.task.Task;
import script.core.BaggedPlantsBuyer;
import script.states.State;
import script.util.PlantShop;

public class BuyPlants extends Task {

    @Override
    public int execute() {
        BaggedPlantsBuyer.action = "Buying plants";
        State.setHopped(false);
        if (Shop.isOpen()) {
            if (PlantShop.shopHasBaggedPlants()) {
                Shop.buyFifty(8431);
            } else {
                PlantShop.setIsEmpty(true);
                BaggedPlantsBuyer.action = "Closing shop and hopping";
            }
        } else {
            Npcs.getNearest(5423).interact("Trade");
            Time.sleep(2000);
        }
        return 600;
    }

    @Override
    public boolean validate() {
        return !State.enoughPlants() && State.hasEnoughGold() && State.nearBuyArea() && !PlantShop.isEmpty;
    }
}

