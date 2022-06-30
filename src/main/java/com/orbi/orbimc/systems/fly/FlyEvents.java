package com.orbi.orbimc.systems.fly;

import com.orbi.orbimc.database.MongoBase;
import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.listeners.root.InventoryEvent;
import com.orbi.orbimc.util.StringParser;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;

public class FlyEvents {

    @InventoryEvent(title = "fs-title")
    public static void onClickMainPanel(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        e.setCancelled(true);
        switch (e.getSlot()) {
            case 13:
                if (!e.getCurrentItem().getItemMeta().hasItemFlag(ItemFlag.HIDE_ENCHANTS))
                    FlyManager.addPlayer(player);
                else
                    FlyManager.removePlayer(player, false);
                break;
            case 10:
                player.sendMessage(ChatColor.AQUA + "Bilgi için discord kanalını ziyaret edin!");
                break;
            case 16:
                FlyGUI.createBuyPanel(player);
                break;
        }
    }

    @InventoryEvent(title = "fs-buy-title")
    public static void onClickBuyPanel(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        e.setCancelled(true);
        switch (e.getSlot()) {
            case 11:
                int carbonMultiplier = FlyData.getMultiplier(e.getClick(), "fs-buy-multiplier-by-carbon");
                int carbonPrice = Repo.getConfig("fs-buy-price-by-carbon") * carbonMultiplier;
                if (FlyData.checkMoney(player, carbonPrice, "availableCarbon")) {
                    player.sendMessage(StringParser.parse(Repo.getMSG("fs-success-buy"), carbonMultiplier));
                    if (FlyManager.flyingPlayers.containsKey(player))
                        FlyManager.flyingPlayers.put(player, FlyManager.flyingPlayers.get(player) + carbonMultiplier);
                    else
                        MongoBase.setValue(player, "availableFlyTime", (int) MongoBase.getValue(player, "availableFlyTime") + carbonMultiplier);
                } else {
                    player.sendMessage(Repo.getMSG("cs-insufficient-carbon"));
                    player.closeInventory();
                }
                break;
            case 15:
                int rhodiumMultiplier = FlyData.getMultiplier(e.getClick(), "fs-buy-multiplier-by-rhodium");
                int rhodiumPrice = Repo.getConfig("fs-buy-price-by-rhodium") * (rhodiumMultiplier / Repo.getConfig("fs-buy-multiplier-by-rhodium"));
                if (FlyData.checkMoney(player, rhodiumPrice, "availableRhodium")) {
                    player.sendMessage(StringParser.parse(Repo.getMSG("fs-success-buy"), rhodiumMultiplier));
                    if (FlyManager.flyingPlayers.containsKey(player))
                        FlyManager.flyingPlayers.put(player, FlyManager.flyingPlayers.get(player) + rhodiumMultiplier);
                    else
                        MongoBase.setValue(player, "availableFlyTime", (int) MongoBase.getValue(player, "availableFlyTime") + rhodiumMultiplier);

                } else {
                    player.sendMessage(Repo.getMSG("rs-insufficient-rhodium"));
                    player.closeInventory();
                }
                break;
        }
    }

}
