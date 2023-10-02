package org.gavin101.priv.gsnapegrass;

import org.gavin101.util.Util;
import org.powbot.api.Condition;
import org.powbot.api.script.AbstractScript;
import org.powbot.api.script.ScriptManifest;
import org.powbot.api.script.paint.Paint;
import org.powbot.api.script.paint.PaintBuilder;
import org.powbot.mobile.service.ScriptUploader;

import org.gavin101.priv.gsnapegrass.Tasks.Common.BankItems;
import org.gavin101.priv.gsnapegrass.Tasks.Common.CloseBank;
import org.gavin101.priv.gsnapegrass.Tasks.Common.GoToBank;
import org.gavin101.priv.gsnapegrass.Tasks.Common.OpenBank;

import org.gavin101.priv.gsnapegrass.Tasks.GatherSnapegrass;
import org.gavin101.priv.gsnapegrass.Tasks.GoToSnapegrass;

import java.util.ArrayList;

@ScriptManifest(
        name = "GSnapegrass",
        description = "Collects snapegrass on the Hosidious beach.",
        version = "0.0.1",
        author = "Gavin101"
)

public class GSnapegrass extends AbstractScript {
    private final Constants c = new Constants();
    // Script vars
    public static String currentState = "null";
    public static String currentTaskList = "null";
    public static String currentTask = "null";

    private ArrayList<Task> gatherSnapegrassTasks = new ArrayList<>();


    public static void main(String[] args) {
        new ScriptUploader().uploadAndStart("GSnapegrass", "",
                "127.0.0.1:5575", true, true);
    }

    @Override
    public void poll() {
        for (Task task : getTaskList()) {
            if (task.activate()) {
                currentTask = task.name;
                task.execute();
            }
        }
    }

    public ArrayList<Task> getTaskList() {
        return gatherSnapegrassTasks;
    }

    public void onStart() {
        currentState = Util.state("Starting GSnapegrass - Gavin101...");
        Condition.wait(() -> c.p().valid(), 200, 50);
        currentState = Util.state("Checking Camera");
        Util.cameraCheck();

        // Build task lists
        gatherSnapegrassTasks.add(new GoToSnapegrass(this));
        gatherSnapegrassTasks.add(new GatherSnapegrass(this));
        gatherSnapegrassTasks.add(new GoToBank(this));
        gatherSnapegrassTasks.add(new OpenBank(this));
        gatherSnapegrassTasks.add(new BankItems(this));
        gatherSnapegrassTasks.add(new CloseBank(this));

        Paint paint = new PaintBuilder()
                .addString(() -> "Current Task: "       +currentTask)
                .addString(() -> "Current State: "      +currentState)
                .trackInventoryItem(Constants.SNAPEGRASS_ID)
                .build();
        addPaint(paint);
    }
}
