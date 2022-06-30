package com.orbi.orbimc.systems.energy;

import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.database.MongoBase;
import com.orbi.orbimc.systems.generator.GeneratorPlayerManager;
import com.orbi.orbimc.util.StringParser;
import com.orbi.orbimc.util.TimeController;
import org.bukkit.entity.Player;

import java.util.Map;

public class EnergyCalculation {

    public static void calculateProduction(Player player, Map<String, Long> playerData) {
        long production = playerData.get("burnedFuel") * (Repo.getConfig("ens-production-efficiency-multiplier") + playerData.get("productionEfficiency"));
        if (production > playerData.get("batteryCapacity")) {
            production = playerData.get("batteryCapacity");
            player.sendMessage(StringParser.parse(Repo.getMSG("ens-warn-produce"), production));
        } else
            player.sendMessage(StringParser.parse(Repo.getMSG("ens-success-produce"), production));
        playerData.put("timeValue", -1L);
        playerData.put("storedEnergy", playerData.get("storedEnergy") + production);
        playerData.put("burnedFuel", 0L);
        GeneratorPlayerManager.removePlayer(player);
        MongoBase.setValue(player, "energySystem", playerData);
    }

    public static void startProduction(Player player) {
        Map<String, Long> playerData = EnergyData.getPlayerData(player);
        if (playerData.get("storedFuel") <= 0) {
            player.sendMessage(Repo.getMSG("esn-insufficient-fuel"));
            player.closeInventory();
            return;
        }
        playerData.put("timeValue", playerData.get("storedFuel") * Repo.getConfig("ens-energy-wait-time-per-fuel") +
                TimeController.dateToTimeValue()); //Şu anki zamandan daha fazla olacak
        player.closeInventory();
        player.sendMessage(Repo.getMSG("esn-success-started"));
        playerData.put("burnedFuel", playerData.get("storedFuel"));
        playerData.put("storedFuel", 0L);
        MongoBase.setValue(player, "energySystem", playerData);
    }

    public static long calculateUpgradePrice(String priceType, String priceMultiplier, String playerMultiplierType, long playerLevel) {
        return Math.round((playerLevel / Repo.getConfig(playerMultiplierType) * (Repo.getConfig(priceType) * (Repo.getConfig(priceMultiplier) * 0.1))));
    }


}
