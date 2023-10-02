package org.gavin101.pub.gectofuntus.Tasks.Common;

import org.gavin101.pub.gectofuntus.Constants;
import org.gavin101.pub.gectofuntus.GEctofuntus;
import org.gavin101.pub.gectofuntus.Task;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;

import org.gavin101.util.Util;

public class CloseBank extends Task {
    private final Constants c = new Constants();
    GEctofuntus main;

    public CloseBank(GEctofuntus main) {
        super();
        super.name = "CloseBank";
        this.main = main;
    }
    @Override
    public boolean activate() {
        switch (GEctofuntus.currentTask) {
            case "CrushBones":
                return Bank.opened()
                        && Inventory.stream().name(GEctofuntus.boneType).count() == 13
                        && Inventory.stream().name("Pot").count() == 13
                        && Inventory.stream().name("Ectophial").count() == 1;
            case "OfferBones":
                return Bank.opened()
                        && Inventory.stream().name(GEctofuntus.bonemealType).count() == 13
                        && Inventory.stream().name("Bucket of slime").count() == 13
                        && Inventory.stream().name("Ectophial").count() == 1;
        }
        return false;
    }

    @Override
    public void execute() {
        GEctofuntus.currentState = Util.state("Closing bank");
        if (Bank.close()) {
            Condition.wait(() -> !Bank.opened(), 150, 20);
        }
    }
}
