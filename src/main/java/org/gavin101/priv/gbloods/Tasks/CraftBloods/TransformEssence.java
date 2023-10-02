package org.gavin101.priv.gbloods.Tasks.CraftBloods;

import org.gavin101.priv.gbloods.Constants;
import org.gavin101.priv.gbloods.GBloods;
import org.gavin101.priv.gbloods.Task;
import org.gavin101.util.Util;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Camera;
import org.powbot.api.rt4.GameObject;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Objects;

public class TransformEssence extends Task {
    private final Constants c = new Constants();
    GBloods main;

    public TransformEssence(GBloods main) {
        super();
        super.name = "TransformEssence";
        this.main = main;
    }
    @Override
    public boolean activate() {
        return Inventory.isFull()
                && Inventory.stream().name("Dense essence block").isNotEmpty()
                && Constants.DARK_ALTAR_AREA.contains(c.p().tile());
    }

    @Override
    public void execute() {
        GBloods.currentState = Util.state("Transforming essence");
        GameObject altar = Objects.stream(10).type(GameObject.Type.INTERACTIVE).name("Dark Altar").nearest().first();
        if (altar.valid()) {
            if (altar.inViewport() && altar.interact("Venerate")) {
                Condition.wait(() -> Inventory.stream().name("Dense essence block").isEmpty(), 100, 50);
            } else {
                System.out.println("Turning camera to dark altar");
                Camera.turnTo(altar);
            }
        }
    }
}
