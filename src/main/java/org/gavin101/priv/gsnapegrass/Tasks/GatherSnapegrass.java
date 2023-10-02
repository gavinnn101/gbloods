package org.gavin101.priv.gsnapegrass.Tasks;

import org.gavin101.priv.gsnapegrass.GSnapegrass;
import org.gavin101.util.Util;
import org.gavin101.priv.gsnapegrass.Constants;
import org.gavin101.priv.gsnapegrass.Task;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

public class GatherSnapegrass extends Task {
    private final Constants c = new Constants();
    GSnapegrass main;

    public GatherSnapegrass(GSnapegrass main) {
        super();
        super.name = "GatherSnapegrass";
        this.main = main;
    }
    @Override
    public boolean activate() {
        return !Inventory.isFull() && Constants.SNAPEGRASS_AREA.contains(c.p().tile());
    }

    @Override
    public void execute() {
        GSnapegrass.currentState = Util.state("Gathering snape grass");
        GroundItem snapegrass = GroundItems.stream().name("Snape grass").nearest().first();
        if (snapegrass.valid()) {
            if (snapegrass.inViewport() && snapegrass.interact("Take")) {
                Condition.wait(() -> !snapegrass.valid(), 150, 20);
            } else {
                System.out.println("Turning camera to snape grass");
                Camera.turnTo(snapegrass);
            }
        }
    }
}
