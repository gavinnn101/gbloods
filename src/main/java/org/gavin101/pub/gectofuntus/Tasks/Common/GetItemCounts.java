package org.gavin101.pub.gectofuntus.Tasks.Common;

import org.gavin101.pub.gectofuntus.Constants;
import org.gavin101.pub.gectofuntus.GEctofuntus;
import org.gavin101.pub.gectofuntus.Task;
import org.gavin101.util.Util;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;

public class GetItemCounts extends Task {
    private final Constants c = new Constants();
    GEctofuntus main;

    public GetItemCounts(GEctofuntus main) {
        super();
        super.name = "GetItemCounts";
        this.main = main;
    }
    @Override
    public boolean activate() {
        return GEctofuntus.slimeCount == -1 && Constants.BANK_AREA.contains(c.p().tile());
    }

    @Override
    public void execute() {
        getItemCounts();
    }

    public static void getItemCounts() {
        GEctofuntus.currentState = Util.state("Getting item counts");
        if (Bank.present()) {
            if (Bank.opened()) {
                GEctofuntus.currentState = Util.state("Checking bank");
                GEctofuntus.boneCount = Bank.stream().name(GEctofuntus.boneType).first().stackSize();
                GEctofuntus.bonemealCount = Bank.stream().name(GEctofuntus.bonemealType).first().stackSize();
                GEctofuntus.slimeCount = Bank.stream().name("Bucket of slime").first().stackSize();
                GEctofuntus.potCount = Bank.stream().name("Pot").first().stackSize();
                System.out.println("Bone count: " +GEctofuntus.boneCount);
                System.out.println("Bonemeal count: " +GEctofuntus.bonemealCount);
                System.out.println("Slime count: " +GEctofuntus.slimeCount);
                System.out.println("Pot count: " +GEctofuntus.potCount);

                // for testing
//                org.gavin101.pub.gectofuntus.boneCount = 50;
//                org.gavin101.pub.gectofuntus.slimeCount = 298;

                // verify functions could probably be refactored into 1 later.
                GEctofuntus.needSlime = verifyNeedSlime();
                GEctofuntus.needBonemeal = verifyNeedBonemeal();
            } else if (Bank.open()) {
                Condition.wait(Bank::opened, 100, 20);
            }
        }
    }

    public static boolean verifyNeedSlime() {
        GEctofuntus.slimeCount = Bank.stream().name("Bucket of slime").first().stackSize() + Inventory.stream().name("Bucket of slime").count();
        if (GEctofuntus.slimeCount >= (GEctofuntus.boneCount + GEctofuntus.bonemealCount)) {
            System.out.println("We have enough slime. Setting needSlime = false");
            return false;
        } else {
            return true;
        }
    }

    public static boolean verifyNeedBonemeal() {
        GEctofuntus.bonemealCount = Bank.stream().name(GEctofuntus.bonemealType).first().stackSize() + Inventory.stream().name(GEctofuntus.bonemealType).count();
        if (GEctofuntus.bonemealCount >= GEctofuntus.slimeCount) {
            System.out.println("We have enough bonemeal. Setting needBonemeal = false");
            return false;
        } else {
            return true;
        }
    }
}
