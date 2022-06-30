package com.orbi.orbimc.systems.tasks.tasklist;

import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.systems.generator.GeneratorPlayerManager;
import com.orbi.orbimc.systems.playeritem.PlayerItemData;
import com.orbi.orbimc.systems.tasks.root.PlayerTask;
import com.orbi.orbimc.systems.tasks.root.PlayerTaskController;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class StorageTask extends PlayerTask {

    public StorageTask() {
        setTaskName(ChatColor.GRAY + "Saklamak güzeldir!");
        setHowToComplete(ChatColor.GRAY + "/depo 'nuza 10 odun ve 10 taş koyun. Nasıl koyacağınızı bilmiyor musunuz, anlatım: " +
                "/depo yazdığınızda açılan panel üzerindeki sandığa tıklayın. Sandığın açtğı panel üzerine depoya taşınmasını " +
                "istediğiniz eşyaları koyun ve nether yıldızına basın. DİKKAT, depoya koyduğunuz eşyaları geri alamazsınız " +
                "sebebini görevi bitirdiğinizde öğreneceksiniz.");
        setTaskLevel(7);
        setTaskAward(new ItemStack(Material.OAK_WOOD, 128), new ItemStack(Material.STONE, 128), new ItemStack(Material.GOLD_BLOCK, 3));
        setRepresentative(Material.CHEST);
        setDoneMessage(ChatColor.GOLD + "Görev başarılı! Depoya koyduğunuz eşyaları geri alamayacaksanız, deponun ne anlamı var ki?" +
                " Deponun anlamı diğer sistemlerinizi yükseltmek veya tamir etmektir. 'Ne tamiri, ne sistemi' dediğini duyar gibiyim. " +
                "Sonraki görevi tamamladığında sorularının cevabını bulmuş olacaksın!");
    }

    @Override
    public void check(Player player) {
        Map<String, Integer> playerData = PlayerItemData.getPlayerData(player);
        if (playerData.get("woodAmount") >= 10 && playerData.get("stoneAmount") >= 10) {
            PlayerTaskController.doneTask(player, this);
        } else
            player.sendMessage(Repo.getMSG("task-unsuccessful"));
    }
}