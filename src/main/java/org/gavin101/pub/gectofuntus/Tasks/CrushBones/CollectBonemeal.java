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

public class CollectBonemeal extends Task {
    private final Constants c = new Constants();
    GEctofuntus main;

    public CollectBonemeal(GEctofuntus main) {
        super();
        super.name = "CollectBonemeal";
        this.main = main;
    }
    @Override
    public boolean activate() {
        return GEctofuntus.needToCollectBones;
    }

    @Override
    public void execute() {
        GEctofuntus.currentState = Util.state("Collecting bonemeal");
        GameObject bin = Objects.stream(10).type(GameObject.Type.INTERACTIVE).name("Bin").nearest().first();
        if (bin.valid()) {
            if (bin.inViewport() && bin.interact("Empty") && Condition.wait(() -> Inventory.stream().name(GEctofuntus.bonemealType).count() == 13, 150, 800)) { // This conditional wait feels weird. Should probably revisit.
                GEctofuntus.needToCollectBones = false;
            } else {
                System.out.println("Turning camera to bin");
                Camera.turnTo(bin);
            }
        }
    }
}
