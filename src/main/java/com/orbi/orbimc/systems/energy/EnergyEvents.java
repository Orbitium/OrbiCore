package com.orbi.orbimc.systems.energy;

import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.listeners.root.InventoryEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class EnergyEvents {

    @InventoryEvent(title = "ens-panel-title")
    public static void inventoryClick(InventoryClickEvent e) {
        e.setCancelled(true);
        if (e.getClickedInventory().getItem(4).getItemMeta().getDisplayName().equals(Repo.getText("esn-start-production-name"))) {
            Player player = (Player) e.getWhoClicked();
            switch (e.getSlot()) {
                case 4:
                    EnergyCalculation.startProduction(player);
                    break;
                case 10:
                    EnergyGUI.addCoalPanel(player);
                    break;
                case 12:
                    EnergyUpgrade.upgradeFuelStorage(player);
                    break;
                case 13:
                    EnergyUpgrade.upgradeProductionEfficiency(player);
                    break;
                case 14:
                    EnergyUpgrade.upgradeBatteryCapacity(player);
                    break;
            }
        } else {
            e.getWhoClicked().sendMessage(Repo.getMSG("esn-canceled-process"));
            e.getWhoClicked().closeInventory();
        }
    }

    @InventoryEvent(title = "esn-add-coal-panel")
    public static void inventoryClick2(InventoryClickEvent e) { //Kömür ekleme paneli için
        e.setCancelled(true);
        switch (e.getSlot()) {
            case 49:
                EnergyCoal.addCoalToStorage((Player) e.getWhoClicked(), e.getClickedInventory());
        }
    }

    public static void inventoryClose(InventoryCloseEvent e) {
        EnergyCoal.cancelTransfer((Player) e.getPlayer(), e.getInventory());
    }
}
