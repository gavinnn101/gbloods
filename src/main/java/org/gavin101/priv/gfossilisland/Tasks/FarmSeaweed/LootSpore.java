package org.gavin101.priv.gfossilisland.Tasks.FarmSeaweed;

import org.gavin101.priv.gfossilisland.Constants;
import org.gavin101.priv.gfossilisland.GFossilIsland;
import org.gavin101.util.Util;
import org.gavin101.priv.gfossilisland.Task;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

public class LootSpore extends Task {
    private final Constants c = new Constants();
    GFossilIsland main;

    public LootSpore(GFossilIsland main) {
        super();
        super.name = "LootSpore";
        this.main = main;
    }

    @Override
    public boolean activate() {
        GroundItem spore = GroundItems.stream().name("Seaweed spore").nearest().first();
        return spore.valid() && (Inventory.stream().name("Seaweed spore").isNotEmpty() || !Inventory.isFull());
    }

    @Override
    public void execute() {
        Util.state("Looting seaweed spores");
        GroundItem spore = GroundItems.stream().name("Seaweed spore").nearest().first();
        if (spore.valid()) {
            if (spore.inViewport() && spore.interact("Take")) {
                Condition.wait(() -> !spore.valid(), 150, 20);
            } else {
                System.out.println("Turning camera to spore");
                Camera.turnTo(spore);
            }
        }
    }
}
