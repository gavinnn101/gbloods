package org.gavin101.pub.gectofuntus.Tasks.Common;

import org.gavin101.pub.gectofuntus.GEctofuntus;
import org.gavin101.pub.gectofuntus.Task;
import org.gavin101.pub.gectofuntus.Constants;
import org.gavin101.util.Util;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

public class GoToBank extends Task {
    private final org.gavin101.pub.gectofuntus.Constants c = new org.gavin101.pub.gectofuntus.Constants();
    GEctofuntus main;

    public GoToBank(GEctofuntus main) {
        super();
        super.name = "GoToBank";
        this.main = main;
    }
    @Override
    public boolean activate() {
         switch (GEctofuntus.currentTask) {
            case "BuySlime":
                    return (Inventory.isFull() || Inventory.stream().name("Coins").first().stackSize() < 1_000) && !org.gavin101.pub.gectofuntus.Constants.BANK_AREA.contains(c.p().tile());
            case "CrushBones":
                    return Inventory.stream().name("Pot").isEmpty() && !org.gavin101.pub.gectofuntus.Constants.BANK_AREA.contains(c.p().tile());
            case "OfferBones":
                return !org.gavin101.pub.gectofuntus.Constants.BANK_AREA.contains(c.p().tile())
                    && (Inventory.stream().name(GEctofuntus.bonemealType).isEmpty() || Inventory.stream().name("Bucket of slime").isEmpty());
            case "GetItemCounts":
                return !org.gavin101.pub.gectofuntus.Constants.BANK_AREA.contains(c.p().tile());
        }
        return false;
    }

    @Override
    public void execute() {
        GEctofuntus.currentState = Util.state("Going to bank "+GEctofuntus.currentTask);
        getToBank();
    }

    public void getToBank() {
        if (org.gavin101.pub.gectofuntus.Constants.PORT_PHASMATYS.contains(c.p().tile())) {
            Movement.walkTo(org.gavin101.pub.gectofuntus.Constants.BANK_AREA.getRandomTile());
        } else if (!org.gavin101.pub.gectofuntus.Constants.BARRIER_ALTAR_SIDE.contains(c.p().tile())) {
            Movement.walkTo(org.gavin101.pub.gectofuntus.Constants.BARRIER_ALTAR_SIDE.getRandomTile());
        } else {
            enterBarrier();
        }
    }

    public void enterBarrier() {
        GameObject barrier = Objects.stream(10).type(GameObject.Type.INTERACTIVE).name("Energy Barrier").nearest().first();
        GEctofuntus.currentState = Util.state("Interacting with barrier");
        if (barrier.valid()) {
            if (barrier.inViewport() && barrier.interact("Pass") && Condition.wait(Chat::canContinue, 150, 20)) {
                GEctofuntus.currentState = Util.state("Clicking continue on barrier dialog");
                if (Chat.clickContinue()) {
                    GEctofuntus.currentState = Util.state("Waiting to get inside");
                    Condition.wait(() -> Players.local().tile().equals(org.gavin101.pub.gectofuntus.Constants.BARRIER_TILE_WEST) || Players.local().tile().equals(Constants.BARRIER_TILE_EAST), 150, 20);
                }
            } else {
                System.out.println("Turning camera to barrier");
                Camera.turnTo(barrier);
            }
        }
    }
}
