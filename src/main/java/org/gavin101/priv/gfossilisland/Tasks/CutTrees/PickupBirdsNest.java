package org.gavin101.priv.gfossilisland.Tasks.CutTrees;


import org.gavin101.util.Util;
import org.gavin101.priv.gfossilisland.Constants;
import org.gavin101.priv.gfossilisland.GFossilIsland;
import org.gavin101.priv.gfossilisland.Task;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

public class PickupBirdsNest extends Task {
    private final Constants c = new Constants();
    GFossilIsland main;

    public PickupBirdsNest(GFossilIsland main) {
        super();
        super.name = "PickupBirdsNest";
        this.main = main;
    }
    @Override
    public boolean activate() {
        return GroundItems.stream().name("Bird nest").nearest().first().valid();
    }

    @Override
    public void execute() {
        GFossilIsland.currentState = Util.state("Picking up bird nest");
        GroundItem birdsNest = GroundItems.stream().name("Bird nest").nearest().first();
        if (Game.tab(Game.Tab.INVENTORY) && Inventory.isFull()) {
            dropItem();
        } else if (birdsNest.inViewport() && birdsNest.interact("Take")) {
            Condition.wait(() -> !birdsNest.valid(), 150, 20);
        }
    }

    public void dropItem() {
        if (Inventory.stream().name("Teak logs").isNotEmpty()) {
            GFossilIsland.currentState = Util.state("Dropping logs to make room for bird's nest.");
            if (!Inventory.selectedItem().name().equals("Teak logs")) {
                if (Inventory.stream().name("Teak logs").first().interact("Drop")) {
                    Condition.wait(() -> !Inventory.isFull(), 100, 20);
                }
            }
        }
    }
}
