package com.orbi.orbimc.systems.tasks;

import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.listeners.root.InventoryEvent;
import com.orbi.orbimc.systems.tasks.root.PlayerTask;
import com.orbi.orbimc.util.StaticItems;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PlayerTaskEvents {

    @InventoryEvent(title = "task-panel-title")
    public static void onClickInventory(InventoryClickEvent e) {
        e.setCancelled(true);
        if (e.getCurrentItem().equals(StaticItems.spaceItem))
            return;

        String itemName = e.getCurrentItem().getItemMeta().getDisplayName().substring(9);

        if (PlayerTaskData.isContains(itemName)) {
            Player player = (Player) e.getWhoClicked();
            PlayerTask playerTask = PlayerTaskData.taskList.get(itemName);
            if (e.getClick().isLeftClick()) {
                int playerTaskLevel = PlayerTaskData.getPlayerTaskLevel(player);
                if (playerTaskLevel == playerTask.getTaskLevel())
                    playerTask.check(player);
                else if (playerTaskLevel > playerTask.getTaskLevel())
                    player.sendMessage(Repo.getMSG("task-already-done"));
                else if (playerTaskLevel < playerTask.getTaskLevel())
                    player.sendMessage(Repo.getMSG("task-insufficient-level"));
            } else if (e.getClick().isRightClick())
                player.sendMessage(ChatColor.AQUA + "[Bilgi] " + playerTask.getHowToComplete());
        }
        e.getWhoClicked().closeInventory();
    }

}
