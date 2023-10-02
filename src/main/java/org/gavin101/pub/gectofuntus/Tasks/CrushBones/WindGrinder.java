package org.gavin101.pub.gectofuntus.Tasks.CrushBones;

import org.gavin101.pub.gectofuntus.Constants;
import org.gavin101.pub.gectofuntus.GEctofuntus;
import org.gavin101.pub.gectofuntus.Task;
import org.gavin101.util.Util;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Camera;
import org.powbot.api.rt4.GameObject;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Objects;

public class WindGrinder extends Task {
    private final Constants c = new Constants();
    GEctofuntus main;

    public WindGrinder(GEctofuntus main) {
        super();
        super.name = "WindGrinder";
        this.main = main;
    }
    @Override
    public boolean activate() {
        return GEctofuntus.needToWindGrinder && Constants.ALTAR_TOP_FLOOR.contains(c.p().tile());
    }

    @Override
    public void execute() {
        GEctofuntus.currentState = Util.state("Winding grinder");
        GameObject boneGrinder = Objects.stream(10).type(GameObject.Type.INTERACTIVE).name("Bone grinder").nearest().first();
        if (boneGrinder.valid()) {
            if (boneGrinder.inViewport() && boneGrinder.interact("Wind")) {
                GEctofuntus.needToWindGrinder = false;
                Condition.wait(() -> Inventory.stream().name(GEctofuntus.bonemealType).count() == 13, 150, 800);
            } else {
                System.out.println("Turning camera to boneGrinder");
                Camera.turnTo(boneGrinder);
            }
        }
    }
}
