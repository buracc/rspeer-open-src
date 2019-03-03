package script;

import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.Production;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;

import java.util.HashMap;
import java.util.function.Predicate;

@ScriptMeta(desc = "Smelts", developer = "buracc", name = "Smelter")
public final class Smelter extends Script {

    private static final int PRIMARY_ID = 1781;
    private static final int SECONDARY_ID = 1783;
    private static final int FURNACE_ID = 16469;
    private static final int BANK_BOOTH_ID = 6943;
    private static final Position BANK_BOOTH_POS = new Position(3096, 3493);
    private static final int WITHDRAW_AMOUNT = 14;
    private static final int MIN_RUN_ENERGY = 3;

    private static final Predicate<Item> PRIMARY_PRED = x -> x.getId() == SECONDARY_ID;
    private static final Predicate<Item> SECONDARY_PRED = x -> x.getId() == PRIMARY_ID;
    private static final HashMap<Integer, Integer> ITEMS = new HashMap<>();
    private static final Predicate<SceneObject> BANK_BOOTH_PRED = x -> x.getId() == BANK_BOOTH_ID && x.getPosition().equals(BANK_BOOTH_POS);

    private long anim = 0;

    static {
        ITEMS.put(SECONDARY_ID, WITHDRAW_AMOUNT);
        ITEMS.put(PRIMARY_ID, WITHDRAW_AMOUNT);
    }

    @Override
    public int loop() {
        if (Production.isOpen() && Production.initiate()) {
            return 1000;
        }

        if (Inventory.contains(PRIMARY_PRED)
                && Inventory.contains(SECONDARY_PRED)
                && !isAnimating()
                && !Players.getLocal().isMoving()
                && SceneObjects.getNearest(FURNACE_ID).interact(x -> true)) {
            return 1500;
        }

        if (Bank.isOpen()
                && Bank.depositInventory()
                && Time.sleepUntil(Inventory::isEmpty, 3000)) {
            for (int i : ITEMS.keySet()) {
                Bank.withdraw(i, ITEMS.get(i));
                Time.sleep(150, 250);
            }
            return 800;
        }

        if (!Players.getLocal().isMoving()
                && !Inventory.contains(PRIMARY_PRED)
                && !Inventory.contains(SECONDARY_PRED)
                && SceneObjects.getNearest(BANK_BOOTH_PRED).interact(x -> true)) {
            return 1500;
        }

        if (!Movement.isRunEnabled() && Movement.getRunEnergy() > MIN_RUN_ENERGY && Players.getLocal().isMoving()) {
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

