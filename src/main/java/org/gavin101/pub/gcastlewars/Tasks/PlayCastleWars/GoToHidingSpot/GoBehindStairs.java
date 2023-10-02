package org.gavin101.pub.gcastlewars.Tasks.PlayCastleWars.GoToHidingSpot;

import org.gavin101.pub.gcastlewars.Constants;
import org.gavin101.pub.gcastlewars.GCastleWars;
import org.gavin101.pub.gcastlewars.Task;
import org.gavin101.util.Util;
import org.powbot.api.Condition;
import org.powbot.api.Tile;
import org.powbot.api.rt4.Movement;

public class GoBehindStairs extends Task {
    private final Constants c = new Constants();
    GCastleWars main;

    public GoBehindStairs(GCastleWars main) {
        super();
        super.name = "GoBehindStairs";
        this.main = main;
    }
    @Override
    public boolean activate() {
        return c.p().movementAnimation() == c.p().idleAnimation()
                && (Constants.ZAMORAK_BASE_TOP.contains(c.p().tile()) || Constants.SARA_BASE_TOP.contains(c.p().tile()))
                && (!Constants.ZAMORAK_HIDING_SPOTS.contains(c.p().tile()) && !Constants.SARA_HIDING_SPOTS.contains(c.p().tile())
        );
    }

    @Override
    public void execute() {
        GCastleWars.currentState = Util.state("Hiding behind stairs");
        Tile hidingSpot = null;
        if (Constants.ZAMORAK_BASE_TOP.contains(c.p().tile())) {
            System.out.println("Hiding behind zammy stairs");
            hidingSpot = Constants.ZAMORAK_HIDING_SPOTS.getRandomTile();
        } else if (Constants.SARA_BASE_TOP.contains(c.p().tile())) {
            System.out.println("Hiding behind sara stairs");
            hidingSpot = Constants.SARA_HIDING_SPOTS.getRandomTile();
        }
        if (hidingSpot != null && Movement.step(hidingSpot)) {
            Tile finalHidingSpot = hidingSpot;
            Condition.wait(() -> c.p().tile().equals(finalHidingSpot), 100, 50);
        }
    }
}
