package com.orbi.orbimc.util;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class InventoryBuilder {
    public static Inventory createPanel1(String title) {//Aşağısı ve yukarısı siyah camla kaplı double sandık
        Inventory inv = Bukkit.createInventory(null, 54, title);
        for (int i = 0; i < 9; i++)
            inv.setItem(i, StaticItems.spaceItem);
        for (int i = 45; i < 54; i++)
            inv.setItem(i, StaticItems.spaceItem);
        return inv;
    }

    public static Inventory createPanel2(String title) { //Çerçeveli tek sandık
        Inventory inv = Bukkit.createInventory(null, 27, title);
        for (int i = 0; i < 10; i++)
            inv.setItem(i, StaticItems.spaceItem);
        for (int i = 17; i < 27; i++)
            inv.setItem(i, StaticItems.spaceItem);
        return inv;
    }
    public static Inventory createCustomInventory(String title, InventoryType type) { //Tamamen cam kaplı sandık
        Inventory inv = Bukkit.createInventory(null, type, title);
        for (int i = 0; i < type.getDefaultSize(); i++)
            inv.setItem(i, StaticItems.spaceItem);
        return inv;
    }

    public static Inventory createCustomInventory(String title, int size) { //Tamamen cam kaplı sandık
        Inventory inv = Bukkit.createInventory(null, size, title);
        for (int i = 0; i < size; i++)
            inv.setItem(i, StaticItems.spaceItem);
        return inv;
    }
}