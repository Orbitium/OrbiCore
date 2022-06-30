package com.orbi.orbimc.systems.cloudfarm;

import com.orbi.orbimc.listeners.root.InventoryEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class CloudFarmEvents {

    //TODO DENGELEME YAPILACAK
    //TODO DISCORD UYUMLU HALE GETİRİLECEK

    @InventoryEvent(title = "cfs-panel-title")
    public static void inventoryClick(InventoryClickEvent e) {
        e.setCancelled(true);
        Player player = (Player) e.getWhoClicked();
        switch (e.getSlot()) {
            case 10:
                CloudFarmPurchase.buyDirt(player);
                break;
            case 12:
                CloudFarmPurchase.buyWorker(player);
                break;
            case 16:
                CloudFarmData.takeProducedCarbon((Player) e.getWhoClicked());
                break;
            case 29:
                CloudFarmUpgrades.upgradeDirtLimit((Player) e.getWhoClicked());
                break;
            case 30:
                CloudFarmUpgrades.upgradeWorkerLimit((Player) e.getWhoClicked());
                break;
            case 31:
                CloudFarmUpgrades.upgradeStorageLimit((Player) e.getWhoClicked());
                break;
            case 32:
                CloudFarmUpgrades.upgradeDirtEfficiency((Player) e.getWhoClicked());
                break;
            case 33:
                CloudFarmUpgrades.upgradeWorkerEfficiency((Player) e.getWhoClicked());

        }
    }

}
