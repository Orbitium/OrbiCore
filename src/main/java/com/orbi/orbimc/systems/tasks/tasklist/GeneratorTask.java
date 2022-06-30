package com.orbi.orbimc.systems.tasks.tasklist;

import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.systems.generator.GeneratorPlayerManager;
import com.orbi.orbimc.systems.tasks.root.PlayerTask;
import com.orbi.orbimc.systems.tasks.root.PlayerTaskController;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GeneratorTask extends PlayerTask {

    public GeneratorTask() {
        setTaskName(ChatColor.GRAY + "Taşların derinlerindekiler...");
        setHowToComplete(ChatColor.GRAY + "Jeneratör üzerinde 20 taş puanına sahip ol. Taş puanı nasıl kasılır bilmiyorsan, anlatayım: " +
                "taş puanı kazanmanın iki yolu vardır; birinci yol enerji harcamadan yavaaaş bir şekilde taş puanı kazanmaktır. Diğer yol " +
                "ise enerji harcayarak çoook daha fazla taş puanı kazanmaktır. /jeneratör paneli üzerindeki taşa tıklayarak enerji kullanarak " +
                "yapacağın taş puanı üretimini açıp/kapatabilirsin. Mevcut taş puanını da, fareni panelde bulunan kırık taşın üzerine " +
                "getirdiğinde görebilirsin.");
        setTaskLevel(6);
        setTaskAward(new ItemStack(Material.IRON_INGOT, 6), new ItemStack(Material.COAL, 128), new ItemStack(Material.DIRT, 20));
        setRepresentative(Material.IRON_INGOT);
        setDoneMessage(ChatColor.GOLD + "Görev başarılı! Biraz daha taş puanı üreterek demir gibi madenleri buradan üretebilirsin!" +
                " Madenleri ne mi yapcaksın? Onu da göstereyim, bir sonraki görevde!");
    }

    @Override
    public void check(Player player) {
        if (GeneratorPlayerManager.cobbleData.containsKey(player) && GeneratorPlayerManager.cobbleData.get(player).availableStonePoint >= 20) {
            PlayerTaskController.doneTask(player, this);
        } else
            player.sendMessage(Repo.getMSG("task-unsuccessful"));
    }
}