package com.orbi.orbimc.systems.tasks.tasklist;

import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.systems.cloudfarm.CloudFarmData;
import com.orbi.orbimc.systems.tasks.root.PlayerTask;
import com.orbi.orbimc.systems.tasks.root.PlayerTaskController;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class DirtCraftTask extends PlayerTask {

    public DirtCraftTask() {
        setTaskName(ChatColor.GRAY + "Bulut tarla sistemine göz atalım!");
        setHowToComplete(ChatColor.GOLD + "/buluttarla üzerinden, 1 adet ücretsiz toprak ve işçini al!");
        setTaskLevel(2);
        setTaskAward(new ItemStack(Material.DIRT, 8), new ItemStack(Material.GRASS_BLOCK, 8), new ItemStack(Material.OAK_LOG, 20), new ItemStack(Material.IRON_PICKAXE));
        setRepresentative(Material.DIRT);
        setDoneMessage(ChatColor.GOLD + "Tebrikler! Artık sen oyundayken veya *değilken* senin için karbon üreten bir sanal tarlan var. " +
                "Bulut tarla sisteminde bu şekilde sanal toprak ve sanal çiftçi alarak pasif olarak karbon üretimi yapabilirsin. " +
                "Ayrıca çeşitli yükseltmeleri de var, onları keşfetmeyi sana bırakıyorum. Diğer görevde .");
    }

    @Override
    public void check(Player player) {
        Map<String, Integer> cloudFarmData = CloudFarmData.getPlayerData(player.getUniqueId().toString());
        if (cloudFarmData.get("dirtAmount") >= 1 && cloudFarmData.get("workerAmount") >= 1)
            PlayerTaskController.doneTask(player, this);
        else
            player.sendMessage(Repo.getMSG("task-unsuccessful"));
    }
}
