package script;

import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.api.Game;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Shop;
import org.rspeer.runetek.api.component.WorldHopper;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.providers.RSWorld;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;

import java.util.ArrayList;
import java.util.function.Predicate;

@ScriptMeta(name = "ShopSeller", developer = "buracc", desc = "Sells items to general shops")
public class ShopSeller extends Script {

    private static final ArrayList<String> ITEMS_TO_SELL = new ArrayList<>();
    private static final String SHOP_KEEPER_NAME = "Shop keeper";
    private static final String NPC_TRADE_OPTION = "Trade";
    private static final Predicate<RSWorld> SAFE_WORLD = x -> x.isMembers() && !x.isDeadman() && !x.isPVP()
        && !x.isSeasonDeadman() && !x.isSkillTotal() && !x.isTournament();

    static {
        ITEMS_TO_SELL.add("Unpowered orb");
        ITEMS_TO_SELL.add("Lantern lens");
    }

    @Override
    public int loop() {
        Npc shopKeeper = Npcs.getNearest(SHOP_KEEPER_NAME);

        if (!Shop.isOpen()
                && shopKeeper != null
                && shopKeeper.interact(NPC_TRADE_OPTION)
                && Time.sleepUntil(Shop::isOpen, 3000)) {
            return 100;
        }

        if (Shop.isOpen()
                && !sellItems()
                && Shop.close()
                && Time.sleepUntil(() -> !Shop.isOpen(), 3000)
                && WorldHopper.open()
                && Time.sleepUntil(WorldHopper::isOpen, 3000)
                && WorldHopper.randomHop(SAFE_WORLD)
                && Time.sleepUntil(Game::isLoggedIn, 3000)) {
            return 100;
        }

        return 1000;
    }

    private boolean sellItems() {
        for (String s : ITEMS_TO_SELL) {
            if (Shop.getQuantity(s) >= 10) {
                return false;
            }

            if (Shop.sellTen(s)) {
                Time.sleepUntil(() -> Shop.getQuantity(s) >= 10, 3000);
            }
        }

        return true;
    }
}
