package com.orbi.orbimc.systems.cloudfarm;

import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.database.MongoBase;
import org.bukkit.entity.Player;

import java.util.Map;

public class CloudFarmPurchase {

    private static void buy(Player player, String dataName, String priceName, String limitType) {
        Map<String, Integer> dataMap = CloudFarmData.getPlayerData(player.getUniqueId().toString());
        //long availableCarbon = CarbonData.getCarbon(player);
        long price = CloudFarmData.calculatePrice(priceName, dataMap.get(dataName));

        if (dataMap.get(dataName).equals(dataMap.get(limitType))) {
            player.sendMessage(Repo.getMSG("cfs-insufficient-area"));
            player.closeInventory();
            return;
        }
/*
        if (availableCarbon < price) {
            player.sendMessage(Repo.getMSG("cs-insufficient-carbon"));
            player.closeInventory();
            return;

       }
*/
        dataMap.put(dataName, dataMap.get(dataName) + 1);
        MongoBase.setValue(player, "cloudFarmSystem", dataMap);
        //CarbonData.decreaseCarbon(player, price);
        player.sendMessage(Repo.getMSG("cfs-success-buy"));
        CloudFarmGUI.updateSingle(player);
    }

    public static void buyDirt(Player player) {
        buy(player, CloudFarmDataNames.dirtAmount, PriceList.dirtAmount, "dirtLimit");
    }

    public static void buyWorker(Player player) {
        buy(player, CloudFarmDataNames.workerAmount, PriceList.workerAmount, "workerLimit");
    }
}
