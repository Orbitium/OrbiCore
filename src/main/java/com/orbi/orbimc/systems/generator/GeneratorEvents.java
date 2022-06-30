package com.orbi.orbimc.systems.generator;

import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.listeners.root.InventoryEvent;
import com.orbi.orbimc.systems.energy.EnergyData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GeneratorEvents {

    public static void onBreakStone(BlockBreakEvent e) {
        GeneratorPlayerManager.updatePlayerData(e);
    }

    @InventoryEvent(title = "generator-panel-title")
    public static void onClickInventory(InventoryClickEvent e) {
        e.setCancelled(true);
        Player player = (Player) e.getWhoClicked();
        switch (e.getSlot()) {
            case 12:
            case 13:
            case 14:
                e.getWhoClicked().sendMessage(ChatColor.GRAY + "Bu özellik henüz aktifleştirilmedi! 1.5 yaması ile aktifleştirilecek!");
                break;
            case 31:
                if (GeneratorPlayerManager.cobbleData.get(player).isEnergyOn) {
                    player.sendMessage(Repo.getMSG("gs-turn-true"));
                    GeneratorPlayerManager.cobbleData.get(player).isEnergyOn = false;
                } else {
                    if (EnergyData.getPlayerData(player).get("storedEnergy") >= GeneratorPlayerManager.cobbleData.get(player).energyCost) {
                        GeneratorPlayerManager.cobbleData.get(player).isEnergyOn = true;
                        player.sendMessage(Repo.getMSG("gs-turn-false"));
                    } else player.sendMessage(Repo.getMSG("esn-insufficient-energy"));
                }
                e.getWhoClicked().closeInventory();
                break;
            case 37:
                GeneratorProduce.produce(player, Material.COAL, Repo.getConfig("gs-coal-need"));
                break;
            case 38:
                GeneratorProduce.produce(player, Material.COPPER_INGOT, Repo.getConfig("gs-copper-need"));
                break;
            case 39:
                GeneratorProduce.produce(player, Material.IRON_INGOT, Repo.getConfig("gs-iron-need"));
                break;
            case 40:
                GeneratorProduce.produce(player, Material.GOLD_INGOT, Repo.getConfig("gs-gold-need"));
                break;
            case 41:
                GeneratorProduce.produce(player, Material.LAPIS_LAZULI, Repo.getConfig("gs-lapis-need"));
                break;
            case 42:
                GeneratorProduce.produce(player, Material.REDSTONE, Repo.getConfig("gs-redstone-need"));
                break;
            case 43:
                GeneratorProduce.produce(player, Material.NETHERITE_INGOT, Repo.getConfig("gs-netherite-need"));
                break;
        }

    }
}
