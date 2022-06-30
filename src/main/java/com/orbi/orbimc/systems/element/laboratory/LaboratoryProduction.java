package com.orbi.orbimc.systems.element.laboratory;

import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.database.MongoBase;
import com.orbi.orbimc.systems.element.ElementData;
import com.orbi.orbimc.systems.energy.EnergyData;
import org.bukkit.entity.Player;

import java.util.Map;

public class LaboratoryProduction {
    //Çalışıyor mu emin değilim
    //Enerji ve item sistemi yapılmalı devam ettirebilmek için
    public static void produce(Player player, int energyCost, String amountType, String resistanceLevelType, String minLimit) {
        Map<String, Long> energyData = EnergyData.getPlayerData(player);
        Map<String, Integer> elementData = ElementData.getPlayerData(player);

        if (energyData.get("storedEnergy") < energyCost)
            sendError(player, "esn-insufficient-energy");
        else if (elementData.get(amountType) == Repo.getConfig(minLimit) + elementData.get(resistanceLevelType))
            sendError(player, "es-insufficient-resistance");
        else {
            energyData.put("storedEnergy", energyData.get("storedEnergy") - energyCost);
            elementData.put(amountType, elementData.get(amountType) + 1);
            MongoBase.setValue(player, "energySystem", energyData);
            MongoBase.setValue(player, "elementSystem", elementData);
            player.sendMessage(Repo.getMSG("es-success-produce"));
            LaboratoryGUI.createPanel(player);
        }

    }

    public static void produceProton(Player player) {
        produce(player, ProduceCost.protonProductionEnergyCost, "protonAmount", "energyResistanceLevel", "es-min-proton-limit");
    }
    public static void produceNeutron(Player player) {
        produce(player, ProduceCost.neutronProductionEnergyCost, "neutronAmount", "radiationResistanceLevel", "es-min-neutron-limit");
    }
    public static void produceElectron(Player player) {
        produce(player, ProduceCost.electronProductionEnergyCost, "electronAmount", "temperatureResistanceLevel", "es-min-electron-limit");
    }

    public static void sendError(Player player, String msg) {
        player.sendMessage(Repo.getMSG(msg));
        player.closeInventory();
    }

}
