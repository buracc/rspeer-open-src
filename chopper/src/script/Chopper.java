package script;

import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;

@ScriptMeta(developer = "burak", desc = "chops", name = "Chopper")
public class Chopper extends Script {

    private static final String TREE_NAME = "Oak";
    private static final String DROP_ACTION = "Drop";
    private static final String CHOP_DOWN_ACTION = "Chop down";

    @Override
    public int loop() {
        SceneObject tree = SceneObjects.getNearest(TREE_NAME);

        if (Inventory.isFull()) {
            for (Item i : Inventory.getItems()) {
                i.interact(DROP_ACTION);
                Time.sleep(150, 250);
            }

            return 3000;
        }

        if (!Players.getLocal().isAnimating()
                && !Players.getLocal().isMoving()
                && tree != null) {
            tree.interact(CHOP_DOWN_ACTION);
        }

        return 3000;
    }
}
