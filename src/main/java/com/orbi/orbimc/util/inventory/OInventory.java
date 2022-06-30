package com.orbi.orbimc.util.inventory;

import com.orbi.orbimc.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public interface OInventory {
    void openInventory(Player player);

    void onClick(InventoryClickEvent e);

    void onPClick(InventoryClickEvent e);

    void onClose(InventoryCloseEvent e);

    void onOpen(InventoryOpenEvent e);

    void setOpenEvent(Consumer<InventoryOpenEvent> e);

    void setCloseEvent(Consumer<InventoryCloseEvent> e);

    InventoryBuilder2 setItem(int slot, ItemStack itemStack);

    InventoryBuilder2 setItem(int slot, ItemBuilder itemBuilder);

    void setEvent(int slot, Consumer<InventoryClickEvent> e);

    void setEvent(int slot, Consumer<InventoryClickEvent> e, boolean cancel);

    void setPEvent(Material material, Consumer<InventoryClickEvent> e);

    void update(int slot);

    void updateAll();

    void build();
}
