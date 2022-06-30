package com.orbi.orbimc.systems.tasks.tasklist;

import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.systems.customcraft.RecipeController;
import com.orbi.orbimc.systems.tasks.root.PlayerTask;
import com.orbi.orbimc.systems.tasks.root.PlayerTaskController;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CarbonTask extends PlayerTask {

    public CarbonTask() {
        setTaskName(ChatColor.GRAY + "15 Karbona sahip ol!");
        setHowToComplete(ChatColor.GRAY + "/ayrıştırıcı paneli üzerinde bambularınızı karbona dönüştürebilirsiniz." +
                " Eşyalarınızı karbona dönüştürmek için /ayrıştırıcı ayrıştır yazarak açılan pencereye bambularınızı koyun ve nether yıldızına basın." +
                ChatColor.GOLD + " Dikkat, tüm bambularınızı dönüştürürseniz üretim yapamazsınız. Bir miktar bambuyu büyümeleri için ekin.");
        setTaskLevel(0);
        setTaskAward(new ItemStack(Material.BAMBOO, 32), new ItemStack(Material.CLAY_BALL, 2));
        setRepresentative(Material.COAL);
        setDoneMessage(ChatColor.GOLD + "Görev tamamlandı! İşte sana 'bambu makası' üretmen için gerekli malzemeleri veriyorum!" +
                " 'Bambu makası'nı nasıl üreteceğini bilmiyorsun değil mi? Çalışma masasını aç," +
                " sol tarafta bulunan kitaba tıkla. Orada bambu makasını görebilirsin!");
    }

    @Override
    public void check(Player player) {
        /*if (CarbonData.getCarbon(player) >= 15) {
            PlayerTaskController.doneTask(player, this);
            player.discoverRecipes(RecipeController.recipes);
        } else {
            player.sendMessage(Repo.getMSG("task-unsuccessful"));
        }

         */
    }
}
