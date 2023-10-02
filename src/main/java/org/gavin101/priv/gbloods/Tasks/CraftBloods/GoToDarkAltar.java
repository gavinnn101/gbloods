package org.gavin101.priv.gbloods.Tasks.CraftBloods;

import org.gavin101.priv.gbloods.GBloods;
import org.gavin101.priv.gbloods.Task;
import org.gavin101.priv.gbloods.Constants;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

import org.gavin101.util.Util;
import org.powbot.dax.api.DaxWalker;

public class GoToDarkAltar extends Task {
    private final org.gavin101.priv.gbloods.Constants c = new org.gavin101.priv.gbloods.Constants();
    GBloods main;

    public GoToDarkAltar(GBloods main) {
        super();
        super.name = "GoToDarkAltar";
        this.main = main;
    }
    @Override
    public boolean activate() {
        return  c.p().movementAnimation() == org.gavin101.priv.gbloods.Constants.IDLE_MOVEMENT_ANIM
                && Inventory.isFull()
                && Inventory.stream().name("Dense essence block").isNotEmpty()
                && !org.gavin101.priv.gbloods.Constants.DARK_ALTAR_AREA.contains(c.p().tile());
    }

    @Override
    public void execute() {
        GBloods.currentState = Util.state("Going to Dark Altar");
        // Daxwalker.walkTo won't turn our run on
        if (Movement.energyLevel() > 20) {
            System.out.println("Turning running on");
            if (Movement.running(true)) {
                Condition.wait(Movement::running, 50, 50);
            }
        }
        // Movement.walkTo/moveTo don't seem consistent in using the agility shortcut.
        // Using DaxWalker/webwalker directly instead.
        DaxWalker.walkTo(Constants.DARK_ALTAR_AREA.getRandomTile());

//        if (Constants.DENSE_RUNESTONE_AREA.contains(c.p().tile())) {
//            GBloods.currentState = Util.state("Walking to agility shortcut");
//            Movement.moveTo(Constants.SHORTCUT_TILE);
//        } else if (c.p().tile().equals(Constants.SHORTCUT_TILE)) {
//            GBloods.currentState = Util.state("Using agility shortcut");
//            GameObject shortcut = Objects.stream(5).name("Rocks").nearest().first();
//            if (shortcut.valid()) {
//                if (shortcut.inViewport() && shortcut.interact("Climb")) {
//                    Condition.wait(() -> c.p().tile().equals(Constants.OVER_SHORTCUT_TILE), 100, 50);
//                } else {
//                    System.out.println("Turning camera to dark altar");
//                    Camera.turnTo(shortcut);
//                }
//            }
//        } else if (c.p().tile().equals(Constants.OVER_SHORTCUT_TILE)) {
//            Movement.moveTo(Constants.DARK_ALTAR_AREA.getRandomTile());
//        }
    }
}
