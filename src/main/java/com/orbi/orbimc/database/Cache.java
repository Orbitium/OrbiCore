package com.orbi.orbimc.database;

import com.mongodb.client.FindIterable;
import com.orbi.orbimc.OrbiCore;
import com.orbi.orbimc.listeners.loader.root.Loader;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cache {

    private static final Map<String, Document> playerDataCache = new HashMap<>();

    private static Object get(String playerName, String dataName) {
        return playerDataCache.get(playerName).get(dataName);
    }

    public static String getAsString(String playerName, String value) {
        return (String) playerDataCache.get(playerName).get(value);
    }

    public static int getAsInt(String playerName, String dataName) {
        return (int) playerDataCache.get(playerName).get(dataName);
    }

    public static long getAsLong(String playerName, String dataName) {
        return (long) playerDataCache.get(playerName).get(dataName);
    }

    public static double getAsDouble(String playerName, String dataName) {
        return (double) playerDataCache.get(playerName).get(dataName);
    }

    public static int getAsInt(Player player, String dataName) {
        return (int) playerDataCache.get(player.getName()).get(dataName);
    }

    public static long getAsLong(Player player, String dataName) {
        return (long) playerDataCache.get(player.getName()).get(dataName);
    }

    public static double getAsDouble(Player player, String dataName) {
        return (double) playerDataCache.get(player.getName()).get(dataName);
    }

    public static void update(String playerName, String dataName, Object newData) {
        MongoBase.setValue(playerName, dataName, newData);
        playerDataCache.get(playerName).put(dataName, newData);
        invoke(dataName, playerName);
    }

    public static void increaseIntValue(String playerName, String dataName, int amount) {
        playerDataCache.get(playerName).put(dataName, (int) playerDataCache.get(playerName).get(dataName) + amount);
        MongoBase.setValue(playerName, dataName, playerDataCache.get(playerName).get(dataName));
        invoke(dataName, playerName);
    }

    public static void increaseLongValue(String playerName, String dataName, long amount) {
        playerDataCache.get(playerName).put(dataName, (long) playerDataCache.get(playerName).get(dataName) + amount);
        MongoBase.setValue(playerName, dataName, playerDataCache.get(playerName).get(dataName));
        invoke(dataName, playerName);
    }

    public static void decreaseIntValue(String playerName, String dataName, int amount) {
        playerDataCache.get(playerName).put(dataName, (int) playerDataCache.get(playerName).get(dataName) - amount);
        MongoBase.setValue(playerName, dataName, playerDataCache.get(playerName).get(dataName));
        invoke(dataName, playerName);

    }

    public static void decreaseLongValue(String playerName, String dataName, long amount) {
        playerDataCache.get(playerName).put(dataName, (long) playerDataCache.get(playerName).get(dataName) + amount);
        MongoBase.setValue(playerName, dataName, playerDataCache.get(playerName).get(dataName));
        invoke(playerName, dataName);
    }

    public static void increaseIntValue(Player player, String dataName, int amount) {
        playerDataCache.get(player.getName()).put(dataName, (int) playerDataCache.get(player.getName()).get(dataName) + amount);
        MongoBase.setValue(player.getName(), dataName, playerDataCache.get(player.getName()).get(dataName));
        invoke(dataName, player.getName());
    }

    public static void increaseLongValue(Player player, String dataName, long amount) {
        playerDataCache.get(player.getName()).put(dataName, (long) playerDataCache.get(player.getName()).get(dataName) + amount);
        MongoBase.setValue(player.getName(), dataName, playerDataCache.get(player.getName()).get(dataName));
        invoke(dataName, player.getName());
    }

    public static void decreaseIntValue(Player player, String dataName, int amount) {
        playerDataCache.get(player.getName()).put(dataName, (int) playerDataCache.get(player.getName()).get(dataName) - amount);
        MongoBase.setValue(player.getName(), dataName, playerDataCache.get(player.getName()).get(dataName));
        invoke(dataName, player.getName());

    }

    public static void decreaseLongValue(Player player, String dataName, long amount) {
        playerDataCache.get(player.getName()).put(dataName, (long) playerDataCache.get(player.getName()).get(dataName) - amount);
        MongoBase.setValue(player.getName(), dataName, playerDataCache.get(player.getName()).get(dataName));
        invoke(player.getName(), dataName);
    }

    public static void increaseDoubleValue(Player player, String dataName, double amount) {
        playerDataCache.get(player.getName()).put(dataName, (double) playerDataCache.get(player.getName()).get(dataName) + amount);
        MongoBase.setValue(player.getName(), dataName, playerDataCache.get(player.getName()).get(dataName));
        invoke(dataName, player.getName());
    }

    public static void decreaseDoubleValue(Player player, String dataName, double amount) {
        playerDataCache.get(player.getName()).put(dataName, (int) playerDataCache.get(player.getName()).get(dataName) - amount);
        MongoBase.setValue(player.getName(), dataName, playerDataCache.get(player.getName()).get(dataName));
        invoke(dataName, player.getName());

    }

    public static void invoke(String dataName, String playerName) {
        if (MongoBase.updateTriggers.get(dataName) != null) {
            MongoBase.updateTriggers.get(dataName).accept(Bukkit.getPlayer(playerName));
        }
    }

    public static void registerPlayerData(String playerName) {
        FindIterable<Document> playerData = MongoBase.getPlayerData(playerName);
        Document data = playerData.first();
        for (Map.Entry<String, Object> entry : MongoBase.dataList.entrySet()) {
            if (data.get(entry.getKey()) == null) {
                data.put(entry.getKey(), entry.getValue());
            } else if (data.get(entry.getKey()) instanceof Number) {
                Number n = (Number) data.get(entry.getKey());
                if (n.doubleValue() < ((Number) entry.getValue()).doubleValue())
                    data.replace(entry.getKey(), entry.getValue());
            }
        }

        MongoBase.collection.replaceOne(new Document().append("_id", playerName), data);
        playerDataCache.put(playerName, data);
    }
}
