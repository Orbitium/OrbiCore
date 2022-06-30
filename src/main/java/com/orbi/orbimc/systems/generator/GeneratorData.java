package com.orbi.orbimc.systems.generator;

import com.orbi.orbimc.database.MongoBase;
import com.orbi.orbimc.database.Repo;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class GeneratorData {

    public static Map<String, Double> getPlayerData(Player player) {
        return (Map<String, Double>) MongoBase.getValue(player, "generatorSystem", createPlayerData());
    }

    public static Map<String, Double> createPlayerData() {
        Map<String, Double> dataMap = new HashMap<>();
        dataMap.put("stonePointMultiplierE", 1.0); //Taş kazanım çarpanı
        dataMap.put("energyCost", 5.0);
        dataMap.put("stonePointMultiplier", 0.05);
        dataMap.put("availableStonePoint", 0.0);
        dataMap.put("energyUsage", 0.0);
        return dataMap;
    }


    public static Map<String, Double> createLore(Map<String, Double> playerData) {
        Map<String, Double> loreMap = new HashMap<>();
        double stonePointMultiplierE = playerData.get("stonePointMultiplierE");
        double energyCost = playerData.get("energyCost");
        double stonePointMultiplier = playerData.get("stonePointMultiplier");

        loreMap.put("stonePointMultiplierE", stonePointMultiplierE);
        loreMap.put("energyCost", energyCost);
        loreMap.put("stonePointMultiplier", stonePointMultiplier);
        loreMap.put("v1", stonePointMultiplierE);
        loreMap.put("v2", stonePointMultiplierE + Repo.getConfig("gs-spe-upgrade-multiplier") * 0.1);
        loreMap.put("v3", stonePointMultiplierE + Repo.getConfig("gs-spe-upgrade-multiplier-cost") * stonePointMultiplierE);
        loreMap.put("v4", energyCost);
        loreMap.put("v5", energyCost - Repo.getConfig("gs-cost-upgrade-multiplier") * 0.1);
        loreMap.put("v6", energyCost + Repo.getConfig("gs-cost-upgrade-multiplier-cost"));
        loreMap.put("v7", stonePointMultiplier);
        loreMap.put("v8", stonePointMultiplier + Repo.getConfig("gs-sp-upgrade-multiplier") * 0.01);
        loreMap.put("v9", stonePointMultiplier + Repo.getConfig("gs-sp-upgrade-multiplier-cost") * stonePointMultiplier);

        return loreMap;
    }


}
