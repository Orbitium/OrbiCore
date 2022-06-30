package com.orbi.orbimc.systems.tasks.root;

import com.orbi.orbimc.database.MongoBase;
import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.systems.tasks.PlayerTaskData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerTaskController {

    public static void doneTask(Player player, PlayerTask playerTask) {
        MongoBase.setValue(player, "taskLevel", PlayerTaskData.getPlayerTaskLevel(player) + 1);
        player.getInventory().addItem(playerTask.getTaskAward());
        player.sendMessage(ChatColor.AQUA + "[Görev başarılı] " + playerTask.getDoneMessage());
    }

}
