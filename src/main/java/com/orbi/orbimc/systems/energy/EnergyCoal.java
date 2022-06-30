package com.orbi.orbimc.systems.energy;

import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.database.MongoBase;
import com.orbi.orbimc.util.StaticItems;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class EnergyCoal {

    public static void addCoalToStorage(Player player, Inventory inv) {
        Map<String, Long> playerEnergyData = EnergyData.getPlayerData(player);
        int amount = 0;
        int messageType = 0; // 0 -> hiç kömür koyulmamış, 1 -> kömür koyulmuş ama yer yok, 2 -> birz kömür eklendi, 3 -> tüm kömür eklendi
        for (ItemStack itemStack : inv.getContents()) {
            if (itemStack == null || itemStack.getItemMeta().hasDisplayName())
                continue;
            Material material = itemStack.getType();
            if (material.equals(Material.COAL) || material.equals(Material.CHARCOAL)) {
                amount += itemStack.getAmount();
            }
            inv.remove(itemStack);
        }
        if (amount > 0) {
            long playerStorageLimit = playerEnergyData.get("fuelStorageLimit") - playerEnergyData.get("storedFuel");
            if (playerStorageLimit == 0) {
                messageType = 1;
                player.getInventory().addItem(new ItemStack(Material.CHARCOAL, amount));
            } else if (amount > playerStorageLimit) {
                messageType = 2;
                int backtake = (int) (amount - playerStorageLimit);
                playerEnergyData.put("storedFuel", playerStorageLimit + playerEnergyData.get("storedFuel"));
                player.getInventory().addItem(new ItemStack(Material.CHARCOAL, backtake));
            } else {
                playerEnergyData.put("storedFuel", playerEnergyData.get("storedFuel") + amount);
                messageType = 3;
            }
        }
        if (messageType != 0) {
            player.sendMessage(Repo.getMSG("esn-success-item-transfer" + messageType));
            MongoBase.setValue(player, "energySystem", playerEnergyData);
        }
        EnergyGUI.createPanel(player);
    }

    public static void cancelTransfer(Player player, Inventory inv) {
        for (ItemStack itemStack : inv.getContents()) {
            if (itemStack == null || itemStack.equals(StaticItems.spaceItem) || itemStack.getItemMeta().hasDisplayName())
                continue;
            player.getInventory().addItem(itemStack);
        }
    }

}
