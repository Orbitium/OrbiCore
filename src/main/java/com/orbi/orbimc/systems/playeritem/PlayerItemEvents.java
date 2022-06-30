package com.orbi.orbimc.systems.playeritem;

import com.orbi.orbimc.database.MongoBase;
import com.orbi.orbimc.listeners.root.InventoryEvent;
import com.orbi.orbimc.util.StaticItems;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class PlayerItemEvents {

    @InventoryEvent(title = "is-panel-title")
    public static void inventoryClick(InventoryClickEvent e) {
        e.setCancelled(true);
        if (e.getCurrentItem().equals(StaticItems.spaceItem) || e.getCurrentItem().equals(StaticItems.spaceItem2))
            return;
        Player player = (Player) e.getWhoClicked();
        if (e.getSlot() == 4)
            PlayerItemGUI.createAddItemPanel(player);
        else {
            Material itemType = e.getCurrentItem().getType();
            Map<String, Integer> playerData = PlayerItemData.getPlayerData(player);
            int multiplier = e.getClick().isRightClick() ? 32 : 1;
            int selectedData = playerData.get(itemType.name());
            if (selectedData >= multiplier) {
                playerData.put(itemType.name(), selectedData -= multiplier);
                if (selectedData == 0)
                    playerData.remove(itemType.name());
                player.getInventory().addItem(new ItemStack(itemType, multiplier));
                MongoBase.setValue(player, "itemSystem", playerData);
                PlayerItemGUI.createPanel(player);
            } else {
                player.sendMessage("yeterisz bakiye kanks");
            }
        }

    }

    @InventoryEvent(title = "es-item-add-panel-title")
    public static void addPanelClick(InventoryClickEvent e) {
        e.setCancelled(true);
        if (e.getCurrentItem().getType().equals(Material.NETHER_STAR))
            PlayerItemObserver.addStorage((Player) e.getWhoClicked(), e.getClickedInventory());
    }

    public static void inventoryClose(InventoryCloseEvent e) {
        PlayerItemObserver.cancelProcess((Player) e.getPlayer(), e.getInventory());
    }
}
