package com.orbi.orbimc.systems.fly;

import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.database.MongoBase;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.HashMap;
import java.util.Map;

public class FlyData {

    public static int createPlayerData() {
        return Repo.getConfig("fs-starter-fly-time");
    }

    /*
    public static Map<String, Integer> createLoreData(Map<String, Integer> playerData) {
        Map<String, Integer> dataMap = new HashMap<>();
        dataMap.put("clockSpeedBefore", playerData.get("clockSpeed"));
        dataMap.put("clockSpeedAfter", playerData.get("clockSpeed") - Repo.getConfig("fs-clock-speed-buy-multiplier"));

        dataMap.put("productionBefore", playerData.get("production"));
        dataMap.put("productionAfter", playerData.get("production") + Repo.getConfig("fs-production-buy-multiplier"));

        dataMap.put("consumptionBefore", playerData.get("consumption"));
        dataMap.put("consumptionAfter", playerData.get("consumption") - Repo.getConfig("fs-consumption-buy-multiplier"));

        return dataMap;
    }

    public static int calculateUpgradeCost(String configMultiplier, int playerLevel, String playerMultiplier) {
        return (Repo.getConfig(configMultiplier) * playerLevel);
    }
    */
    public static Map<String, Integer> createPriceData() {
        int carbonPrice = Repo.getConfig("fs-buy-price-by-carbon");
        int rhodiumPrice = Repo.getConfig("fs-buy-price-by-rhodium");
        Map<String, Integer> dataMap = new HashMap<>();
        dataMap.put("v1", carbonPrice);
        dataMap.put("v2", carbonPrice * 10);
        dataMap.put("v3", carbonPrice * 100);
        dataMap.put("v4", rhodiumPrice);
        dataMap.put("v5", rhodiumPrice * 10);

        return dataMap;
    }

    public static int getMultiplier(ClickType clickType, String type) {
        switch (clickType) {
            case SHIFT_LEFT:
                return 10 * Repo.getConfig(type);
            case RIGHT:
                return 100 * Repo.getConfig(type);
        }
        return Repo.getConfig(type);
    }

    public static boolean checkMoney(Player player, int price, String moneyType) {
        long money = (long) MongoBase.getValue(player, moneyType);
        if (money < price || money < 0) {
            return false;
        } else {
            MongoBase.setValue(player, moneyType, money - price);
            return true;
        }
    }

    public static int getPlayerData(Player player) {
        return (int) MongoBase.getValue(player, "availableFlyTime");
    }

}
