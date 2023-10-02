package org.gavin101.pub.gectofuntus.Tasks.CrushBones;

import org.gavin101.pub.gectofuntus.Constants;
import org.gavin101.pub.gectofuntus.GEctofuntus;
import org.gavin101.pub.gectofuntus.Task;
import org.gavin101.util.Util;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Camera;
import org.powbot.api.rt4.GameObject;
import org.powbot.api.rt4.Objects;

public class GetGrinderStatus extends Task {
    private final Constants c = new Constants();
    GEctofuntus main;

    public GetGrinderStatus(GEctofuntus main) {
        super();
        super.name = "GetGrinderStatus";
        this.main = main;
    }
    @Override
    public boolean activate() {
        return !GEctofuntus.needToCollectBones
                && !GEctofuntus.needToLoadBones
                && !GEctofuntus.needToWindGrinder
                && Constants.ALTAR_TOP_FLOOR.contains(c.p().tile());
    }

    @Override
    public void execute() {
        GEctofuntus.currentState = Util.state("Getting grinder status");
        GameObject boneGrinder = Objects.stream(10).type(GameObject.Type.INTERACTIVE).name("Bone grinder").nearest().first();
        if (boneGrinder.valid()) {
            if (boneGrinder.inViewport() && boneGrinder.interact("Status")) {
                // This will set a flag using onMessage listener in org.gavin101.pub.gectofuntus.java
                Condition.wait(() -> GEctofuntus.needToCollectBones || GEctofuntus.needToLoadBones || GEctofuntus.needToWindGrinder, 150, 30);
            } else {
                System.out.println("Turning camera to boneGrinder");
                Camera.turnTo(boneGrinder);
            }
        }
    }
}
