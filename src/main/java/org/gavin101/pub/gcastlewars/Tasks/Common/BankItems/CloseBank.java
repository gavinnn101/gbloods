package org.gavin101.pub.gcastlewars.Tasks.Common.BankItems;

import org.gavin101.pub.gcastlewars.Constants;
import org.gavin101.pub.gcastlewars.GCastleWars;
import org.gavin101.pub.gcastlewars.Task;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;

import org.gavin101.util.Util;

public class CloseBank extends Task {
    private final Constants c = new Constants();
    GCastleWars main;

    public CloseBank(GCastleWars main) {
        super();
        super.name = "CloseBank";
        this.main = main;
    }
    @Override
    public boolean activate() {
        return Bank.opened() && Inventory.occupiedSlotCount() <= 1;
    }

    @Override
    public void execute() {
        GCastleWars.currentState = Util.state("Closing bank");
        if (Bank.close()) {
            Condition.wait(() -> !Bank.opened(), 50, 50);
        }
    }
}
