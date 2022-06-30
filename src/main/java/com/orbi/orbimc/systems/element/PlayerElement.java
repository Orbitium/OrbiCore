package com.orbi.orbimc.systems.element;

import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.database.MongoBase;
import com.orbi.orbimc.bone.security.authority.Permission;
import com.orbi.orbimc.bone.security.authority.PermissionController;
import com.orbi.orbimc.systems.element.laboratory.ElementUpCost;
import com.orbi.orbimc.systems.energy.EnergyData;
import com.orbi.orbimc.bone.tab.PlayerTabList;
import org.bukkit.entity.Player;

import java.util.Map;

public class PlayerElement {

    public static void elementUp(Player player, ElementUpCost elementUpCost) {
        Map<String, Object> elementUpCostMap = elementUpCost.getByMap();
        Map<String, Integer> elementData = ElementData.getPlayerData(player);
        Map<String, Integer> resistanceData = ElementData.createLabData(elementData);
        Map<String, Long> energyData = EnergyData.getPlayerData(player);

        Permission nextElement = PermissionController.getPermissionByName((String) elementUpCostMap.get("nextElement"));
        elementUpCostMap.remove("nextElement");

        if ((int) elementUpCostMap.get("storedEnergy") > energyData.get("storedEnergy"))
            sendError(player, "esn-insufficient-energy");

        else if (resistanceData.get("energyResistance") < (int) elementUpCostMap.get("storedEnergy"))
            sendError(player, "es-insufficient-resistance");
        else if (resistanceData.get("temperatureResistance") < (int) elementUpCostMap.get("temperatureResistance"))
            sendError(player, "es-insufficient-resistance");
        else if (resistanceData.get("radiationResistance") < (int) elementUpCostMap.get("radiationResistance"))
            sendError(player, "es-insufficient-resistance");

        else if (elementData.get("protonAmount") < (int) elementUpCostMap.get("protonAmount"))
            sendError(player, "es-insufficient-proton");
        else if (elementData.get("neutronAmount") < (int) elementUpCostMap.get("neutronAmount"))
            sendError(player, "es-insufficient-neutron");
        else if (elementData.get("electronAmount") < (int) elementUpCostMap.get("electronAmount"))
            sendError(player, "es-insufficient-electron");
        else {
            elementData.put("protonAmount", elementData.get("protonAmount") - (int) elementUpCostMap.get("protonAmount"));
            elementData.put("neutronAmount", elementData.get("neutronAmount") - (int) elementUpCostMap.get("neutronAmount"));
            elementData.put("electronAmount", elementData.get("electronAmount") - (int) elementUpCostMap.get("electronAmount"));
            energyData.put("storedEnergy", energyData.get("storedEnergy") - (int) elementUpCostMap.get("storedEnergy"));
            MongoBase.setValue(player, "permission", nextElement.getPermissionLevel());
            MongoBase.setValue(player, "elementSystem", elementData);
            MongoBase.setValue(player, "energySystem", energyData);
            player.sendMessage(Repo.getMSG("es-success-produce"));
            PlayerTabList.alignPlayer(player);

            player.closeInventory();
        }
    }

    public static void sendError(Player player, String msg) {
        player.sendMessage(Repo.getMSG(msg));
        player.closeInventory();
    }

}
