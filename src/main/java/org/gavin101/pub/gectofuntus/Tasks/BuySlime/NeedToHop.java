package org.gavin101.pub.gectofuntus.Tasks.BuySlime;

import org.gavin101.pub.gectofuntus.Constants;
import org.gavin101.pub.gectofuntus.GEctofuntus;
import org.gavin101.pub.gectofuntus.Task;
import org.powbot.api.rt4.Store;

public class NeedToHop extends Task {
    private final Constants c = new Constants();
    GEctofuntus main;

    public NeedToHop(GEctofuntus main) {
        super();
        super.name = "NeedToHop";
        this.main = main;
    }

    @Override
    public boolean activate() {
        if (Store.opened()) {
            int slimeInStore = Store.getItem(Constants.BUCKET_OF_SLIME_ID).itemStackSize();
            return slimeInStore == 0;
        }
        return false;
    }

    @Override
    public void execute() {
        System.out.println("Setting needToHop to true");
        GEctofuntus.needToHop = true;
    }
}
