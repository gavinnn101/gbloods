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

public class CraftRunes extends Task {
    private final Constants c = new Constants();
    GBloods main;

    public CraftRunes(GBloods main) {
        super();
        super.name = "CraftRunes";
        this.main = main;
    }
    @Override
    public boolean activate() {
        return Inventory.stream().name("Dark essence fragments").isNotEmpty()
                && Constants.BLOOD_ALTAR_AREA.contains(c.p().tile());
    }

    @Override
    public void execute() {
        GBloods.currentState = Util.state("Crafting blood runes");
        GameObject altar = Objects.stream(10).type(GameObject.Type.INTERACTIVE).name("Blood Altar").nearest().first();
        if (altar.valid()) {
            if (altar.inViewport() && altar.interact("Bind")) {
                Condition.wait(() -> Inventory.stream().name("Dark essence fragments").isEmpty(), 50, 50);
            } else {
                System.out.println("Turning camera to blood altar");
                Camera.turnTo(altar);
            }
        }
    }
}
