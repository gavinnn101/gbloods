package org.gavin101.priv.gfossilisland.Tasks.FarmSeaweed;

import org.gavin101.util.Util;
import org.gavin101.priv.gfossilisland.Constants;
import org.gavin101.priv.gfossilisland.GFossilIsland;
import org.gavin101.priv.gfossilisland.Task;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

public class NoteSeaweed extends Task {
    private final Constants c = new Constants();
    GFossilIsland main;

    public NoteSeaweed(GFossilIsland main) {
        super();
        super.name = "NoteSeaweed";
        this.main = main;
    }
    @Override
    public boolean activate() {
        return Inventory.isFull() && Inventory.stream().id(Constants.UNNOTED_GIANT_SEAWEED_ID).isNotEmpty();
    }

    @Override
    public void execute() {
        GFossilIsland.currentState = Util.state("Noting seaweed");
        Game.tab(Game.Tab.INVENTORY);
        Item seaweed = Inventory.stream().id(Constants.UNNOTED_GIANT_SEAWEED_ID).first();
        if (seaweed.valid()) {
            if (Inventory.selectedItem().id() != Constants.UNNOTED_GIANT_SEAWEED_ID) {
                if (seaweed.interact("Use")) {
                    Condition.wait(() -> Inventory.selectedItem().id() == Constants.UNNOTED_GIANT_SEAWEED_ID, 150, 20);
                }
            } else {
                Npc leprechaun = Npcs.stream().name("Tool Leprechaun").nearest().first();
                if (leprechaun.valid()) {
                    if (leprechaun.inViewport() && leprechaun.interact("Use", false)) {
                        Condition.wait(() -> Inventory.stream().id(Constants.UNNOTED_GIANT_SEAWEED_ID).isEmpty(), 150, 20);
                    } else {
                        System.out.println("Turning camera to leprechaun");
                        Camera.turnTo(leprechaun);
                    }
                }
            }
        }
    }
}
