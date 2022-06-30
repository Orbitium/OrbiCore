package com.orbi.orbimc.systems.tasks.tasklist;

import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.systems.element.ElementData;
import com.orbi.orbimc.systems.tasks.root.PlayerTask;
import com.orbi.orbimc.systems.tasks.root.PlayerTaskController;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class RhodiumTask extends PlayerTask {

    public RhodiumTask() {
        setTaskName(ChatColor.GRAY + "Rodyum!");
        setHowToComplete(ChatColor.GRAY + "Bu pek görev sayılmaz, sadece rodyumu anlatmak istedim :). Rodyum her 24 saatte" +
                " bir oyuna girdiğinde elementine bağlı olarak otomatik bir şekilde hesabına tanımların. Tıklayarak bu görevi tamamlayabilirsin.");
        setTaskLevel(4);
        setTaskAward(new ItemStack(Material.IRON_AXE));
        setRepresentative(Material.NETHERITE_BLOCK);
        setDoneMessage(ChatColor.GOLD + "Bir sonraki görevde, enerji sisteminden bahsedeceğim.");
    }

    @Override
    public void check(Player player) {
        PlayerTaskController.doneTask(player, this);
    }
}
