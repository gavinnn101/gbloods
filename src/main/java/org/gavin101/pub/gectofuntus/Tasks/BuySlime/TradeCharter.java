package org.gavin101.pub.gectofuntus.Tasks.BuySlime;

import org.gavin101.pub.gectofuntus.Constants;
import org.gavin101.pub.gectofuntus.GEctofuntus;
import org.gavin101.pub.gectofuntus.Task;
import org.gavin101.util.Util;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

public class TradeCharter extends Task {
    private final org.gavin101.pub.gectofuntus.Constants c = new org.gavin101.pub.gectofuntus.Constants();
    GEctofuntus main;

    public TradeCharter(GEctofuntus main) {
        super();
        super.name = "TradeCharter";
        this.main = main;
    }
    @Override
    public boolean activate() {
        return !GEctofuntus.needToHop
                && !Store.opened()
                && !Inventory.isFull()
                && Constants.CHARTER_CREW_AREA.contains(c.p().tile());
    }

    @Override
    public void execute() {
        GEctofuntus.currentState = Util.state("Trading charter crew");
        Npc charterCrew = Npcs.stream().within(10).name("Trader Crewmember").nearest().first();
        if (charterCrew.valid()) {
            if (charterCrew.inViewport() && charterCrew.interact("Trade")) {
                Condition.wait(Store::opened, 100, 20);
            } else {
                System.out.println("Turning camera to charterCrew");
                Camera.turnTo(charterCrew);
            }
        }
    }
}
