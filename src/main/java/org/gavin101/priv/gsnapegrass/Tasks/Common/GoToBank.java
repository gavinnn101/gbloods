package org.gavin101.priv.gsnapegrass.Tasks.Common;

import org.gavin101.priv.gsnapegrass.GSnapegrass;
import org.gavin101.util.Util;
import org.gavin101.priv.gsnapegrass.Constants;
import org.gavin101.priv.gsnapegrass.Task;
import org.powbot.api.rt4.*;

public class GoToBank extends Task {
    private final Constants c = new Constants();
    GSnapegrass main;

    public GoToBank(GSnapegrass main) {
        super();
        super.name = "GoToBank";
        this.main = main;
    }
    @Override
    public boolean activate() {
        return Inventory.isFull()
                && c.p().animation() == -1
                && !Bank.opened()
                && !Constants.BANK_AREA.contains(c.p().tile());
    }

    @Override
    public void execute() {
        GSnapegrass.currentState = Util.state("Going to bank");
        Movement.moveTo(Constants.BANK_AREA.getRandomTile());
    }
}
