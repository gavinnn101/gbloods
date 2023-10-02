package org.gavin101.pub.gectofuntus.Tasks.CrushBones;

import org.gavin101.pub.gectofuntus.Constants;
import org.gavin101.pub.gectofuntus.GEctofuntus;
import org.gavin101.pub.gectofuntus.Task;
import org.gavin101.util.Util;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

public class GoToCrusher extends Task {
    private final org.gavin101.pub.gectofuntus.Constants c = new org.gavin101.pub.gectofuntus.Constants();
    GEctofuntus main;

    public GoToCrusher(GEctofuntus main) {
        super();
        super.name = "GoToCrusher";
        this.main = main;
    }
    @Override
    public boolean activate() {
        return org.gavin101.pub.gectofuntus.Constants.ALTAR_BOT_FLOOR.contains(c.p().tile())
                && Inventory.stream().name(GEctofuntus.boneType).count() == 13
                && c.p().animation() == -1;
    }

    @Override
    public void execute() {
        GEctofuntus.currentState = Util.state("Going to crusher");
        GameObject staircase = Objects.stream().name("Staircase").within(10).nearest().first();
        if (Game.closeOpenTab()) {
            // Inventory tab blocks view of staircase sometimes.
            Condition.wait(() -> Game.tab() == Game.Tab.NONE, 150, 20);
        }
        if (staircase.valid()) {
            if (staircase.inViewport() && staircase.interact("Climb-up")) {
                Condition.wait(() -> Constants.ALTAR_TOP_FLOOR.contains(c.p().tile()), 150, 40);
            } else {
                System.out.println("Turning camera to staircase");
                Camera.turnTo(staircase);
            }
        }
    }
}