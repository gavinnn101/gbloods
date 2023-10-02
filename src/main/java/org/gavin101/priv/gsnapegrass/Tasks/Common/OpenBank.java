package org.gavin101.priv.gsnapegrass.Tasks.Common;

import org.gavin101.priv.gsnapegrass.Constants;
import org.gavin101.priv.gsnapegrass.GSnapegrass;
import org.gavin101.priv.gsnapegrass.Task;
import org.gavin101.util.Util;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;

public class OpenBank extends Task {
    private final Constants c = new Constants();
    GSnapegrass main;

    public OpenBank(GSnapegrass main) {
        super();
        super.name = "OpenBank";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return Inventory.isFull()
                && !Bank.opened()
                && Bank.present()
                && Constants.BANK_AREA.contains(c.p().tile());
    }

    @Override
    public void execute() {
        GSnapegrass.currentState = Util.state("Opening bank");
        if (Bank.open()) {
            Condition.wait(Bank::opened, 100, 20);
        }
    }
}
