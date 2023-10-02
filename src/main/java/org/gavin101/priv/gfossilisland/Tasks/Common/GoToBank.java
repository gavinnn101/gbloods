package org.gavin101.priv.gfossilisland.Tasks.Common;

import org.gavin101.util.Util;
import org.gavin101.priv.gfossilisland.Constants;
import org.gavin101.priv.gfossilisland.GFossilIsland;
import org.gavin101.priv.gfossilisland.Task;
import org.powbot.api.Condition;
import org.powbot.api.rt4.GameObject;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Movement;
import org.powbot.api.rt4.Objects;

public class GoToBank extends Task {
    private final Constants c = new Constants();
    GFossilIsland main;

    public GoToBank(GFossilIsland main) {
        super();
        super.name = "GoToBank";
        this.main = main;
    }
    @Override
    public boolean activate() {
        return Inventory.isFull() && !Constants.BANK_AREA.contains(c.p().tile());
    }

    @Override
    public void execute() {
        GFossilIsland.currentState = Util.state("Going to bank");
        if (Constants.TREE_AREA.contains(c.p().tile())) {
            GameObject hole = Objects.stream(10).type(GameObject.Type.INTERACTIVE).name("Hole").nearest().first();
            if (hole.valid() && hole.interact("Climb through")) {
                Condition.wait(() -> !Constants.TREE_AREA.contains(c.p().tile()), 150, 20);
            }
        }
        Movement.moveTo(Constants.BANK_AREA.getRandomTile());
    }
}
