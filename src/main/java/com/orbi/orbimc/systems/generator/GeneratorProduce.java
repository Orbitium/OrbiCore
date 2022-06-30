package com.orbi.orbimc.systems.generator;

import com.orbi.orbimc.database.Repo;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GeneratorProduce {

    public static void produce(Player player, Material material, int price) {
        double availableStonePoint = GeneratorPlayerManager.cobbleData.get(player).availableStonePoint;
        if (availableStonePoint <= price) {
            player.sendMessage(Repo.getMSG("gs-insufficient-sp"));
            return;
        }
        GeneratorPlayerManager.cobbleData.get(player).availableStonePoint = availableStonePoint - price;
        player.getInventory().addItem(new ItemStack(material));
        player.sendMessage(Repo.getMSG("gs-success-produce"));
        GeneratorGUI.createPanel(player);
    }

}
