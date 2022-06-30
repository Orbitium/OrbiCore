package com.orbi.orbimc.systems.playeritem;

import com.orbi.orbimc.database.MongoBase;
import com.orbi.orbimc.util.StaticItems;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class PlayerItemObserver {

    public static void addStorage(Player player, Inventory inv) {
        Map<String, Integer> playerData = PlayerItemData.getPlayerData(player);

        for (ItemStack itemStack : inv.getContents()) {
            if (itemStack == null || itemStack.getItemMeta().hasDisplayName() || itemStack.equals(StaticItems.spaceItem))
                continue;

            String material = itemStack.getType().name();

            if (playerData.get(material) == null) {
                if (playerData.size() <= 37)
                    playerData.put(material, itemStack.getAmount());
                else
                    return;
            } else
                playerData.put(material, playerData.get(material) + itemStack.getAmount());
            itemStack.setAmount(0);
        }
        playerData.put("level", PlayerItemData.calculateLevel(playerData));
        MongoBase.setValue(player, "itemSystem", playerData);
        player.closeInventory();
    }

    public static void cancelProcess(Player player, Inventory inv) {
        for (ItemStack itemStack : inv.getContents()) {
            if (itemStack == null || itemStack.equals(StaticItems.spaceItem) || itemStack.getItemMeta().hasDisplayName())
                continue;
            else player.getInventory().addItem(itemStack);
        }
    }

}
