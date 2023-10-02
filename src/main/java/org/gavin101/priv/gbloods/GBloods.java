package org.gavin101.priv.gbloods;

import org.gavin101.priv.gbloods.Tasks.Common.HopWorlds;
import org.gavin101.priv.gbloods.Tasks.CraftBloods.*;
import org.gavin101.util.Util;
import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.rt4.walking.model.Skill;
import org.powbot.api.script.AbstractScript;
import org.powbot.api.script.ScriptManifest;
import org.powbot.api.script.paint.Paint;
import org.powbot.api.script.paint.PaintBuilder;
import org.powbot.mobile.script.ScriptManager;
import org.powbot.mobile.service.ScriptUploader;

import java.util.ArrayList;

@ScriptManifest(
        name = "GBloods",
        description = "Crafts blood runes in arceuus.",
        version = "0.0.1",
        author = "Gavin101"
)

public class GBloods extends AbstractScript {

    private ArrayList<Task> craftBloodsTasks = new ArrayList<>();

    private final Constants c = new Constants();
    // Script vars
    public static String currentState = "null";
    public static String currentTask = "null";

    // World hop vars
    public static int worldsHopped = 0;
    public static int[] hopRange = new int[]{ 1_800_000, 2_700_000 }; // 30 - 45 minutes
//    public static int[] hopRange = new int[]{ 60_000, 120_000 }; // 1-2 minutes for testing

    public static long nextHopTime = 0;
    public static String nextHopTimeFormatted = "00:00";

    public static int bloodEssenceUsed = 0;

    // Script uploader
    public static void main(String[] args) {
        new ScriptUploader().uploadAndStart("GBloods", "",
                "emulator-5556", true, true);
    }
    @Override
    public void poll() {
        Util.cameraCheck();
        for (Task task : getTaskList()) {
            if (task.activate()) {
                currentTask = task.name;
                task.execute();
            }
        }
    }

    public ArrayList<Task> getTaskList() {
        return craftBloodsTasks;
    }

    public void onStart() {
        currentState = Util.state("Starting GBloods - Gavin101...");

        // Build task lists
        craftBloodsTasks.add(new ActivateBloodEssence(this));
        craftBloodsTasks.add(new GoToDenseRunestone(this));
        craftBloodsTasks.add(new MineRunestone(this));
        craftBloodsTasks.add(new GoToDarkAltar(this));
        craftBloodsTasks.add(new TransformEssence(this));
        craftBloodsTasks.add(new ChipEssence(this));
        craftBloodsTasks.add(new GoToBloodAltar(this));
        craftBloodsTasks.add(new CraftRunes(this));
        // Add common tasks
        craftBloodsTasks.add(new HopWorlds(this));


        Paint paint = new PaintBuilder()
                .addString(() -> "Current Task: "       +currentTask)
                .addString(() -> "Current State: "      +currentState)
                .trackSkill(Skill.Runecrafting)
                .trackInventoryItem(Constants.BLOOD_RUNE_ID)
                .addString(() -> "Blood essence used: " +bloodEssenceUsed)
                .addString(() -> "Next world hop: " +nextHopTimeFormatted)
                .addString(() -> "World hops: " +worldsHopped)
                .build();
        addPaint(paint);

        // Set next world hop
        nextHopTime = getNextHopTime();
    }

    public static long getNextHopTime() {
        long nextHopTime = ScriptManager.INSTANCE.getRuntime(true) + Random.nextInt(hopRange[0], hopRange[1]);
        System.out.println("Setting next hop time to: " + nextHopTime);
        // Set formatted hop time to use in paint
        nextHopTimeFormatted = Util.convertMsToMinutes(nextHopTime);
        return nextHopTime;
    }
}
