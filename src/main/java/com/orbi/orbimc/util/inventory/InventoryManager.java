package com.orbi.orbimc.util.inventory;

import com.orbi.orbimc.database.Repo;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class InventoryManager {

    public static final Map<String, InventoryBuilder2> inventories = new HashMap<>();

    public static void add(InventoryBuilder2 inventoryBuilder) {
        inventories.put(inventoryBuilder.getTitle(), inventoryBuilder);
    }

    public static void open(Player player, String title) {
        inventories.get(Repo.getText(title)).openInventory(player);
    }

    public static void open(HumanEntity player, String title) {
        inventories.get(Repo.getText(title)).openInventory((Player) player);
    }

}
