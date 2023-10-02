package org.gavin101.priv.gfossilisland.Tasks.FarmSeaweed;

import org.gavin101.priv.gfossilisland.Constants;
import org.gavin101.priv.gfossilisland.GFossilIsland;
import org.gavin101.priv.gfossilisland.Task;
import org.powbot.api.Condition;
import org.powbot.api.rt4.GameObject;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Objects;

public class PickSeaweed extends Task {
    private final Constants c = new Constants();
    GFossilIsland main;

    public PickSeaweed(GFossilIsland main) {
        super();
        super.name = "PickSeaweed";
        this.main = main;
    }

    @Override
    public boolean activate() {
        GameObject seaweed = Objects.stream(10).type(GameObject.Type.INTERACTIVE).name("Seaweed").nearest().first();
        return seaweed.valid() && !Inventory.isFull();
    }

    @Override
    public void execute() {
        GameObject seaweed = Objects.stream().name("Seaweed").nearest().first();
        if (seaweed.interact("Pick")) {
            Condition.wait(() -> !seaweed.valid() || Inventory.isFull(), 150, 50);
        }
    }
}
