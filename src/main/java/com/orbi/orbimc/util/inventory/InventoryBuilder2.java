package com.orbi.orbimc.util.inventory;

import com.orbi.orbimc.OrbiCore;
import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class InventoryBuilder2 implements OInventory {

    private final Inventory inventory;
    private final String title;

    private final Map<Integer, Consumer<InventoryClickEvent>> guiEvent = new HashMap<>();
    private final Map<Material, Consumer<InventoryClickEvent>> playerInvEvent = new HashMap<>();

    private Consumer<InventoryCloseEvent> inventoryCloseEvent;
    private Consumer<InventoryOpenEvent> inventoryOpenEvent;


    public InventoryBuilder2(Pattern pattern, int size, String title) {
        this.title = Repo.getText(title);
        inventory = Bukkit.createInventory(OrbiCore.getInventoryHolder(), size, this.title);
        pattern.loadContent(inventory);
    }

    @Override
    public InventoryBuilder2 setItem(int slot, ItemStack itemStack) {
        inventory.setItem(slot, itemStack);
        return this;
    }

    @Override
    public InventoryBuilder2 setItem(int slot, ItemBuilder itemBuilder) {
        inventory.setItem(slot, itemBuilder.build());
        return this;
    }

    @Override
    public void setEvent(int slot, Consumer<InventoryClickEvent> e) {
        guiEvent.put(slot, e.andThen(ev -> ev.setCancelled(true)));
    }

    @Override
    public void setEvent(int slot, Consumer<InventoryClickEvent> e, boolean cancel) {
        guiEvent.put(slot, e.andThen(ev -> ev.setCancelled(cancel)));
    }

    @Override
    public void setPEvent(Material material, Consumer<InventoryClickEvent> e) {
        playerInvEvent.put(material, e);
    }

    @Override
    public void openInventory(Player player) {
        player.openInventory(inventory);
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        int slot = e.getSlot();
        if (guiEvent.containsKey(slot))
            guiEvent.get(slot).accept(e);
        else
            e.setCancelled(true);
    }

    @Override
    public void onPClick(InventoryClickEvent e) {
        if (playerInvEvent.containsKey(e.getCurrentItem().getType()))
            playerInvEvent.get(e.getCurrentItem().getType()).accept(e);
    }

    @Override
    public void onOpen(InventoryOpenEvent e) {
        if (inventoryOpenEvent != null)
            inventoryOpenEvent.accept(e);
    }

    @Override
    public void setOpenEvent(Consumer<InventoryOpenEvent> e) {
        inventoryOpenEvent = e;
    }

    @Override
    public void onClose(InventoryCloseEvent e) {
        if (inventoryCloseEvent != null)
            inventoryCloseEvent.accept(e);
    }

    @Override
    public void setCloseEvent(Consumer<InventoryCloseEvent> e) {
        inventoryCloseEvent = e;
    }

    @Override
    public void update(int slot) {

    }

    @Override
    public void updateAll() {

    }

    public String getTitle() {
        return title;
    }

    @Override
    public void build() {
        InventoryManager.add(this);
    }
}
