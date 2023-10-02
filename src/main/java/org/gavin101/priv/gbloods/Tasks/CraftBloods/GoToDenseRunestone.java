package org.gavin101.priv.gbloods.Tasks.CraftBloods;

import org.gavin101.priv.gbloods.GBloods;
import org.gavin101.priv.gbloods.Task;
import org.gavin101.priv.gbloods.Constants;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Movement;
import org.gavin101.util.Util;
import org.powbot.api.rt4.Players;
import org.powbot.dax.api.DaxWalker;

public class GoToDenseRunestone extends Task {
    private final Constants c = new Constants();
    GBloods main;

    public GoToDenseRunestone(GBloods main) {
        super();
        super.name = "GoToDenseRunestone";
        this.main = main;
    }
    @Override
    public boolean activate() {
        if (c.p().movementAnimation() <= Constants.IDLE_MOVEMENT_ANIM) {
            if (Constants.BLOOD_ALTAR_AREA.contains(c.p().tile())) {
                return Inventory.stream().name("Dark essence fragments").isEmpty()
                        && Inventory.stream().name("Dark essence block").isEmpty();
            } else {
                return !Inventory.isFull()
                        && Inventory.stream().name("Dark essence block").isEmpty()
                        && !Constants.DENSE_RUNESTONE_AREA.contains(c.p().tile());
            }
        }
        return false;
    }

    @Override
    public void execute() {
        GBloods.currentState = Util.state("Going to dense runestone");
        Movement.moveTo(Constants.DENSE_RUNESTONE_AREA.getRandomTile());
    }
}
