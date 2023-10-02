package org.gavin101.pub.gectofuntus.Tasks.CrushBones;

import org.gavin101.pub.gectofuntus.Constants;
import org.gavin101.pub.gectofuntus.GEctofuntus;
import org.gavin101.pub.gectofuntus.Task;
import org.gavin101.util.Util;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

public class LoadGrinder extends Task {
    private final org.gavin101.pub.gectofuntus.Constants c = new org.gavin101.pub.gectofuntus.Constants();
    GEctofuntus main;

    public LoadGrinder(GEctofuntus main) {
        super();
        super.name = "LoadGrinder";
        this.main = main;
    }
    @Override
    public boolean activate() {
        return GEctofuntus.needToLoadBones
                && Constants.ALTAR_TOP_FLOOR.contains(c.p().tile())
                && Inventory.stream().name(GEctofuntus.boneType).isNotEmpty();
    }

    @Override
    public void execute() {
        GEctofuntus.currentState = Util.state("Loading bones");
        Game.tab(Game.Tab.INVENTORY);
        Item bones = Inventory.stream().name(GEctofuntus.boneType).first();
        if (bones.valid()) {
            if (!Inventory.selectedItem().name().equals(GEctofuntus.boneType)) {
                if (bones.interact("Use")) {
                    Condition.wait(() -> Inventory.selectedItem().name().equals(GEctofuntus.boneType), 150, 20);
                }
            } else if (Inventory.selectedItem().name().equals(GEctofuntus.boneType)) {
                GameObject loader = Objects.stream(10).type(GameObject.Type.INTERACTIVE).name("Loader").nearest().first();
                if (loader.valid()) {
                    if (loader.inViewport() && loader.interact("Use", false)) {
                        GEctofuntus.needToLoadBones = false;
                        Condition.wait(() -> Inventory.stream().name(GEctofuntus.bonemealType).count() == 13, 150, 800); // This conditional wait can cause a somewhat long afk sometimes. Should probably fix
                    } else {
                        System.out.println("Turning camera to loader");
                        Camera.turnTo(loader);
                    }
                }
            }
        }
    }
}
