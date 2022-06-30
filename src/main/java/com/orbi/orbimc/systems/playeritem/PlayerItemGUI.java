package com.orbi.orbimc.systems.playeritem;

import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.util.InventoryBuilder;
import com.orbi.orbimc.util.ItemBuilder;
import com.orbi.orbimc.util.StaticItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Map;

public class PlayerItemGUI {

    public static void createPanel(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, Repo.getText("is-panel-title"));
        for (int i = 0; i < 9; i++)
            inv.setItem(i, StaticItems.spaceItem);
        for (int i = 10; i < 54; i++)
            inv.setItem(i, StaticItems.spaceItem2);

        Map<String, Integer> playerData = PlayerItemData.getPlayerData(player);

        String storageHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2UyMjM5MWUzNWEzZTViY2VlODlkYjMxMmU4NzRmZGM5ZDllN2E2MzUxMzE0YjgyYmRhOTdmYmQyYmU4N2ViOCJ9fX0=";
        inv.setItem(4, new ItemBuilder(storageHead, Repo.getText("is-storage-name"), "Çekim gücü: " + playerData.get("level")).build());

        int lastIndex = 9;

        for (Map.Entry<String, Integer> entry : playerData.entrySet()) {
            try {
                String key = entry.getKey();
                if (!key.equals("level"))
                    inv.setItem(lastIndex++, new ItemBuilder(Material.getMaterial(key), entry.getValue() + " adet bulunuyor.", true, true).build());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                PlayerItemData.savePlayerItemByCarbon(player);
                break;
            }
        }

        player.openInventory(inv);
    }

    public static void createAddItemPanel(Player player) {
        Inventory inventory = InventoryBuilder.createPanel1(Repo.getText("es-item-add-panel-title"));
        inventory.setItem(49, new ItemBuilder(Material.NETHER_STAR, Repo.getText("is-add-item-name")).build());
        player.openInventory(inventory);
    }

}
