package script.tasks;

import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.api.Game;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.WorldHopper;
import org.rspeer.script.task.Task;
import script.core.BaggedPlantsBuyer;
import script.states.State;
import script.util.PlantShop;

public class Hop extends Task {

    @Override
    public int execute() {
        closeAndHop();
        return 600;
    }

    @Override
    public boolean validate() {
        return PlantShop.isEmpty && !State.hopped;
    }

    private static void closeAndHop(){
        InterfaceComponent component = Interfaces.getComponent(300,1,11); //interface component for closing shop
        if(component != null){
            component.interact("Close");
            Time.sleep(1800);
        }
        Time.sleepUntil(WorldHopper::open, 2400, 4800);
        randomSafeP2P();
    }

    private static boolean randomSafeP2P(){
        BaggedPlantsBuyer.action = "Hopping";
        State.setHopped(true);
        PlantShop.setIsEmpty(false);
        return WorldHopper.randomHop(world -> world.getId() != Game.getClient().getCurrentWorld() && world.isMembers() && !world.isPVP()  && !world.isSkillTotal()
                && !world.isTournament() && !world.isHighRisk()
                && !world.isDeadman() && !world.isSeasonDeadman()
        );
    }
}
