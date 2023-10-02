package org.gavin101.pub.gcastlewars.Tasks.Common.BankItems;

import org.gavin101.pub.gcastlewars.Constants;
import org.gavin101.pub.gcastlewars.GCastleWars;
import org.gavin101.pub.gcastlewars.Task;
import org.powbot.api.rt4.GameObject;
import org.powbot.api.rt4.Inventory;
import org.gavin101.util.Util;
import org.powbot.api.rt4.Movement;
import org.powbot.api.rt4.Objects;

public class GoToBank extends Task {
    private final Constants c = new Constants();
    GCastleWars main;

    public GoToBank(GCastleWars main) {
        super();
        super.name = "GoToBank";
        this.main = main;
    }
    @Override
    public boolean activate() {
        return c.p().movementAnimation() == c.p().idleAnimation()
                && !Inventory.stream().allMatch(i -> i.name().equals("Castle wars ticket"))
                && !Objects.stream(20).type(GameObject.Type.INTERACTIVE).name("Bank chest").nearest().first().valid();
    }

    @Override
    public void execute() {
        GCastleWars.currentState = Util.state("Going to castle wars bank");
        Movement.moveTo(Constants.CASTLE_WARS_BANK);
    }
}
