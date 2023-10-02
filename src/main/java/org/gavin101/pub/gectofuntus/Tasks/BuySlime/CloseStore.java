package org.gavin101.pub.gectofuntus.Tasks.BuySlime;

import org.gavin101.pub.gectofuntus.Constants;
import org.gavin101.pub.gectofuntus.GEctofuntus;
import org.gavin101.pub.gectofuntus.Task;
import org.gavin101.util.Util;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Store;

public class CloseStore extends Task {
    private final Constants c = new Constants();
    GEctofuntus main;

    public CloseStore(GEctofuntus main) {
        super();
        super.name = "CloseStore";
        this.main = main;
    }
    @Override
    public boolean activate() {
        return Store.opened() && GEctofuntus.needToHop;
    }

    @Override
    public void execute() {
        GEctofuntus.currentState = Util.state("Closing store");
        if (Store.close()) {
            Condition.wait(() -> !Store.opened(), 100, 20);
        }
    }
}
