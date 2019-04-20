package script;

import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Dialog;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.local.Health;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;

import java.util.function.Predicate;

@ScriptMeta(name = "Fighter", desc = "fights", developer = "burak")
public class Fighter extends Script {

    private static final String FOOD = "Salmon";
    private static final int MIN_EAT_HEALTH = 10;
    private static final String EAT_ACTION = "Eat";
    private static final String ATTACK_ACTION = "Attack";
    private static final String NPC_NAME = "Scorpion";

    @Override
    public int loop() {
        Player local = Players.getLocal();
        final Predicate<Npc> npcPred = x -> x.getName().equals(NPC_NAME) && (x.getTargetIndex() == local.getIndex() || x.getTargetIndex() == -1);
        Npc targetNpc = Npcs.getNearest(npcPred);
        Item food = Inventory.getFirst(FOOD);

        if (food == null) {
            return -1;
        }

        if (Dialog.canContinue()) {
            Dialog.processContinue();
        }

        if (Health.getCurrent() <= MIN_EAT_HEALTH) {
            food.interact(EAT_ACTION);
        }

        if (local.getTargetIndex() == -1
                && targetNpc != null
                && targetNpc.interact(ATTACK_ACTION)) {
            Time.sleepUntil(() -> local.getTargetIndex() != -1, 5000);
        }


        return 600;
    }
}
