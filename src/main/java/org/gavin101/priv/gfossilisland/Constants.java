package org.gavin101.priv.gfossilisland;

import org.powbot.api.Area;
import org.powbot.api.Tile;
import org.powbot.api.rt4.Player;
import org.powbot.api.rt4.Players;

import java.util.ArrayList;

public class Constants {

    public Constants() {
        super();
    }

    public ArrayList<String> userTaskList = new ArrayList<String>();

    public Player p() {
        return Players.local();
    }

    public static final Area BANK_AREA = new Area(new Tile(3739, 3805, 0), new Tile(3742, 3803, 0));

    public static final int UNNOTED_GIANT_SEAWEED_ID = 21504;
    public static final int NOTED_GIANT_SEAWEED_ID = 21505;

    public static final Area TREE_AREA = new Area(new Tile(3700, 3840, 0), new Tile(3717, 3829, 0));
    public static final int CUTTING_ANIM = 867;
    public static final String[] AXE_LIST = {"Bronze axe", "Iron axe", "Steel axe", "Black axe", "Mithril axe",
                                             "Adamant axe", "Rune axe", "Dragon axe", "Crystal axe"};
    public static final int TEAK_LOG_ID = 6333;

    public static final Area OUTSIDE_HOLE_BANK_SIDE = new Area(new Tile(3714, 3814, 0), new Tile(3718, 3811, 0)); // This won't be needed when the walker can use the agility shortcut
}