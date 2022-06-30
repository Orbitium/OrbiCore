package com.orbi.orbimc.systems.rhodium;

import com.orbi.orbimc.listeners.root.InventoryEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

public class RhodiumEvents {

    @InventoryEvent(title = "rs-panel-title")
    public static void inventoryClick(InventoryClickEvent e) {
        e.setCancelled(true);
    }

}
