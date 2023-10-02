package org.gavin101.pub.gectofuntus;

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



    // Buy slime
    public static final Area CHARTER_CREW_AREA = new Area(new Tile(3696, 3506, 0), new Tile(3707, 3495, 0));
    public static final int BUCKET_OF_SLIME_ID = 4286;
    public static final int[] WORLD_LIST = {
            303, 304, 305, 306, 307, 309, 310, 311, 312, 313, 314, 315, 317, 320,
            322, 323, 324, 325, 327, 328, 329, 330, 331, 332, 333, 334, 336, 337,
            339, 340, 341, 342, 343, 344, 346, 347, 348, 350, 351, 352, 354, 355,
            357, 358, 359, 360, 362, 367, 368, 369, 370, 374, 375, 376, 377, 378,
            387, 388, 389, 395, 421, 422, 424, 443, 444, 445, 446, 463, 464, 465,
            477, 478, 480, 481, 482, 484, 485, 486, 487, 488, 489, 490, 491, 492,
            494, 495, 496, 505, 506, 507, 508, 509, 510, 511, 512, 513, 514, 515,
            517, 518, 519, 520, 521, 522, 523, 524, 525, 531, 532, 534, 535, 542,
            544, };
    // Crush Bones
    public static final Area LOADER_AREA = new Area(new Tile(3657, 3526, 1), new Tile(3661, 3524, 1));
    // Offer Bones
    public static final int altarFullDialogueID = 229;
    public static final int GHOST_DIALOGUE_ID = 217;
    public static final int GHOST_DIALOGUE_COMPONENT_ID = 6;
    // my stuff
    public static final Area BANK_AREA = new Area(new Tile(3686, 3467, 0), new Tile(3690, 3466, 0));
//    public static final Area PORT_PHASMATYS = new Area(new Tile(3659, 3507, 0), new Tile(3691, 3466, 0));
    public static final Area PORT_PHASMATYS = new Area(new Tile(3657, 3507, 0), new Tile(3711, 3460, 0));
    public static final Area ALTAR_BOT_FLOOR = new Area(new Tile(3655, 3524, 0), new Tile(3664, 3514, 0));
    public static final Area ALTAR_TOP_FLOOR = new Area(new Tile(3654, 3526, 1), new Tile(3667, 3521, 1));
    public static final Area BARRIER_ALTAR_SIDE = new Area(new Tile(3658, 3509, 0), new Tile(3660, 3511, 0));
    public static final Tile BARRIER_TILE_WEST = new Tile(3659, 3507, 0);
    public static final Tile BARRIER_TILE_EAST = new Tile(3660, 3507, 0);
    public static final Tile STUCK_BANK_TILE = new Tile(3688, 3471, 0);

    public static final int ECTO_TOKEN_ID = 4278;
}