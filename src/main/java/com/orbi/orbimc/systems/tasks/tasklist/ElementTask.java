package com.orbi.orbimc.systems.tasks.tasklist;

import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.systems.element.ElementData;
import com.orbi.orbimc.systems.playeritem.PlayerItemData;
import com.orbi.orbimc.systems.tasks.root.PlayerTask;
import com.orbi.orbimc.systems.tasks.root.PlayerTaskController;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ElementTask extends PlayerTask {

    public ElementTask() {
        setTaskName(ChatColor.GRAY + "Elementler!");
        setHowToComplete(ChatColor.GRAY + "/element laboratuvarını tamir et!");
        setTaskLevel(8);
        setTaskAward(new ItemStack(Material.DIAMOND, 6), new ItemStack(Material.NETHERITE_INGOT, 1), new ItemStack(Material.GOLD_BLOCK, 3), new ItemStack(Material.IRON_INGOT, 3));
        setRepresentative(Material.NETHER_STAR);
        setDoneMessage(ChatColor.GOLD + "Yıldızlara kadar olan yolculuğunda bu son öğrenimdi...");
    }

    @Override
    public void check(Player player) {
        Map<String, Integer> playerData = ElementData.getPlayerData(player);
        if (playerData.get("energyResistanceLevel") >= 1) {
            PlayerTaskController.doneTask(player, this);
        } else
            player.sendMessage(Repo.getMSG("task-unsuccessful"));
    }
}