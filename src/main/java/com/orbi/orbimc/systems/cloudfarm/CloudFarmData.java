package com.orbi.orbimc.systems.cloudfarm;

import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.database.MongoBase;
import com.orbi.orbimc.util.TimeController;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class CloudFarmData {
    public static double calculateProduce(String id) {
        Map<String, Integer> dataMap = getPlayerData(id);

        double produce;
        int passedTime = TimeController.calculateTimeSpacing(dataMap.get(CloudFarmDataNames.lastCheck));
        int workerPerCrop = dataMap.get(CloudFarmDataNames.workerAmount) * (Repo.getConfig("cfs-base-crop-per-worker") + dataMap.get(CloudFarmDataNames.workerEfficiency));
        int dirtAmount = dataMap.get(CloudFarmDataNames.dirtAmount) <= workerPerCrop ? dataMap.get(CloudFarmDataNames.dirtAmount) : workerPerCrop;
        double dirtEfficiency = dataMap.get(CloudFarmDataNames.dirtEfficiency) * Repo.getConfig("dirt-upgrade-multiplier");
        produce = (dirtAmount * Repo.getConfig("cfs-base-carbon-per-crop")) * (passedTime / (Repo.getConfig("cfs-base-produce-time") - dirtEfficiency));

        if (produce > dataMap.get(CloudFarmDataNames.storageLimit))
            produce = dataMap.get(CloudFarmDataNames.storageLimit);

        return produce;
    }

    public static long calculatePrice(String item, int multiplier) {
        return (long) Repo.getConfig(item) * multiplier;
    }

    public static Map<String, Integer> getPlayerData(String playerID) {
        return (Map<String, Integer>) MongoBase.getValue(playerID, "cloudFarmSystem");
    }

    public static void takeProducedCarbon(Player player) {
        double producedCarbon = CloudFarmData.calculateProduce(player.getUniqueId().toString());
        if (producedCarbon >= 1) {
            Map<String, Integer> playerData = getPlayerData(player.getUniqueId().toString());
            playerData.put("lastCheck", TimeController.dateToTimeValue());
            //CarbonData.increaseCarbon(player, Math.round(producedCarbon));
            MongoBase.setValue(player, "cloudFarmSystem", playerData);
            player.sendMessage(String.format(Repo.getMSG("cfs-collect-success"), Math.round(producedCarbon)));
        } else
            player.sendMessage(Repo.getMSG("cfs-collect-error"));
        player.closeInventory();
    }

    public static Map<String, Integer> cratePlayerData() {
        Map<String, Integer> dataMap = new HashMap<>();
        dataMap.put(CloudFarmDataNames.dirtAmount, Repo.getConfig("cfs-starter-dirt-amount"));
        dataMap.put(CloudFarmDataNames.workerAmount, Repo.getConfig("cfs-starter-employee-amount"));
        dataMap.put(CloudFarmDataNames.dirtLimit, Repo.getConfig("cfs-starter-max-dirt-amount"));
        dataMap.put(CloudFarmDataNames.workerLimit, Repo.getConfig("cfs-starter-max-employee-amount"));
        dataMap.put(CloudFarmDataNames.storageLimit, Repo.getConfig("cfs-starter-max-storage"));
        dataMap.put(CloudFarmDataNames.dirtEfficiency, Repo.getConfig("cfs-starter-dirt-efficiency"));
        dataMap.put(CloudFarmDataNames.workerEfficiency, Repo.getConfig("cfs-starter-employee-efficiency"));
        dataMap.put(CloudFarmDataNames.lastCheck, TimeController.dateToTimeValue());
        return dataMap;
    }


}
