package org.gavin101.pub.gectofuntus.Tasks.Common;

import org.gavin101.pub.gectofuntus.Constants;
import org.gavin101.pub.gectofuntus.GEctofuntus;
import org.gavin101.pub.gectofuntus.Task;
import org.gavin101.util.Util;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;

import java.util.Map;

public class BankItems extends Task {
    private final Constants c = new Constants();
    GEctofuntus main;

    public BankItems(GEctofuntus main) {
        super();
        super.name = "BankItems";
        this.main = main;
    }
    @Override
    public boolean activate() {
        return Bank.opened();
    }

    @Override
    public void execute() {
        GEctofuntus.currentState = Util.state("Banking items: "+GEctofuntus.currentTask);
        bankItems(GEctofuntus.requiredItems);
        switch (GEctofuntus.currentTask) {
            case "BuySlime":
                GEctofuntus.slimeCount = Bank.stream().name("Bucket of slime").first().stackSize() + Inventory.stream().name("Bucket of slime").count();
                if (GEctofuntus.slimeCount >= (GEctofuntus.boneCount + GEctofuntus.bonemealCount)) {
                    System.out.println("We have enough slime. Setting needSlime = false");
                    GEctofuntus.needSlime = false;
                }
                break;
            case "CrushBones":
                GEctofuntus.bonemealCount = Bank.stream().name(GEctofuntus.bonemealType).first().stackSize() + Inventory.stream().name(GEctofuntus.bonemealType).count();
                GEctofuntus.boneCount = Bank.stream().name(GEctofuntus.boneType).first().stackSize() + Inventory.stream().name(GEctofuntus.boneType).count();
                if (GEctofuntus.bonemealCount >= GEctofuntus.slimeCount || GEctofuntus.boneCount < 13) {
                    System.out.println("We have enough bonemeal. Setting needBonemeal = false");
                    GEctofuntus.needBonemeal = false;
                }
                break;
            case "OfferBones":
                GEctofuntus.slimeCount = Bank.stream().name("Bucket of slime").first().stackSize() + Inventory.stream().name("Bucket of slime").count();
                GEctofuntus.bonemealCount = Bank.stream().name(GEctofuntus.bonemealType).first().stackSize() + Inventory.stream().name(GEctofuntus.bonemealType).count();
                if (GEctofuntus.bonemealCount < 13) {
                    Util.endScript("Less than 13 bonemeal left. Ending script");
                } else if (GEctofuntus.slimeCount < 13) {
                    Util.endScript("Less than 13 buckets of slime left. Ending script");
            }
                break;
        }
    }

    public void bankItems(Map<String, Integer> requiredItems) {
        GEctofuntus.currentState = Util.state("Banking items");
        depositAnyExcept(requiredItems);
        GEctofuntus.currentState = Util.state("Withdrawing items");
        for (Map.Entry<String,Integer> entry : requiredItems.entrySet()) {
            String itemName = entry.getKey();
            int numOfItemRequired = entry.getValue();
            // Check if we need to withdraw the item
            long invyItemCount = Inventory.stream().name(itemName).count();
            long invyItemStackSize = Inventory.stream().name(itemName).first().stackSize();
            if (invyItemCount < numOfItemRequired && invyItemStackSize < numOfItemRequired) {
                // Withdraw needed item if we have enough in the bank.
                System.out.println(invyItemCount + "/" + invyItemStackSize + " " + itemName + " in inventory is less than required number: " +numOfItemRequired);
                int amountToWithdraw = (int) (numOfItemRequired - invyItemCount);
                if (Bank.stream().name(itemName).first().stackSize() >= amountToWithdraw && Bank.withdraw(itemName, amountToWithdraw)) {
                    Condition.wait(() -> Inventory.stream().name(itemName).count() == numOfItemRequired
                            ||  Inventory.stream().name(itemName).first().stackSize() == numOfItemRequired, 150, 20);
                }
            }
        }
    }

    public void depositAnyExcept(Map<String, Integer> requiredItems) {
        GEctofuntus.currentState = Util.state("Depositing items");
        for (Item item : Inventory.items()) {
            if (!requiredItems.containsKey(item.name()) && item.interact("Deposit-All")) {
                Condition.wait(() -> Inventory.stream().name(item.name()).isEmpty(), 150, 20);
            }
        }
    }
}
