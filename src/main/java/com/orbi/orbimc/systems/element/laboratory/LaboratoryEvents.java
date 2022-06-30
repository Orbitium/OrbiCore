package com.orbi.orbimc.systems.element.laboratory;

import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.bone.security.authority.PermissionController;
import com.orbi.orbimc.listeners.root.InventoryEvent;
import com.orbi.orbimc.systems.element.PlayerElement;
import com.orbi.orbimc.systems.energy.EnergyGUI;
import com.orbi.orbimc.systems.playeritem.PlayerItemGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class LaboratoryEvents {

    @InventoryEvent(title = "es-panel-title")
    public static void inventoryClick(InventoryClickEvent e) {
        e.setCancelled(true);
        Player player = (Player) e.getWhoClicked();
        switch (e.getSlot()) {
            case 1:
                LaboratoryProduction.produceProton(player);
                break;
            case 10:
                LaboratoryProduction.produceNeutron(player);
                break;
            case 19:
                LaboratoryProduction.produceElectron(player);
                break;
            case 12:
                LaboratoryUpgrade.upgradeEnergyResistance(player);
                break;
            case 13:
                LaboratoryUpgrade.upgradeTemperatureResistance(player);
                break;
            case 14:
                LaboratoryUpgrade.upgradeRadiationResistance(player);
                break;
            case 16:
                EnergyGUI.createPanel(player);
                break;
            case 25:
                PlayerItemGUI.createPanel(player);
                break;
            case 31:
                PlayerElement.elementUp(player, ElementUpCost.valueOf(PermissionController.getPermission(player.getUniqueId().toString()).name()));
                break;
        }
    }

    @InventoryEvent(title = "es-broken-panel-title")
    public static void repairPanelClick(InventoryClickEvent e) {
        e.setCancelled(true);
        if (e.getSlot() == 13) {
            LaboratoryUpgrade.repairLab((Player) e.getWhoClicked());
        }
    }

}
