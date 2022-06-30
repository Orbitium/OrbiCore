package com.orbi.orbimc.systems.cloudfarm;

import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.database.MongoBase;
import org.bukkit.entity.Player;

import java.util.Map;

public class CloudFarmUpgrades {

    private static void buy(Player player, String dataName, String priceNames, int upgradeAmount) {
        Map<String, Integer> dataMap = CloudFarmData.getPlayerData(player.getUniqueId().toString());
        //long availableCarbon = CarbonData.getCarbon(player);

        long price = CloudFarmData.calculatePrice(priceNames, dataMap.get(dataName));

        //if (availableCarbon < price) {
         //   sendErrorToPlayer(player, "cs-insufficient-carbon");
        //    return;
        //}

        dataMap.put(dataName, dataMap.get(dataName) + upgradeAmount);
        //CarbonData.decreaseCarbon(player, price);
        MongoBase.setValue(player, "cloudFarmSystem", dataMap);

        player.sendMessage(Repo.getMSG("cfs-success-buy"));
        CloudFarmGUI.updateSingle(player);
    }


    public static void upgradeDirtLimit(Player player) {
        buy(player, CloudFarmDataNames.dirtLimit, PriceList.dirtLimit, 1);
    }

    public static void upgradeWorkerLimit(Player player) {
        buy(player, CloudFarmDataNames.workerLimit, PriceList.workerLimit, 1);
    }

    public static void upgradeStorageLimit(Player player) {
        buy(player, CloudFarmDataNames.storageLimit, PriceList.storageLimit, 5);
    }

    public static void upgradeDirtEfficiency(Player player) {
        buy(player, CloudFarmDataNames.dirtEfficiency, PriceList.dirtEfficiency, 1);
    }

    public static void upgradeWorkerEfficiency(Player player) {
        buy(player, CloudFarmDataNames.workerEfficiency, PriceList.workerEfficiency, 1);
    }

    /*
    public static void upgradeDirtLimit(Player player) {
        Map<String, Integer> dataMap = CloudFarmData.getPlayerData(player.getUniqueId().toString());
        Double availableCarbon = (Double) MongoBase.getValue(player.getUniqueId().toString(), "availableCarbon");
        int price = CloudFarmData.calculatePrice("cfs-dirt-limit-upgrade-price", dataMap.get(DataNames.dirtLimit));

        if (availableCarbon < price) {
            sendErrorToPlayer(player, "cs-insufficient-carbon");
            return;
        }

        dataMap.put(DataNames.dirtAmount, dataMap.get(DataNames.dirtAmount) + 1);
        MongoBase.setValue(player, "availableCarbon", availableCarbon - price);
        MongoBase.setValue(player, "cloudFarmSystem", dataMap);

        player.sendMessage(Repo.getMSG("cfs-success-buy"));
        CloudFarmGUI.updateSingle(player);
    }

    public static void upgradeEmployeeLimit(Player player) {
        Map<String, Integer> dataMap = CloudFarmData.getPlayerData(player.getUniqueId().toString());
        Double availableCarbon = (Double) MongoBase.getValue(player.getUniqueId().toString(), "availableCarbon");
        int price = CloudFarmData.calculatePrice("cfs-worker-limit-upgrade-price", dataMap.get("maxWorkerAmount"));

        if (availableCarbon < price) {
            sendErrorToPlayer(player, "cs-insufficient-carbon");
            return;
        }

        dataMap.put("maxEmployeeAmount", dataMap.get("maxEmployeeAmount") + 1);
        MongoBase.setValue(player, "availableCarbon", availableCarbon - price);
        MongoBase.setValue(player, "cloudFarmSystem", dataMap);

        player.sendMessage(Repo.getMSG("cfs-success-buy"));
        CloudFarmGUI.updateSingle(player);
    }

    public static void upgradeStorageLimit(Player player) {
        Map<String, Integer> dataMap = CloudFarmData.getPlayerData(player.getUniqueId().toString());
        Double availableCarbon = (Double) MongoBase.getValue(player.getUniqueId().toString(), "availableCarbon");
        int price = dataMap.get("maxStorage") * Repo.getData("cfs-storage-upgrade-price");

        if (availableCarbon < price) {
            sendErrorToPlayer(player, "cs-insufficient-carbon");
            return;
        }
        if (CloudFarmData.calculateProduce(player.getUniqueId().toString()) >= 1) {
            sendErrorToPlayer(player, "cfs-storage-filled");
            return;
        }

        dataMap.put("maxStorage", dataMap.get("maxStorage") + Repo.getConfig("cfs-storage-upgrade-amount"));
        MongoBase.setValue(player, "availableCarbon", availableCarbon - price);
        MongoBase.setValue(player, "cloudFarmSystem", dataMap);

        player.sendMessage(Repo.getMSG("cfs-success-buy"));
        CloudFarmGUI.updateSingle(player);
    }

    public static void upgradeDirtEfficiency(Player player) {
        Map<String, Integer> dataMap = CloudFarmData.getPlayerData(player.getUniqueId().toString());
        Double availableCarbon = (Double) MongoBase.getValue(player.getUniqueId().toString(), "availableCarbon");
        int price = dataMap.get("dirtEfficiency") * Repo.getData("cfs-dirt-efficiency-upgrade-price");

        if (availableCarbon < price) {
            sendErrorToPlayer(player, "cs-insufficient-carbon");
            return;
        }

        dataMap.put("dirtEfficiency", dataMap.get("dirtEfficiency") + 1);
        MongoBase.setValue(player, "availableCarbon", availableCarbon - price);
        MongoBase.setValue(player, "cloudFarmSystem", dataMap);

        player.sendMessage(Repo.getMSG("cfs-success-buy"));
        CloudFarmGUI.updateSingle(player);
    }

    public static void upgradeEmployeeEfficiency(Player player) {
        Map<String, Integer> dataMap = CloudFarmData.getPlayerData(player.getUniqueId().toString());
        Double availableCarbon = (Double) MongoBase.getValue(player.getUniqueId().toString(), "availableCarbon");
        int price = dataMap.get("employeeEfficiency") * Repo.getData("cfs-employee-efficiency-upgrade-price");

        if (availableCarbon < price) {
            sendErrorToPlayer(player, "cs-insufficient-carbon");
            return;
        }

        dataMap.put("employeeEfficiency", dataMap.get("employeeEfficiency") + 1);
        MongoBase.setValue(player, "availableCarbon", availableCarbon - price);
        MongoBase.setValue(player, "cloudFarmSystem", dataMap);

        player.sendMessage(Repo.getMSG("cfs-success-buy"));
        CloudFarmGUI.updateSingle(player);
    }
    */
    public static void sendErrorToPlayer(Player player, String messageID) {
        player.sendMessage(Repo.getMSG(messageID));
        player.closeInventory();
    }
}
