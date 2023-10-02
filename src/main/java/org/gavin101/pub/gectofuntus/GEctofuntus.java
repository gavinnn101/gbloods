package org.gavin101.pub.gectofuntus;


import org.gavin101.pub.gectofuntus.Tasks.BuySlime.*;
import org.gavin101.pub.gectofuntus.Tasks.Common.*;
import org.gavin101.pub.gectofuntus.Tasks.CrushBones.*;
import org.gavin101.pub.gectofuntus.Tasks.OfferBones.CollectTokens;
import org.gavin101.pub.gectofuntus.Tasks.OfferBones.OfferBones;
import org.gavin101.util.Util;
import com.google.common.eventbus.Subscribe;
import org.powbot.api.Condition;
import org.powbot.api.event.InventoryChangeEvent;
import org.powbot.api.event.MessageEvent;
import org.powbot.api.event.SkillExpGainedEvent;
import org.powbot.api.rt4.Players;
import org.powbot.api.rt4.walking.model.Skill;
import org.powbot.api.script.AbstractScript;
import org.powbot.api.script.OptionType;
import org.powbot.api.script.ScriptConfiguration;
import org.powbot.api.script.ScriptManifest;
import org.powbot.api.script.paint.Paint;
import org.powbot.api.script.paint.PaintBuilder;
import org.powbot.mobile.service.ScriptUploader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@ScriptManifest(
        name = "GEctofuntus",
        description = "Purchases slime, crushes bones, and offers bones at Ectofuntus.",
        version = "0.0.1",
        author = "Gavin101"
)

@ScriptConfiguration.List(
        {
                @ScriptConfiguration(
                        name = "Buy slime",
                        description = "Buys slime from crewtraders in Port Phasmatys.",
                        optionType = OptionType.BOOLEAN,
                        defaultValue = "true"
                ),
                @ScriptConfiguration(
                        name = "Slime amount",
                        description = "Amount of slime to buy",
                        optionType = OptionType.INTEGER,
                        defaultValue = "0"
                ),
                @ScriptConfiguration(
                        name = "Crush bones",
                        description = "Crushes bones upstairs at the ectofuntus for bonemeal.",
                        optionType = OptionType.BOOLEAN,
                        defaultValue = "true"
                ),
                @ScriptConfiguration(
                        name = "Bones",
                        description = "Type of bones to use",
                        optionType = OptionType.STRING,
                        defaultValue = "Dragon bones",
                        allowedValues = {"Big bones", "Dragon bones", "Lava dragon bones", "Wyvern bones", "Superior dragon bones"}
                ),
                @ScriptConfiguration(
                        name = "Offer bones",
                        description = "Offer bonemeal + slime to the altar.",
                        optionType = OptionType.BOOLEAN,
                        defaultValue = "true"
                )
        }
)

public class GEctofuntus extends AbstractScript {
    private ArrayList<Task> buySlimeTasks = new ArrayList<>();
    private ArrayList<Task> crushBonesTasks = new ArrayList<>();
    private ArrayList<Task> offerBonesTasks = new ArrayList<>();
    private ArrayList<Task> getItemCountTasks = new ArrayList<>();
    private final Constants c = new Constants();

    // Script vars
    public static String currentState = "null";
    public static String currentTask = "null";
    public static boolean needToHop = false;
    public static boolean needSlime;
    public static boolean needBonemeal;
    public static boolean needToOffer;
    public static String boneType;
    public static int slimeToBuy = -1;
    public static int slimeCounter = 0;
    public static int bonemealCounter = 0;
    public static int offerCounter = 0;
    public static long boneCount = -1;
    public static long slimeCount = -1;
    public static long bonemealCount = -1;
    public static String bonemealType;
    public static int potCount = -1;
    public static HashMap<String, String> boneToBonemeal = new HashMap<>();
    public static Map<String,Integer> requiredItems = new HashMap<String,Integer>();

    // bone grinder status vars
    public static boolean needToLoadBones;
    public static boolean needToWindGrinder;
    public static boolean needToCollectBones;
    public static boolean needToCollectTokens = false;


    // Script uploader
    public static void main(String[] args) {
        new ScriptUploader().uploadAndStart("GEctofuntus", "", "127.0.0.1:5565", true, true);
    }

    public ArrayList<Task> getTaskList() {
        if (slimeCount == -1) {
            currentTask = "GetItemCounts";
            return getItemCountTasks;
        } else if (needSlime) {
            requiredItems.clear();
            requiredItems.put("Coins", 10000);
            currentTask = "BuySlime";
            return buySlimeTasks;
        } else if (needBonemeal) {
            requiredItems.clear();
            requiredItems.put("Ectophial", 1);
            requiredItems.put(boneType, 13);
            requiredItems.put("Pot", 13);
            currentTask = "CrushBones";
            return crushBonesTasks;
        } else if (needToOffer) {
            requiredItems.clear();
            requiredItems.put("Ectophial", 1);
            requiredItems.put(bonemealType, 13);
            requiredItems.put("Bucket of slime", 13);
            currentTask = "OfferBones";
            return offerBonesTasks;
        }
        return null;
    }

    @Override
    public void poll() {
        for (Task task : getTaskList()) {
            if (task.activate()) {
                task.execute();
            }
        }
    }

    @Override
    public void onStart() {
        currentState = Util.state("Starting Scripts.Public.GEctofuntus - Gavin101...");
        Condition.wait(() -> Players.local().valid(), 500, 50);
        currentState = Util.state("Checking Camera");
        Util.cameraCheck();
        // Get script settings
        needSlime = getOption("Buy slime");
        slimeToBuy = getOption("Slime amount");
        needBonemeal = getOption("Crush bones");
        needToOffer = getOption("Offer bones");
        boneType = getOption("Bones");
        // Build task list
        getItemCountTasks.add(new GoToBank(this));
        getItemCountTasks.add(new GetItemCounts(this));

        if (needSlime) {
            buySlimeTasks.add(new GoToBank(this));
            buySlimeTasks.add(new OpenBank(this));
            buySlimeTasks.add(new BankItems(this));
            buySlimeTasks.add(new GoToCharter(this));
            buySlimeTasks.add(new TradeCharter(this));
            buySlimeTasks.add(new BuyItem(this));
            buySlimeTasks.add(new CloseStore(this));
            buySlimeTasks.add(new NeedToHop(this));
            buySlimeTasks.add(new HopWorlds(this));
        }
        if (needBonemeal) {
            crushBonesTasks.add(new GoToBank(this));
            crushBonesTasks.add(new OpenBank(this));
            crushBonesTasks.add(new BankItems(this));
            crushBonesTasks.add(new CloseBank(this));
            crushBonesTasks.add(new TeleportToAltar(this));
            crushBonesTasks.add(new GoToCrusher(this));
            crushBonesTasks.add(new GetGrinderStatus(this));
            crushBonesTasks.add(new LoadGrinder(this));
            crushBonesTasks.add(new WindGrinder(this));
            crushBonesTasks.add(new CollectBonemeal(this));
        }
        if (needToOffer) {
            offerBonesTasks.add(new GoToBank(this));
            offerBonesTasks.add(new OpenBank(this));
            offerBonesTasks.add(new BankItems(this));
            offerBonesTasks.add(new CloseBank(this));
            offerBonesTasks.add(new TeleportToAltar(this));
            offerBonesTasks.add(new CollectTokens(this));
            offerBonesTasks.add(new OfferBones(this));
        }
        createBonemealMap(boneToBonemeal);
        bonemealType = getBonemealType();
        if (Objects.equals(bonemealType, "")) {
            Util.endScript("Couldn't find a valid bonemeal mapping.");
        }

        Paint paint = new PaintBuilder()
                .addString(() -> "Current State: "      +currentState)
                .trackSkill(Skill.Prayer)
                .addString(() -> "Slime bought: "       +slimeCounter)
                .addString(() -> "Bones crushed: "      +bonemealCounter)
                .addString(() -> "Bonemeal offered: "   +offerCounter)
                .build();
        addPaint(paint);
    }

    public static void createBonemealMap(HashMap<String, String> bonemealMap) {
        // "Big bones", "Dragon bones", "Lava dragon bones", "Wyvern bones", "Superior dragon bones"
        bonemealMap.put("Big bones", "Big bonemeal");
        bonemealMap.put("Dragon bones", "Dragon bonemeal");
        bonemealMap.put("Lava dragon bones", "Lava dragon bonemeal");
        bonemealMap.put("Wyvern bones", "Wyvern bonemeal");
        bonemealMap.put("Superior dragon bones", "Superior dragon bonemeal");
    }

    public static String getBonemealType() {
        for (HashMap.Entry<String,String> boneMapping : boneToBonemeal.entrySet()) {
            if (Objects.equals(boneMapping.getKey(), boneType)) {
                return boneMapping.getValue();
            }
        }
        return "";
    }

    @Subscribe
    public void onMessage(MessageEvent e) {
        String text = e.getMessage();
        if (text.contains("The grinder is empty")) {
            needToLoadBones = true;
        } else if (text.contains("There are already")|| text.contains("There are some crushed")) {
            needToCollectBones = true;
        } else if (text.contains("in the hopper")) {
            needToWindGrinder = true;
        }
    }

    @Subscribe
    public void onInventoryChange(InventoryChangeEvent e) {
        if (Objects.equals(e.getItemName(), bonemealType)) {
            if (Constants.ALTAR_TOP_FLOOR.contains(c.p().tile())) {
                bonemealCounter++;
            }
        }
    }

    @Subscribe
    public void onSkillExpGainedEvent(SkillExpGainedEvent e) {
        if (e.getSkill() == Skill.Prayer) {
            offerCounter++;
        }
    }
}
