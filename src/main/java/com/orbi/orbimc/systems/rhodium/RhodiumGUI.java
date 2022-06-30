package com.orbi.orbimc.systems.rhodium;

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

public class RhodiumGUI {

    public static void createPanel(Player player) {
        Inventory inv = InventoryBuilder.createCustomInventory(Repo.getText("rs-panel-title"), InventoryType.CHEST);
        List<String> lore = Collections.singletonList(StringParser.parse(Repo.getText("rs-production-lore"), RhodiumData.calculateRhodiumProduction(player)));
        inv.setItem(13, new ItemBuilder(Material.NETHER_STAR, StringParser.parse(Repo.getText("rs-production-name"), RhodiumData.getRhodium(player)), lore).build());
        player.openInventory(inv);
    }

}
