package com.orbi.orbimc.systems.energy;

import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.database.MongoBase;
import com.orbi.orbimc.database.data.Data;
import com.orbi.orbimc.database.data.DataContainer;
import com.orbi.orbimc.math.ConvertType;
import com.orbi.orbimc.math.Converter;
import com.orbi.orbimc.systems.energy.EnergyCalculation;
import com.orbi.orbimc.systems.generator.GeneratorPlayerData;
import com.orbi.orbimc.systems.generator.GeneratorPlayerManager;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class EnergyData implements DataContainer {

    @Override
    public void loadData() {
        new Data("batteryCapacity", (long) Repo.getConfig("esn-starter-battery-limit"));
        new Data("productionEfficiency", 1);
        new Data("fuelStorageLimit", (long) Repo.getConfig("esn-starter-coal-storage-limit"));
        new Data("storedFuel", 0);
        new Data("burnedFuel", 0);
        new Data("storedEnergy", 0L);
        new Data("timeValue", -1L);
    }

    public static Map<String, String> createUpgradeLoreData(Map<String, Long> playerData) {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("fuelLimit", playerData.get("fuelStorageLimit") + "");
        dataMap.put("fuelLimitUp", Converter.formatValue(playerData.get("fuelStorageLimit") + Repo.getConfig("esn-storage-limit-up-multiplier")));
        dataMap.put("fuelLimitPrice", EnergyCalculation.calculateUpgradePrice("ens-upgrade-fuel-limit-price", "ens-upgrade-fuel-price-multiplier", "esn-storage-limit-up-multiplier", playerData.get("fuelStorageLimit")) + "");

        dataMap.put("efficiency", Converter.formatValue(playerData.get("productionEfficiency"), ConvertType.ENERGY));
        dataMap.put("efficiencyUp", Converter.formatValue(playerData.get("productionEfficiency") + Repo.getConfig("esn-production-efficiency-up-multiplier"), ConvertType.ENERGY));
        dataMap.put("efficiencyPrice", EnergyCalculation.calculateUpgradePrice("ens-upgrade-efficiency-price", "ens-upgrade-efficiency-price-multiplier", "esn-production-efficiency-up-multiplier", playerData.get("productionEfficiency")) + "");


        dataMap.put("battery", Converter.formatValue(playerData.get("batteryCapacity"), ConvertType.ENERGY));
        dataMap.put("batteryUp", Converter.formatValue(playerData.get("batteryCapacity") + Repo.getConfig("esn-battery-limit-up-multiplier"), ConvertType.ENERGY));
        dataMap.put("batteryPrice", EnergyCalculation.calculateUpgradePrice("ens-upgrade-battery-limit-price", "ens-upgrade-battery-price-multiplier", "esn-battery-limit-up-multiplier", playerData.get("batteryCapacity")) + "");
        return dataMap;
    }

    public static Map<String, Long> getPlayerData(Player player) {
        if (GeneratorPlayerManager.cobbleData.containsKey(player)) {
            Map<String, Long> playerData = (Map<String, Long>) MongoBase.getValue(player, "energySystem");
            if (playerData.get("storedEnergy") > GeneratorPlayerManager.cobbleData.get(player).getAvailableEnergy()) {
                playerData.put("storedEnergy", GeneratorPlayerManager.cobbleData.get(player).getAvailableEnergy());
                MongoBase.setValue(player, "energySystem", playerData);
            }
            return playerData;
        }
        return (Map<String, Long>) MongoBase.getValue(player, "energySystem");
    }
}
