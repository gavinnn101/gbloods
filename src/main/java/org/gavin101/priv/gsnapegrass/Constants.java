package org.gavin101.priv.gsnapegrass;

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
    public static final int SNAPEGRASS_ID = 231;
    public static final Area SNAPEGRASS_AREA = new Area(new Tile(1832, 3647, 0), new Tile(1845, 3635, 0));
    public static final Area BANK_AREA = new Area(new Tile(1751, 3598, 0), new Tile(1749, 3600, 0));
}
