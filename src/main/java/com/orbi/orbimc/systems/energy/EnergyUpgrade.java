package com.orbi.orbimc.systems.energy;

import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.database.MongoBase;
import org.bukkit.entity.Player;

import java.util.Map;

public class EnergyUpgrade {

    public static void upgrade(Player player, String priceType, String priceMultiplier, String playerMultiplierType, String dataType) {
        //Price type -> Ana fiyat, PriceMultiplier -> Fiyat çarpanı, playerMultiplierType -> Satın alım başına artış miktarı, mesela kömür deposu +50 gelişir.
        //Datatype kömür deposu gibi hangi data üzerinde işlem yaptırılacağını söyler
        Map<String, Long> energyData = EnergyData.getPlayerData(player);
        //long availableCarbon = CarbonData.getCarbon(player);
        long price = EnergyCalculation.calculateUpgradePrice(priceType, priceMultiplier, playerMultiplierType, energyData.get(dataType));
        /*if (availableCarbon < price) {
            player.sendMessage(Repo.getMSG("cs-insufficient-carbon"));
            player.closeInventory();
            return;
        }

         */
        energyData.put(dataType, energyData.get(dataType) + Repo.getConfig(playerMultiplierType));
        //CarbonData.decreaseCarbon(player, price);
        MongoBase.setValue(player, "energySystem", energyData);
        player.sendMessage(Repo.getMSG("esn-success-upgrade"));
        EnergyGUI.createPanel(player);
    }

    public static void upgradeFuelStorage(Player player) {
        upgrade(player, "ens-upgrade-fuel-limit-price", "ens-upgrade-fuel-price-multiplier", "esn-storage-limit-up-multiplier", "fuelStorageLimit");
    }
    public static void upgradeProductionEfficiency(Player player) {
        upgrade(player, "ens-upgrade-efficiency-price", "ens-upgrade-efficiency-price-multiplier", "esn-production-efficiency-up-multiplier", "productionEfficiency");
    }
    public static void upgradeBatteryCapacity(Player player) {
        upgrade(player, "ens-upgrade-battery-limit-price", "ens-upgrade-battery-price-multiplier", "esn-battery-limit-up-multiplier", "batteryCapacity");
    }
}
