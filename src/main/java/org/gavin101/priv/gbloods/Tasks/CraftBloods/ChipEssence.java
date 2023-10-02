package org.gavin101.priv.gbloods.Tasks.CraftBloods;

import org.gavin101.priv.gbloods.GBloods;
import org.gavin101.priv.gbloods.Task;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;

import org.gavin101.util.Util;


public class ChipEssence extends Task {
    GBloods main;

    public ChipEssence(GBloods main) {
        super();
        super.name = "ChipEssence";
        this.main = main;
    }
    @Override
    public boolean activate() {
        return Inventory.stream().name("Dark essence block").isNotEmpty()
                && (!Inventory.isFull() || Inventory.stream().name("Dark essence fragments").isEmpty());
    }

    @Override
    public void execute() {
        GBloods.currentState = Util.state("Chipping essence blocks");
        Item chisel = Inventory.stream().name("Chisel").first();
        while (Inventory.stream().name("Dark essence block").isNotEmpty()) {
            if (chisel.valid()) {
                if (!Inventory.selectedItem().name().equals("Chisel") && chisel.interact("Use")) {
                    Condition.wait(() -> Inventory.selectedItem().name().equals("Chisel"), 50, 50);
                } else {
                    Item essence = Inventory.stream().name("Dark essence block").first();
                    if (essence.valid() && essence.interact("Use")) {
                        Condition.wait(() -> !essence.valid(), 50, 50);
                    }
                }
            }
        }
    }
}
