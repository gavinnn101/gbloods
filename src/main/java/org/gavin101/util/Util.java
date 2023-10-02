package org.gavin101.util;

import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import org.powbot.mobile.rlib.generated.RItemDefinition;
import org.powbot.mobile.rscache.loader.ItemLoader;
import org.powbot.mobile.script.ScriptManager;


public class Util {

    public static int MAX_CAMERA_PITCH = 99;
    public static int MAX_CAMERA_ZOOM = 1;

    public static String state(String s) {
        System.out.println(s);
        return s;
    }

    public static void cameraCheck() {
        if (Camera.getZoom() > MAX_CAMERA_ZOOM) {
            state("Zooming camera out");
            if (Game.tab(Game.Tab.SETTINGS)) {
                Camera.moveZoomSlider(MAX_CAMERA_ZOOM);
                Condition.wait(() -> Camera.getZoom() == MAX_CAMERA_ZOOM, 100, 25);
            }
        }
        if (Camera.pitch() < 90) {
            state("Changing camera angle");
            Camera.pitch(true);
            Condition.wait(() -> Camera.pitch() == MAX_CAMERA_PITCH, 100, 25);
        }
    }

    public static void endScript(String exitMsg) {
        Util.state(exitMsg);
        if (Bank.opened()) {
            if (Bank.close()) {
                Condition.wait(() -> !Bank.opened(), 250, 50);
            }
        }
        if (Game.loggedIn()) {
            if (Game.logout()) {
                Condition.wait(() -> !Game.loggedIn(), 250, 50);
            }
        }
        ScriptManager.INSTANCE.stop();
    }

    public static String convertMsToMinutes(long milliseconds) {
        long totalSeconds = milliseconds / 1000;
        long seconds = totalSeconds % 60;
        long totalMinutes = totalSeconds / 60;
        long minutes = totalMinutes % 60;
        long totalHours = totalMinutes / 60;
        long hours = totalHours % 24;
        long days = totalHours / 24;

        // Return string formatted depending on if the value includes days, hours, etc.
        if (days > 0) {
            return String.format("%02d:%02d:%02d:%02d", days, hours, minutes, seconds);
        } else if (hours > 0) {
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format("%02d:%02d", minutes, seconds);
        }
    }

    public static boolean useEctophial() {
        Util.state("Using ectophial");
        if (Game.tab(Game.Tab.INVENTORY)) {
            Item ectophial = Inventory.stream().name("Ectophial").first();
            if (ectophial.valid()) {
                return ectophial.interact("Empty") && Condition.wait(() -> !ectophial.actions().contains("Empty"), 100, 40);
            } else {
                Util.endScript("No ectophial in inventory. Ending script");
                return false;
            }
        }
        return false;
    }

    public static boolean isItemStackable(String itemName) {
        // ItemLoader finds id `667` instead of `995` which I guess returns not stackable?
        if (itemName.equals("Coins")) {
            return true;
        }
        RItemDefinition itemDef = ItemLoader.lookup(itemName);
        if (itemDef != null) {
            boolean stackable = itemDef.stackable();
            System.out.println(itemName +" itemDef not null.");
            System.out.println(itemName +" stackable: " +stackable);
            System.out.println("itemDef ID: " +itemDef.id());
            return stackable;
        } else {
            // This probably isn't good practice but not sure what to do atm.
            System.out.println("Tried to check if " +itemName +" is stackable but returned null.");
            return false;
        }
    }

    public static long getCurrentInventoryCount(String itemName, boolean isStackable) {
        if (isStackable) {
            return Inventory.stream().name(itemName).first().stackSize();
        } else {
            return Inventory.stream().name(itemName).count();
        }
    }
}
