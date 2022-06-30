package com.orbi.orbimc.systems.fly;

import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.util.InventoryBuilder;
import com.orbi.orbimc.util.ItemBuilder;
import com.orbi.orbimc.util.StringParser;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FlyGUI {

    public static void createPanel(Player player) {
        Inventory inv = InventoryBuilder.createCustomInventory(Repo.getText("fs-title"), InventoryType.CHEST);

        inv.setItem(10, new ItemBuilder(Material.BOOK, Repo.getText("fs-get-info-name")).build());
        inv.setItem(13, new ItemBuilder(Material.ELYTRA, Repo.getText("fs-main-name-false"), FlyManager.flyingPlayers.containsKey(player)).build());
        inv.setItem(16, new ItemBuilder(Material.FIREWORK_ROCKET, StringParser.parse(Repo.getText("fs-storage-name"), FlyData.getPlayerData(player)), Collections.singletonList(Repo.getText("fs-storage-lore"))).build());

        player.openInventory(inv);
    }

    public static void createBuyPanel(Player player) {
        Inventory inv = InventoryBuilder.createCustomInventory(Repo.getText("fs-buy-title"), InventoryType.CHEST);
        Map<String, Integer> loreMap = FlyData.createPriceData();
        List<String> carbonLore = StringParser.parse(loreMap, Repo.getMultipleText("fs-buy-by-carbon-lore0", "fs-buy-by-carbon-lore1", "fs-buy-by-carbon-lore2"));
        List<String> rhodiumLore = StringParser.parse(loreMap, Repo.getMultipleText("fs-buy-by-rhodium-lore0", "fs-buy-by-rhodium-lore1"));
        inv.setItem(11, new ItemBuilder(Material.COAL, Repo.getText("fs-buy-by-carbon-name"), carbonLore).build());
        inv.setItem(15, new ItemBuilder(Material.NETHER_STAR, Repo.getText("fs-buy-by-rhodium-name"), rhodiumLore).build());
        player.openInventory(inv);
    }

}
