package org.gavin101.priv.gbloods.Tasks.CraftBloods;

import org.gavin101.priv.gbloods.Constants;
import org.gavin101.priv.gbloods.GBloods;
import org.gavin101.priv.gbloods.Task;

import org.gavin101.util.Util;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Movement;

public class GoToBloodAltar extends Task {
    private final Constants c = new Constants();
    GBloods main;

    public GoToBloodAltar(GBloods main) {
        super();
        super.name = "GoToBloodAltar";
        this.main = main;
    }
    @Override
    public boolean activate() {
        return c.p().movementAnimation() <= Constants.IDLE_MOVEMENT_ANIM
                && Inventory.stream().name("Dark essence block").isNotEmpty()
                && Inventory.stream().name("Dark essence fragments").isNotEmpty()
                && !Constants.BLOOD_ALTAR_AREA.contains(c.p().tile());
    }

    @Override
    public void execute() {
        GBloods.currentState = Util.state("Going to blood altar");
        Movement.moveTo(Constants.BLOOD_ALTAR_AREA.getRandomTile());
    }
}
