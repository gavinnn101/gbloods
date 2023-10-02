package org.gavin101.priv.gbloods.Tasks.CraftBloods;

import org.gavin101.priv.gbloods.Constants;
import org.gavin101.priv.gbloods.GBloods;
import org.gavin101.priv.gbloods.Task;
import org.gavin101.util.Util;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Camera;
import org.powbot.api.rt4.GameObject;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Objects;

public class MineRunestone extends Task {
    private final Constants c = new Constants();
    GBloods main;

    public MineRunestone(GBloods main) {
        super();
        super.name = "MineRunestone";
        this.main = main;
    }
    @Override
    public boolean activate() {
        return !Inventory.isFull()
                && c.p().animation() == -1
                && !c.p().inMotion()
                && Constants.DENSE_RUNESTONE_AREA.contains(c.p().tile());
    }

    @Override
    public void execute() {
        GBloods.currentState = Util.state("Mining runestone");
        GameObject runestone = Objects.stream(20).type(GameObject.Type.INTERACTIVE).name("Dense runestone").nearest().first();
        if (runestone.valid()) {
            if (runestone.inViewport() && runestone.interact("Chip")) {
                Condition.wait(() -> c.p().animation() != -1, 100, 20);
            } else {
                System.out.println("Turning camera to runestone");
                Camera.turnTo(runestone);
            }
        }
    }
}
