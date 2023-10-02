package org.gavin101.pub.gcastlewars;

import org.gavin101.pub.gcastlewars.Tasks.Common.BankItems.BankItems;
import org.gavin101.pub.gcastlewars.Tasks.Common.BankItems.CloseBank;
import org.gavin101.pub.gcastlewars.Tasks.Common.BankItems.GoToBank;
import org.gavin101.pub.gcastlewars.Tasks.Common.BankItems.OpenBank;
import org.gavin101.pub.gcastlewars.Tasks.Common.StayLoggedIn;
import org.gavin101.pub.gcastlewars.Tasks.PlayCastleWars.GetIntoGame.AcceptGameInvite;
import org.gavin101.pub.gcastlewars.Tasks.PlayCastleWars.GetIntoGame.GoToWaitingRoom;
import org.gavin101.pub.gcastlewars.Tasks.PlayCastleWars.GoToHidingSpot.GoBehindStairs;
import org.gavin101.pub.gcastlewars.Tasks.PlayCastleWars.GoToHidingSpot.GoUpstairs;
import org.gavin101.pub.gcastlewars.Tasks.PrepGame.UnequipForbiddenItems;
import org.gavin101.util.Util;
import com.google.common.eventbus.Subscribe;
import com.google.common.primitives.Ints;
import org.powbot.api.Condition;
import org.powbot.api.event.BreakEvent;
import org.powbot.api.rt4.*;
import org.powbot.api.script.AbstractScript;
import org.powbot.api.script.ScriptManifest;
import org.powbot.api.script.paint.Paint;
import org.powbot.api.script.paint.PaintBuilder;
import org.powbot.api.script.paint.TrackInventoryOption;
import org.powbot.mobile.script.ScriptManager;
import org.powbot.mobile.service.ScriptUploader;

import java.util.ArrayList;

@ScriptManifest(
        name = "GCastleWars",
        description = "Joins a castle wars game and AFKs for tickets.",
        version = "0.0.1",
        author = "Gavin101"
)

public class GCastleWars extends AbstractScript {
    private final Constants c = new Constants();
    // Script vars
    public static String currentTaskList = "null";
    public static String currentTask = "null";
    public static String currentState = "null";

    private ArrayList<Task> prepGame = new ArrayList<>();
    private ArrayList<Task> playCastleWars = new ArrayList<>();

    public static void main(String[] args) {
        new ScriptUploader().uploadAndStart("GCastleWars", "",
                "127.0.0.1:5585", true, true);
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
        if (!Inventory.stream().allMatch(i -> i.name().equals("Castle wars ticket"))
                ||(Equipment.itemAt(Equipment.Slot.CAPE).valid()
                || Equipment.itemAt(Equipment.Slot.HEAD).valid())
                && !Equipment.itemAt(Equipment.Slot.CAPE).name().equals("Hooded cloak")) {
            currentTaskList = "prepGame";
            return prepGame;
        } else {
            currentTaskList = "playCastleWars";
            return playCastleWars;
        }
    }

    public void onStart() {
        Paint paint = new PaintBuilder()
                .addString(() -> "Current Task List: " +currentTaskList)
                .addString(() -> "Current Task: "       +currentTask)
                .addString(() -> "Current State: "      +currentState)
                .trackInventoryItem(
                        Constants.CASTLE_WARS_TICKET_ID,
                        "Castle wars ticket",
                        TrackInventoryOption.QuantityChange)
                .build();
        addPaint(paint);

        currentState = Util.state("Starting Scripts.Public.GCastleWars - Gavin101...");
        Condition.wait(() -> c.p().valid(), 200, 50);
        currentState = Util.state("Checking Camera");
        Util.cameraCheck();

        currentState = Util.state("Checking that we're on a castle wars world");
        if (!onCastleWarsWorld(Constants.CASTLE_WARS_WORLDS)) {
            System.out.println(Worlds.current().getNumber() + " is not a Castle Wars world. Please hop and rerun.");
            ScriptManager.INSTANCE.stop();
        }

        // Build task lists
        prepGame.add(new UnequipForbiddenItems(this));
        prepGame.add(new GoToBank(this));
        prepGame.add(new OpenBank(this));
        prepGame.add(new BankItems(this));
        prepGame.add(new CloseBank(this));

        playCastleWars.add(new GoToWaitingRoom(this));
        playCastleWars.add(new AcceptGameInvite(this));
        playCastleWars.add(new GoUpstairs(this));
        playCastleWars.add(new GoBehindStairs(this));
        playCastleWars.add(new StayLoggedIn(this));
    }

    public boolean onCastleWarsWorld(final int[] castleWarsWorlds) {
        return Ints.contains(castleWarsWorlds, Worlds.current().getNumber());
    }

    @Subscribe
    public void onBreakEvent(BreakEvent e) {
        // Delay break if we're in the middle of a game
        if (Components.stream().anyMatch(i -> i.text().contains("= Saradomin"))) {
            System.out.println("Delaying break 30 seconds while we're in a game");
            e.delay(30_000);
        }
    }
}
