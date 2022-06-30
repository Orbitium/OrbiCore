package com.orbi.orbimc.systems.tasks.tasklist;

import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.systems.energy.EnergyData;
import com.orbi.orbimc.systems.tasks.root.PlayerTask;
import com.orbi.orbimc.systems.tasks.root.PlayerTaskController;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class EnergyTask extends PlayerTask {

    public EnergyTask() {
        setTaskName(ChatColor.GRAY + "Çarkları döndürelim!");
        setHowToComplete(ChatColor.GOLD + "/enerji paneli üzerinde 30w kömür üretimi yap! Nasıl yapacağını bilmiyor musun?" +
                " Öğreteyim: /enerji yazdıktan sonra önüne gelen paneldeki kömür'e tıkla," +
                " daha sonra yeni açılan yere kömürlerini(odun kömürü de olur) koy ve kömür bloğuna bas. " +
                "/enerji yazarak enerji panelini tekrar aç ve en üstte bulunan 'enerji üretimini başlat! yazan butona bas!");
        setTaskLevel(5);
        setTaskAward(new ItemStack(Material.COAL, 64), new ItemStack(Material.IRON_INGOT, 6), new ItemStack(Material.COPPER_INGOT, 12));
        setRepresentative(Material.LAVA_BUCKET);
        setDoneMessage(ChatColor.GOLD + "Ben bu enerjiyi ürettim de, ne işime yarayacak ki? Diyorsan deme, bir sonraki görevde göstereceğim!");
    }

    @Override
    public void check(Player player) {
        Map<String, Long> energyData = EnergyData.getPlayerData(player);
        if (energyData.get("storedEnergy") >= 30)
            PlayerTaskController.doneTask(player, this);
        else
            player.sendMessage(Repo.getMSG("task-unsuccessful"));
    }
}
