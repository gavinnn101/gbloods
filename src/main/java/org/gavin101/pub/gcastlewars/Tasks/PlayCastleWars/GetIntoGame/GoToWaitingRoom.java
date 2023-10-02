package org.gavin101.pub.gcastlewars.Tasks.PlayCastleWars.GetIntoGame;

import org.gavin101.pub.gcastlewars.GCastleWars;
import org.gavin101.pub.gcastlewars.Task;
import org.gavin101.pub.gcastlewars.Constants;

import org.gavin101.util.Util;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

public class GoToWaitingRoom extends Task {
    private final Constants c = new Constants();
    GCastleWars main;

    public GoToWaitingRoom(GCastleWars main) {
        super();
        super.name = "GoToWaitingRoom";
        this.main = main;
    }
    @Override
    public boolean activate() {
        return c.p().movementAnimation() == c.p().idleAnimation()
                && !Components.stream().anyMatch(i -> i.text().contains("= Saradomin"))
                && !Components.stream().widget(Constants.TIME_UNTIL_START_WIDGET_PARENT).first().visible();
    }

    @Override
    public void execute() {
        GCastleWars.currentState = Util.state("Entering waiting room");
        GameObject portal = Objects.stream(20).type(GameObject.Type.INTERACTIVE).name("Guthix Portal").nearest().first();
        if (portal.valid()) {
            if (portal.inViewport() && portal.interact("Enter")) {
                Condition.wait(() -> Components.stream().widget(Constants.TIME_UNTIL_START_WIDGET_PARENT).first().visible(), 50, 20);
            } else {
                System.out.println("Moving to guthix portal");
                Movement.moveTo(portal);
            }
        } else {
            GCastleWars.currentState = Util.state("Going to castle wars");
            Movement.moveTo(Constants.CASTLE_WARS_OUTSIDE.getRandomTile());
        }
    }
}
