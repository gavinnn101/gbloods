package org.gavin101.priv.gbloods.Tasks.Common;

import org.gavin101.priv.gbloods.Constants;
import org.gavin101.priv.gbloods.GBloods;
import org.gavin101.priv.gbloods.Task;
import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.rt4.Game;
import org.powbot.api.rt4.World;
import org.powbot.api.rt4.Worlds;

import org.gavin101.util.Util;
import org.powbot.mobile.script.ScriptManager;

public class HopWorlds extends Task {
    GBloods main;

    public HopWorlds(GBloods main) {
        super();
        super.name = "HopWorlds";
        this.main = main;
    }
    @Override
    public boolean activate() {
        return ScriptManager.INSTANCE.getRuntime(true) >= GBloods.nextHopTime;
    }

    @Override
    public void execute() {
        int randomWorld = Random.nextInt(0, Constants.WORLD_LIST.length-1);
        World hopWorld = Worlds.stream().id(Constants.WORLD_LIST[randomWorld]).first();
        GBloods.currentState = Util.state("Hopping to world: " +hopWorld.getNumber());

        GBloods.nextHopTime = GBloods.getNextHopTime();
        System.out.println("Next hop is at: " +GBloods.nextHopTime);
        GBloods.worldsHopped++;
        Game.tab(Game.Tab.LOGOUT);
        if (hopWorld.valid() && hopWorld.hop()) {
            Condition.wait(() -> Worlds.current().getNumber() == hopWorld.getNumber(), 100, 50);
            GBloods.worldsHopped++;
        }
    }
}
