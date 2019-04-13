package script.rendering.paint;

import script.main.SeagullKiller;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.RenderEvent;

import java.awt.*;

public class PaintRenderer implements RenderListener {

    public static String action;
    private SeagullKiller main;
    //private static final int LVL_75_XP = 1210421;

    public PaintRenderer(SeagullKiller main) {
        this.main = main;
    }

    @Override
    public void notify(RenderEvent renderEvent) {
        Player me = Players.getLocal();
        Graphics g = renderEvent.getSource();
        /* int xpGained = Skills.getExperience(Skill.MAGIC) - main.startXp;
        int hourlyRate = (int) SeagullKiller.time.getHourlyRate(xpGained);
        int xpLeft = LVL_75_XP - Skills.getExperience(Skill.MAGIC);
        int hoursLeft = xpLeft / hourlyRate; */


        if (me.getTargetIndex() != -1)
            me.getTarget().getPosition().outline(g);

        int x = 372;
        int y = 19;

        if (main.getCurrent() != null) {
            action = main.getCurrent().toString();
        } else {
            action = "Idle";
        }

        g.setColor(new Color(0, 0, 0, 0.5f));
        g.fillRect(x - 5, y - 15, 150, 100);
        g.setColor(Color.red);

        g.setColor(Color.decode("#B63EA3"));
        g.drawString("SeagullKiller", x, y);
        g.setColor(Color.GREEN);

        g.drawString("Runtime: " + SeagullKiller.time.toElapsedString(), x, y += 11);
        g.drawString("Status: " + action, x, y += 11);
        /* g.drawString("XP/h: " + hourlyRate, x, y += 11);
        g.drawString("XP left: " + xpLeft, x, y += 11);
        g.drawString("H left: " + hoursLeft, x, y += 11); */
    }
}
