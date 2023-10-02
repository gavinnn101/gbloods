package org.gavin101.priv.gsnapegrass.Tasks;

import org.gavin101.priv.gsnapegrass.GSnapegrass;
import org.gavin101.util.Util;
import org.gavin101.priv.gsnapegrass.Constants;
import org.gavin101.priv.gsnapegrass.Task;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Movement;

public class GoToSnapegrass extends Task {
    private final Constants c = new Constants();
    GSnapegrass main;

    public GoToSnapegrass(GSnapegrass main) {
        super();
        super.name = "GoToSnapegrass";
        this.main = main;
    }
    @Override
    public boolean activate() {
        return !Inventory.isFull() && !Constants.SNAPEGRASS_AREA.contains(c.p().tile());
    }

    @Override
    public void execute() {
        GSnapegrass.currentState = Util.state("Going to snapegrass area");
        Movement.moveTo(Constants.SNAPEGRASS_AREA.getRandomTile());
    }
}
