package org.gavin101.pub.gcastlewars.Tasks.Common;

import org.gavin101.pub.gcastlewars.Constants;
import org.gavin101.pub.gcastlewars.GCastleWars;
import org.gavin101.pub.gcastlewars.Task;
import org.powbot.api.Random;
import org.powbot.api.rt4.Chat;
import org.powbot.api.rt4.Component;
import org.powbot.api.rt4.Movement;
import org.powbot.api.rt4.Widgets;

import org.gavin101.util.Util;

import java.time.Instant;

public class StayLoggedIn extends Task {
    private final Constants c = new Constants();
    GCastleWars main;

    public static long sleepTime = 0;
    public static long endTime = 0;

    public StayLoggedIn(GCastleWars main) {
        super();
        super.name = "StayLoggedIn";
        this.main = main;
    }
    @Override
    public boolean activate() {
        if (Instant.now().getEpochSecond() >= endTime + sleepTime) {
            System.out.println("sleep time over");
            Component time_until_start = Widgets.widget(Constants.TIME_UNTIL_START_WIDGET_PARENT).component(Constants.TIME_UNTIL_START_WIDGET_CHILD);
            return  !Chat.chatting()
                    && (time_until_start.visible()
                    || Constants.SARA_HIDING_SPOTS.contains(c.p().tile())
                    || Constants.ZAMORAK_HIDING_SPOTS.contains(c.p().tile()));
        }
        return false;
    }

    @Override
    public void execute() {
        GCastleWars.currentState = Util.state("Clicking under character (Anti-afk)");
        if (Movement.step(c.p().tile())) {
            sleepTime = Random.nextInt(30, 180);
            endTime = Instant.now().getEpochSecond();
            System.out.println("Next anti-afk click: " +sleepTime + " seconds.");
        }
    }
}
