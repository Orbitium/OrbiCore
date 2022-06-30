package com.orbi.orbimc.systems.element.laboratory;

import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.database.MongoBase;
import com.orbi.orbimc.systems.element.ElementData;
import com.orbi.orbimc.systems.playeritem.PlayerItemData;
import com.orbi.orbimc.systems.playeritem.PlayerItemNameConverter;
import org.bukkit.entity.Player;

import java.util.Map;

public class LaboratoryUpgrade {

    public static void upgrade(Player player, UpgradeCost upgradeCost, String resistanceName) {
        Map<String, Integer> playerElementData = ElementData.getPlayerData(player);
        Map<String, Integer> upgradeCostMap = upgradeCost.getByMap(playerElementData.get(resistanceName));
        Map<String, Integer> playerItemData = PlayerItemData.getPlayerData(player);

        //long playerCarbon = CarbonData.getCarbon(player) - upgradeCostMap.get("carbonAmount");
/*
        if (playerCarbon < 0) {
            player.sendMessage(Repo.getMSG("cs-insufficient-carbon"));
            player.closeInventory();
            return;
        }
*/
        upgradeCostMap.remove("carbonAmount");
        upgradeCostMap.remove("before");
        upgradeCostMap.remove("after");

        for (Map.Entry<String, Integer> costs : upgradeCostMap.entrySet()) {
            String selectedItem = costs.getKey();
            if (playerItemData.get(selectedItem) < costs.getValue()) { //Oyuncuda yeterli eşyalar var mı
                player.sendMessage(PlayerItemNameConverter.convert(selectedItem.substring(0, selectedItem.length() - 6)));
                player.closeInventory();
                return;
            } else //Varsa o eşyayı azalt ve üretime hazırla
                playerItemData.put(selectedItem, playerItemData.get(selectedItem) - (int) costs.getValue());
        }

        playerElementData.put(resistanceName, playerElementData.get(resistanceName) + 1);
        MongoBase.setValue(player, "itemSystem", playerItemData);
        MongoBase.setValue(player, "elementSystem", playerElementData);
        //CarbonData.decreaseCarbon(player, upgradeCostMap.get("carbonAmount"));
        player.sendMessage(Repo.getMSG("es-success-produce"));
        LaboratoryGUI.createPanel(player);
    }

    public static void upgradeEnergyResistance(Player player) {
        upgrade(player, UpgradeCost.ENERGY, "energyResistanceLevel");
    }

    public static void upgradeTemperatureResistance(Player player) {
        upgrade(player, UpgradeCost.TEMPERATURE, "temperatureResistanceLevel");
    }

    public static void upgradeRadiationResistance(Player player) {
        upgrade(player, UpgradeCost.RADIATION, "radiationResistanceLevel");
    }

    public static void repairLab(Player player) {
        Map<String, Integer> playerItemData = PlayerItemData.getPlayerData(player);
        Map<String, Integer> needList = ElementData.createRepairLabData();
        Map<String, Integer> playerElementData = ElementData.getPlayerData(player);

        for (Map.Entry<String, Integer> costs : needList.entrySet()) {
            String selectedItem = costs.getKey();
            if (playerItemData.get(selectedItem) < costs.getValue()) { //Oyuncuda yeterli eşyalar var mı
                player.sendMessage(PlayerItemNameConverter.convert(selectedItem.substring(0, selectedItem.length() - 6)));
                player.closeInventory();
                return;
            } else //Varsa o eşyayı azalt ve üretime hazırla
                playerItemData.put(selectedItem, playerItemData.get(selectedItem) - costs.getValue());
        }
        MongoBase.setValue(player, "itemSystem", playerItemData);
        playerElementData.put("energyResistanceLevel", 1);
        MongoBase.setValue(player, "elementSystem", playerElementData);
        player.sendMessage(Repo.getMSG("es-success-repair"));
    }
}
