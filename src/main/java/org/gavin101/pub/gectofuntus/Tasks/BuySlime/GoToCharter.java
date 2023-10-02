package org.gavin101.pub.gectofuntus.Tasks.BuySlime;

import org.gavin101.pub.gectofuntus.Constants;
import org.gavin101.pub.gectofuntus.GEctofuntus;
import org.gavin101.pub.gectofuntus.Task;
import org.gavin101.util.Util;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Movement;

public class GoToCharter extends Task {
    private final Constants c = new Constants();
    GEctofuntus main;

    public GoToCharter(GEctofuntus main) {
        super();
        super.name = "GoToCharter";
        this.main = main;
    }
    @Override
    public boolean activate() {
        return GEctofuntus.needSlime
                && !GEctofuntus.needToHop
                && !Inventory.isFull()
                && !Constants.CHARTER_CREW_AREA.contains(c.p().tile());
    }

    @Override
    public void execute() {
        GEctofuntus.currentState = Util.state("Going to charter");
        Movement.walkTo(Constants.CHARTER_CREW_AREA.getRandomTile());
    }
}
