package com.orbi.orbimc.listeners;

import com.orbi.orbimc.OrbiCore;
import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.systems.energy.EnergyEvents;
import com.orbi.orbimc.systems.playeritem.PlayerItemEvents;
import com.orbi.orbimc.util.inventory.InventoryManager;
import com.orbi.orbimc.villager.Parchment;
import com.orbi.orbimc.villager.VillagerIQ;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.InventoryHolder;

public class InventoryListener implements Listener {

    @EventHandler
    public static void onClickOnInventory(InventoryClickEvent e) {
        //Anvilde özel büyüler basılınca eşya alınmıyor onu hallet
        if (e.getClickedInventory() != null && e.getClickedInventory().getType() == InventoryType.ANVIL) {
            if (e.getSlot() == 2)
                if (e.getWhoClicked().getInventory().getItemInMainHand().getType() == Material.AIR)
                    if (e.getClickedInventory().getItem(2) != null) {
                        e.getWhoClicked().setItemOnCursor(e.getClickedInventory().getItem(2));
                        e.getClickedInventory().clear();
                    }
            return;
        } else if (e.getInventory().getHolder() == null || e.getCurrentItem() == null)
            return;

        InventoryHolder holder = e.getClickedInventory().getHolder();
        String title = e.getView().getTitle();

        if (holder == OrbiCore.getInventoryHolder()) {
            InventoryManager.inventories.get(title).onClick(e);
            if (e.getClick().equals(ClickType.DOUBLE_CLICK))
                e.setCancelled(true);
        } else if (holder == ((Player) e.getWhoClicked())) {
            if (InventoryManager.inventories.containsKey(title)) {
                InventoryManager.inventories.get(title).onPClick(e);
                if (e.getClick().equals(ClickType.DOUBLE_CLICK))
                    e.setCancelled(true);
            }
        }


        // FIXME: 1.04.2022 Oyuncu envanterindeki bir eşyaya iki kez tıklarsa, yukarıdaki envanterden eşyalar geliyor
    }

    @EventHandler
    public static void onCloseInventory(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() != null && e.getInventory().getHolder() == OrbiCore.getInventoryHolder()) {
            InventoryManager.inventories.get(e.getView().getTitle()).onClose(e);
        }
        String title = e.getView().getTitle();
        if (title.equals(Repo.getText("es-item-add-panel-title")))
            PlayerItemEvents.inventoryClose(e);
        else if (title.equals(Repo.getText("esn-add-coal-panel")))
            EnergyEvents.inventoryClose(e);
    }

    @EventHandler
    public static void onOpenInventory(InventoryOpenEvent e) {
        if (e.getInventory().getHolder() != null && e.getInventory().getHolder() == OrbiCore.getInventoryHolder()) {
            InventoryManager.inventories.get(e.getView().getTitle()).onOpen(e);
        }
        if (e.getInventory().getType() == InventoryType.MERCHANT)
            if (((Player) e.getPlayer()).isSneaking())
                e.setCancelled(true);
    }
}
