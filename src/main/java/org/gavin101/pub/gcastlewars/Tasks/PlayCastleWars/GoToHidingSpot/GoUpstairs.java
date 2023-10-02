package org.gavin101.pub.gcastlewars.Tasks.PlayCastleWars.GoToHidingSpot;

import org.gavin101.pub.gcastlewars.Constants;
import org.gavin101.pub.gcastlewars.GCastleWars;
import org.gavin101.pub.gcastlewars.Task;
import org.gavin101.util.Util;
import org.powbot.api.Condition;
import org.powbot.api.rt4.GameObject;
import org.powbot.api.rt4.Objects;

public class GoUpstairs extends Task {
    private final Constants c = new Constants();
    GCastleWars main;

    public GoUpstairs(GCastleWars main) {
        super();
        super.name = "GoUpstairs";
        this.main = main;
    }
    @Override
    public boolean activate() {
        return c.p().movementAnimation() == c.p().idleAnimation() &&
                (Constants.ZAMORAK_BASE_MIDDLE.contains(c.p().tile()) || Constants.SARA_BASE_MIDDLE.contains(c.p().tile()));
    }

    @Override
    public void execute() {
        GCastleWars.currentState = Util.state("Going up ladder");
        GameObject ladder = Objects.stream().name("Ladder")
                .filtered(i -> i.actions().contains("Climb-up")).nearest().first();
        if (ladder.valid() && ladder.interact("Climb-up", false)) {
            Condition.wait(() -> Constants.ZAMORAK_BASE_TOP.contains(c.p().tile())
                    || Constants.SARA_BASE_TOP.contains(c.p().tile()), 50, 20);
        }

    }
}
