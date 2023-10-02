package org.gavin101.priv.gsnapegrass.Tasks.Common;

import org.gavin101.priv.gsnapegrass.Constants;
import org.gavin101.priv.gsnapegrass.GSnapegrass;
import org.gavin101.priv.gsnapegrass.Task;
import org.gavin101.util.Util;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;

public class CloseBank extends Task {
    private final Constants c = new Constants();
    GSnapegrass main;

    public CloseBank(GSnapegrass main) {
        super();
        super.name = "CloseBank";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return Inventory.isEmpty() && Bank.opened();
    }

    @Override
    public void execute() {
        GSnapegrass.currentState = Util.state("Closing bank");
        if (Bank.close()) {
            Condition.wait(() -> !Bank.opened(), 100, 40);
        }
    }
}
