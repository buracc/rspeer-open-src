package script.states;

import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.api.commons.BankLocation;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Players;
import script.core.BaggedPlantsBuyer;

public class State {

    public static int amount = 0;

    public static boolean hopped = false;

    public static void setAmount(int amnt) {
        amount = amnt;
    }

    public static void setHopped(boolean hop) {
        hopped = hop;
    }

    public static boolean nearBuyArea() {
        return BaggedPlantsBuyer.PLANTS_SHOP.getCenter().distance(Players.getLocal()) < 15;
    }

    public static boolean nearBankArea() {
        return BankLocation.FALADOR_EAST.getPosition().distance(Players.getLocal()) < 15;
    }

    public static boolean enoughPlants() {
        return Inventory.isFull() || Inventory.getCount(8431) > 19;
    }

    public static boolean hasEnoughGold() {
        Item gold = Inventory.getFirst("Coins");
        return gold != null && gold.getStackSize() >= 20000;
    }
}
