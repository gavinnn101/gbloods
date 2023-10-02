package org.gavin101.pub.gcastlewars.Tasks.PrepGame;

import org.gavin101.pub.gcastlewars.Constants;
import org.gavin101.pub.gcastlewars.GCastleWars;
import org.gavin101.pub.gcastlewars.Task;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Equipment;
import org.gavin101.util.Util;
import org.powbot.api.rt4.Game;
import org.powbot.api.rt4.Item;

public class UnequipForbiddenItems extends Task {
    private final Constants c = new Constants();
    GCastleWars main;

    public UnequipForbiddenItems(GCastleWars main) {
        super();
        super.name = "UnequipForbiddenItems";
        this.main = main;
    }
    @Override
    public boolean activate() {
        return !Equipment.itemAt(Equipment.Slot.CAPE).name().equals("Hooded cloak")
                && (Equipment.itemAt(Equipment.Slot.CAPE).valid() || Equipment.itemAt(Equipment.Slot.HEAD).valid());
    }

    @Override
    public void execute() {
        GCastleWars.currentState = Util.state("Unequipping head/back items");
        if (Game.tab(Game.Tab.EQUIPMENT)) {
            Item headSlot = Equipment.itemAt(Equipment.Slot.HEAD);
            if (headSlot.valid() && headSlot.interact("Remove")) {
                Condition.wait(() -> !headSlot.valid(), 50, 20);
            }
            Item capeSlot = Equipment.itemAt(Equipment.Slot.CAPE);
            if (capeSlot.valid() && capeSlot.interact("Remove")) {
                Condition.wait(() -> !capeSlot.valid(), 50, 20);
            }
        }
    }
}
