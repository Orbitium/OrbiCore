package com.orbi.orbimc.systems.playeritem;

import com.orbi.orbimc.database.MongoBase;
import com.orbi.orbimc.database.Repo;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerItemData {

    public static Map<String, Integer> blockLevel = new HashMap<>();

    public static void loadItemData() {
        blockLevel.put("DIRT", 1);
        blockLevel.put("COPPER_INGOT", 40);
        blockLevel.put("IRON_INGOT", 200);
        blockLevel.put("GOLD_INGOT", 250);
        blockLevel.put("DIAMOND", 1850);
        blockLevel.put("NETHERITE_INGOT", 5200);
    }

    public static Map<String, Integer> createPlayerData() {
        Map<String, Integer> playerData = new HashMap<>();
        playerData.put("level", 0);
        return playerData;
    }

    public static Map<String, Integer> getPlayerData(Player player) {
        return (Map<String, Integer>) MongoBase.getValue(player, "itemSystem");
    }

    public static void savePlayerItemByCarbon(Player player) {
        Map<String, Integer> playerData = getPlayerData(player);
        for (Map.Entry<String, Integer> e : playerData.entrySet()) {
            switch (e.getKey()) {
                case "woodAmount":
                case "stoneAmount":
                    //CarbonData.increaseCarbon(player, e.getValue() * 2L);
                    break;
                case "ironAmount":
                    //CarbonData.increaseCarbon(player, e.getValue() * 20000L);
                    break;
                case "goldAmount":
                    //CarbonData.increaseCarbon(player, e.getValue() * 4300L);
                    break;
            }
        }
        player.sendMessage(Repo.getMSG("is-saved-item"));
        MongoBase.setValue(player, "itemSystem", createPlayerData());
    }

    public static int calculateLevel(Map<String, Integer> playerData) {
        int level = 0;
        for (Map.Entry<String, Integer> item : playerData.entrySet()) {
            for (Map.Entry<String, Integer> itemLevel : blockLevel.entrySet()) {
                if (item.getKey().equals(itemLevel.getKey()))
                    level += item.getValue() * itemLevel.getValue();
            }
        }
        return (int) Math.round(level - level * (playerData.size() - 1 * 0.02));
    }
}
