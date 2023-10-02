package org.gavin101.priv.gfossilisland.Tasks.Common;

import org.gavin101.priv.gfossilisland.Constants;
import org.gavin101.priv.gfossilisland.GFossilIsland;
import org.gavin101.priv.gfossilisland.Task;
import org.gavin101.util.Util;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;

public class BankItems extends Task {
    private final Constants c = new Constants();
    GFossilIsland main;

    public BankItems(GFossilIsland main) {
        super();
        super.name = "BankItems";
        this.main = main;
    }
    @Override
    public boolean activate() {
        return Inventory.isFull() && Bank.opened();
    }

    @Override
    public void execute() {
        GFossilIsland.currentState = Util.state("Banking items");
        if (Bank.depositAllExcept(Constants.AXE_LIST)) {
            // This should probably check inventory for only items in axe list but cant think of the code rn
            Condition.wait(() -> Inventory.isEmpty()
                    || Inventory.stream().name("Teak logs").isEmpty(), 150, 20);
        }
    }
}
