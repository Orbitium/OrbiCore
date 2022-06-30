package com.orbi.orbimc.systems.fly;

import com.orbi.orbimc.OrbiCore;
import com.orbi.orbimc.database.MongoBase;
import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.util.StringParser;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class FlyManager {

    public static Map<Player, Integer> flyingPlayers = new HashMap<>();
    private static final int flyTimeCost = Repo.getConfig("if-fly-time-cost");
    private static final String flyTimeMessage = Repo.getMSG("remainder-fly-time");
    private static int task;

    //Oyuncu ölünce flyı kapat
    public static void addPlayer(Player player) {
        if (player.getWorld().getName().equals("world")) {
            player.sendMessage(Repo.getMSG("fly-disabled-in-world"));
            return;
        }
        int availableFlyTime = FlyData.getPlayerData(player);
        if (availableFlyTime >= flyTimeCost) {
            flyingPlayers.put(player, availableFlyTime);
            player.sendMessage(Repo.getMSG("enabled-fly"));
            if (flyingPlayers.size() <= 1)
                task = initializeCounter();
            player.setAllowFlight(true);
            player.setFlying(true);
        } else {
            player.sendMessage(Repo.getMSG("fs-insufficient-fly-time"));
        }
        player.closeInventory();
    }

    public static void removePlayer(Player player, boolean isForced) {
        MongoBase.setValue(player, "availableFlyTime", flyingPlayers.get(player));
        flyingPlayers.remove(player);
        player.closeInventory();
        player.sendMessage(Repo.getMSG("disabled-fly-" + isForced));
        player.setAllowFlight(false);
        player.setFlying(false);
        if (flyingPlayers.size() == 0)
            stopCounter();
    }

    public static int initializeCounter() {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(OrbiCore.getInstance(), () -> {
            for (Map.Entry<Player, Integer> entry : flyingPlayers.entrySet()) {
                if (entry.getValue() < flyTimeCost)
                    removePlayer(entry.getKey(), true);
                else {
                    int flyTime = entry.getValue() - flyTimeCost;
                    flyingPlayers.put(entry.getKey(), flyTime);
                    String message = StringParser.parse(flyTimeMessage, flyTime);
                    entry.getKey().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                }
            }
        }, 20L, 20L);
    }

    public static void stopCounter() {
        Bukkit.getScheduler().cancelTask(task);
    }

}
