package org.gavin101.pub.gcastlewars;

import org.powbot.api.Area;
import org.powbot.api.Tile;
import org.powbot.api.rt4.Player;
import org.powbot.api.rt4.Players;

import java.util.ArrayList;

public class Constants {

    public ArrayList<String> userTaskList = new ArrayList<String>();

    public Player p() {
        return Players.local();
    }

    public static final Tile CASTLE_WARS_BANK = new Tile(2443, 3083, 0);

    public static final int CASTLE_WARS_TICKET_ID = 4067;

    public static final int FREE_SPACE_WIDGET_ID = 219;

    public static int[] CASTLE_WARS_WORLDS = new int[] {383, 334, 354};

    public static Area CASTLE_WARS_OUTSIDE = new Area(
            new Tile(2436, 3097, 0),
            new Tile(2446, 3081, 0)
    );

    public static final int TIME_UNTIL_START_WIDGET_PARENT = 131;
    public static final int TIME_UNTIL_START_WIDGET_CHILD = 2;

    public static final Area ZAMORAK_BASE_MIDDLE = new Area(
            new Tile(2368, 3135, 1),
            new Tile(2379, 3124, 1)
    );
    public static final Area ZAMORAK_BASE_TOP = new Area(
            new Tile(2368, 3135, 2),
            new Tile(2379, 3124, 2)
    );
    public static final Area ZAMORAK_HIDING_SPOTS = new Area(
            new Tile(2368, 3135, 2),
            new Tile(2373, 3136, 2)
    );
    public static final Area SARA_BASE_MIDDLE  = new Area(
            new Tile(2420, 3083, 1),
            new Tile(2431, 3072, 1)
    );
    public static final Area SARA_BASE_TOP  = new Area(
            new Tile(2420, 3083, 2),
            new Tile(2431, 3072, 2)
    );
    public static final Area SARA_HIDING_SPOTS = new Area(
            new Tile(2426, 3072, 2),
            new Tile(2431, 3071, 2)
    );
}
