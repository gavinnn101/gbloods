package org.gavin101.priv.gbloods.Tasks.CraftBloods;

import org.gavin101.priv.gbloods.GBloods;
import org.gavin101.priv.gbloods.Task;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;

import org.gavin101.util.Util;

public class ActivateBloodEssence extends Task {
    GBloods main;

    public ActivateBloodEssence(GBloods main) {
        super();
        super.name = "ActivateBloodEssence";
        this.main = main;
    }
    @Override
    public boolean activate() {
        return Inventory.stream().name("Blood essence").isNotEmpty()
                && Inventory.stream().name("Blood essence (active)").isEmpty()
                && !Inventory.isFull();
    }

    @Override
    public void execute() {
        GBloods.currentState = Util.state("Activating blood essence");
        Item bloodEssence = Inventory.stream().name("Blood essence").first();
        if (bloodEssence.valid() && bloodEssence.interact("Activate")) {
            GBloods.bloodEssenceUsed++;
            Condition.wait(() -> Inventory.stream().name("Blood essence (active)").isNotEmpty(), 50, 50);
        }
    }
}
