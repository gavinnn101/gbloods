package org.gavin101.priv.gsnapegrass.Tasks.Common;

import org.gavin101.priv.gsnapegrass.Constants;
import org.gavin101.priv.gsnapegrass.GSnapegrass;
import org.gavin101.priv.gsnapegrass.Task;
import org.gavin101.util.Util;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;

public class BankItems extends Task {
    private final Constants c = new Constants();
    GSnapegrass main;

    public BankItems(GSnapegrass main) {
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
        GSnapegrass.currentState = Util.state("banking items");
        if (Bank.depositInventory()) {
            Condition.wait(() -> !Inventory.isEmpty(), 50, 50);
        }
    }
}
