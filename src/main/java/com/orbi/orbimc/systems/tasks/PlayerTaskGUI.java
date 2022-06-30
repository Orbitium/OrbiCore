package com.orbi.orbimc.systems.tasks;

import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.systems.tasks.root.PlayerTask;
import com.orbi.orbimc.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.Map;

public class PlayerTaskGUI {

    public static List<String> defaultLore;

    public static void createPanel(Player player) {
        Inventory inv = Bukkit.createInventory(null, 9, Repo.getText("task-panel-title"));
        int playerTaskLevel = PlayerTaskData.getPlayerTaskLevel(player);

        for (Map.Entry<String, PlayerTask> entry : PlayerTaskData.taskList.entrySet()) {
            PlayerTask value = entry.getValue();
            String name = ChatColor.GOLD + "Görev: " + value.getTaskName();
            boolean glow = value.getTaskLevel() < playerTaskLevel;
            inv.setItem(value.getTaskLevel(), new ItemBuilder(value.getRepresentative(), name, defaultLore, glow).build());
        }
        player.openInventory(inv);
    }

}
