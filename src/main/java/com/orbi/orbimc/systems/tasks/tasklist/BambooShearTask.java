package com.orbi.orbimc.systems.tasks.tasklist;

import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.systems.tasks.root.PlayerTask;
import com.orbi.orbimc.systems.tasks.root.PlayerTaskController;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BambooShearTask extends PlayerTask {

    public BambooShearTask() {
        setTaskName(ChatColor.GRAY + "1 adet toprak 'üret'!");
        setHowToComplete(ChatColor.GOLD + "4 adet meşe fidanı, 4 adet meşe yaprağı ve bir adet odun kömürü kullanarak toprak üret!(Ürettiğin toprağın envanterinde olması gerekli)");
        setTaskLevel(1);
        setTaskAward(new ItemStack(Material.OAK_SAPLING, 5), new ItemStack(Material.BONE, 16), new ItemStack(Material.CHARCOAL, 8));
        setRepresentative(Material.SHEARS);
        setDoneMessage(ChatColor.GOLD + "Tebrikler! Sunucuda buna benzer onlarca farklı üretim bulunmakta! Hadi bir kaçına daha göz atalım! Bambu makasın ile biraz ağaç yaprağı kır. (Toplamda 4 adet meşe yaprağın, 4 adet meşe fidanın ve bir adet odun kömürün olsun) daha sonra çalışma masasına tıkla ve oradan TOPRAK üret!");
    }

    @Override
    public void check(Player player) {
        for (ItemStack i : player.getInventory().getContents()) {
            if (i != null && i.getType().equals(Material.DIRT)) {
                PlayerTaskController.doneTask(player, this);
            }
        }
        player.sendMessage(Repo.getMSG("task-unsuccessful"));
    }

}
