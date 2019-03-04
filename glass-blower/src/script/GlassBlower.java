package script;

import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.Production;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;

@ScriptMeta(name = "GlassBlower", developer = "buracc", desc = "Blows glass")
public final class GlassBlower extends Script {

    private static final int GLASSBLOWING_PIPE_ID = 1785;
    private static final int MOLTEN_GLASS_ID = 1775;
    private static final int MIN_RUN_ENERGY = 3;
    private static final int PRODUCE_INDEX = 6;
    private static final String USE_ACTION = "Use";

    private long anim = 0;

    @Override
    public int loop() {
        if (Production.isOpen()
                && Production.initiate(PRODUCE_INDEX)
                && Time.sleepUntil(this::isAnimating, 3000)) {
            return 1000;
        }

        if (Inventory.containsAll(GLASSBLOWING_PIPE_ID, MOLTEN_GLASS_ID)
                && !isAnimating()
                && Inventory.getFirst(GLASSBLOWING_PIPE_ID).interact(USE_ACTION)
                && Time.sleepUntil(Inventory::isItemSelected, 3000)
                && Inventory.getFirst(MOLTEN_GLASS_ID).interact(USE_ACTION)
                && Time.sleepUntil(Production::isOpen, 3000)) {
            return 100;
        }

        if (Bank.isOpen()
                && Bank.depositAllExcept(GLASSBLOWING_PIPE_ID)
                && Time.sleepUntil(() -> Inventory.containsOnly(GLASSBLOWING_PIPE_ID), 3000)
                && Bank.withdrawAll(MOLTEN_GLASS_ID)
                && Time.sleepUntil(() -> Inventory.contains(MOLTEN_GLASS_ID), 3000)) {
            return 300;
        }

        if (!Players.getLocal().isMoving()
                && !Inventory.containsAll(GLASSBLOWING_PIPE_ID, MOLTEN_GLASS_ID)
                && Bank.open()
                && Time.sleepUntil(Bank::isOpen, 3000)) {
            return 1000;
        }

        if (!Movement.isRunEnabled()
                && Movement.getRunEnergy() > MIN_RUN_ENERGY
                && Players.getLocal().isMoving()) {
            Movement.toggleRun(true);
        }
        return 600;
    }

    private boolean isAnimating() {
        if (Players.getLocal().isAnimating()) {
            anim = System.currentTimeMillis();
            return true;
        }

        return System.currentTimeMillis() <= (anim + Random.high(2200, 3000));
    }
}
