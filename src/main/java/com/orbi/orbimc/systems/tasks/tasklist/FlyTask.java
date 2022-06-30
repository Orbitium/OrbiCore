package com.orbi.orbimc.systems.tasks.tasklist;

import com.orbi.orbimc.database.MongoBase;
import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.systems.fly.FlyData;
import com.orbi.orbimc.systems.fly.FlyManager;
import com.orbi.orbimc.systems.tasks.root.PlayerTask;
import com.orbi.orbimc.systems.tasks.root.PlayerTaskController;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Map;

public class FlyTask extends PlayerTask {

    public FlyTask() {
        ItemStack f = new ItemStack(Material.FIREWORK_ROCKET, 16);
        FireworkMeta fm = (FireworkMeta) f.getItemMeta();
        fm.addEffect(FireworkEffect.builder().withColor(Color.PURPLE, Color.RED, Color.BLACK).withFlicker().build());
        fm.setPower(2);
        f.setItemMeta(fm);
        setTaskName(ChatColor.GRAY + "Aşağıda kimse kalmasın!");
        setHowToComplete(ChatColor.GOLD + "Uçuş sistemi, uçuş puanı harcayarak uçmanızı sağlar. /uçuş yazıp, açılan paneldeki elitraya basarak uçuş modunuzu açıp/kapatabilirsiniz. Bu görevi tamamlamak için 120 uçuş puanı harcayın ve uçuş modunuzu kapatın!");
        setTaskLevel(3);
        setTaskAward(new ItemStack(Material.CHAINMAIL_BOOTS), f, new ItemStack(Material.SAND, 25), new ItemStack(Material.IRON_HOE), new ItemStack(Material.OAK_LOG, 16));
        setRepresentative(Material.ELYTRA);
        setDoneMessage(ChatColor.GOLD + "Vın vınn! Uçuş puanın 350 olarak düzenlendi, kaktüs gibi farmlar yaparken lazım olabilir!" +
                " Ayrıca uçuş puanını /oy vererek de kazanabilirsin! Sana sunucudaki EN GÜÇLÜ birimi göstermek istiyorum, diğer görevde tabii.");
    }

    @Override
    public void check(Player player) {
        if (FlyData.getPlayerData(player) <= 180 || !FlyManager.flyingPlayers.containsKey(player)) {
            MongoBase.setValue(player, "availableFlyTime", 350);
            PlayerTaskController.doneTask(player, this);
        } else
            player.sendMessage(Repo.getMSG("task-unsuccessful"));
    }
}
