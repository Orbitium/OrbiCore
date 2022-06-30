package com.orbi.orbimc.systems.generator;

import com.orbi.orbimc.database.MongoBase;
import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.systems.energy.EnergyData;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class GeneratorPlayerData {

    private final Player player;
    private final double stonePointMultiplierE;
    public final double energyCost;
    private final double stonePointMultiplier;
    public double availableStonePoint;
    public long availableEnergy;

    public boolean isEnergyOn;

    public void chooseProduce() {
        if (isEnergyOn)
            generateWithE();
        else
            generateWithoutE();
    }

    private void generateWithoutE() {
        availableStonePoint += stonePointMultiplier;
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_GRAY + "[Jeneratör sistemi] ➤" + stonePointMultiplier + " taş puanı kazanıldı!"));
    }

    private void generateWithE() {
        if (availableEnergy < energyCost) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Repo.getMSG("gs-energy-end")));
            stopWithEGenerate();
            return;
        }
        availableEnergy -= energyCost;
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_GRAY + "[Jeneratör sistemi] ➤" + stonePointMultiplierE + " taş puanı kazanıldı! " + ChatColor.YELLOW + "Kalan enerji: " + availableEnergy));
        availableStonePoint += stonePointMultiplierE;
    }

    private void stopWithEGenerate() {
        isEnergyOn = false;
    }


    public long getAvailableEnergy() {
        return availableEnergy;
    }

    public void save() {
        Map<String, Double> playerData = new HashMap<>();
        playerData.put("stonePointMultiplierE", stonePointMultiplierE);
        playerData.put("energyCost", energyCost);
        playerData.put("stonePointMultiplier", stonePointMultiplier);
        playerData.put("availableStonePoint", availableStonePoint);
        playerData.put("energyUsage", isEnergyOn ? 1.0 : 0.0);
        MongoBase.setValue(player, "generatorSystem", playerData);
        EnergyData.getPlayerData(player);
    }

    public GeneratorPlayerData(Player player, long storedEnergy) {
        Map<String, Double> playerData = GeneratorData.getPlayerData(player);
        this.player = player;
        this.stonePointMultiplierE = playerData.get("stonePointMultiplierE");
        this.energyCost = playerData.get("energyCost");
        this.stonePointMultiplier = playerData.get("stonePointMultiplier");
        this.availableStonePoint = playerData.get("availableStonePoint");
        this.isEnergyOn = playerData.get("energyUsage") == 1.0;
        this.availableEnergy = storedEnergy;
    }
}
