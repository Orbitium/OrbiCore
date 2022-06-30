package com.orbi.orbimc.systems.generator;

import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.systems.energy.EnergyData;
import com.orbi.orbimc.util.InventoryBuilder;
import com.orbi.orbimc.util.ItemBuilder;
import com.orbi.orbimc.util.StringParser;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.Map;

public class GeneratorGUI {

    public static void createPanel(Player player) {
        try {
            Inventory inv = InventoryBuilder.createCustomInventory(Repo.getText("generator-panel-title"), 54);
            Map<String, Double> playerData = GeneratorData.getPlayerData(player);
            playerData.put("availableStonePoint", GeneratorPlayerManager.cobbleData.get(player).availableStonePoint);
            Map<String, Double> loreData = GeneratorData.createLore(playerData);

            String generatorHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2Y5ZjM1NmY1ZmU3ZDFiYzkyY2RkZmFlYmEzZWU3NzNhYzlkZjFjYzRkMWMyZjhmZTVmNDcwMTMwMzJjNTUxZCJ9fX0=";
            String speHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjZlYmMzMGFhMmVkZmExOTkxYTViYTc3ZTJmMmNiOWQ3Mzk4ZDM3NWJlOTliMWMxZWZmN2FlZjJkZGRmNzM5OSJ9fX0=";
            String energyHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzc0MDBlYTE5ZGJkODRmNzVjMzlhZDY4MjNhYzRlZjc4NmYzOWY0OGZjNmY4NDYwMjM2NmFjMjliODM3NDIyIn19fQ==";
            String spHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDU0ZDljNDg4YzNmYmRlNTQ1NGUzODYxOWY5Y2M1YjViYThjNmMwMTg2ZjhhYTFkYTYwOTAwZmNiYzNlYTYifX19";

            List<String> generatorLore = StringParser.parse(loreData, Repo.getMultipleText("generator-info-lore1", "generator-info-lore2", "generator-info-lore3"));
            List<String> upgradeSpeLore = StringParser.parse(loreData, Repo.getMultipleText("upgrade-spe-lore0", "upgrade-spe-lore1"));
            List<String> upgradeEnergyLore = StringParser.parse(loreData, Repo.getMultipleText("upgrade-energy-cost-lore0", "upgrade-energy-cost-lore1"));
            List<String> upgradeSpLore = StringParser.parse(loreData, Repo.getMultipleText("upgrade-sp-lore0", "upgrade-sp-lore1"));

            inv.setItem(4, new ItemBuilder(generatorHead, Repo.getText("generator-info-name"), generatorLore).build());
            inv.setItem(12, new ItemBuilder(speHead, Repo.getText("upgrade-spe-name"), upgradeSpeLore).build());
            inv.setItem(13, new ItemBuilder(energyHead, Repo.getText("upgrade-energy-cost-name"), upgradeEnergyLore).build());
            inv.setItem(14, new ItemBuilder(spHead, Repo.getText("upgrade-sp-name"), upgradeSpLore).build());

            double availableStonePoint = playerData.get("availableStonePoint");
            boolean turn = playerData.get("energyUsage").toString().equals("1.0");
            inv.setItem(31, new ItemBuilder(Material.COBBLESTONE, StringParser.parse(Repo.getText("gs-available-stone-point"), availableStonePoint), Repo.getText("gs-turn-" + turn)).build());
            String needText = Repo.getText("gs-need-stone-point");
            inv.setItem(37, new ItemBuilder(Material.COAL, StringParser.parse(needText, Repo.getConfig("gs-coal-need")), true, true).build());
            inv.setItem(38, new ItemBuilder(Material.COPPER_INGOT, StringParser.parse(needText, Repo.getConfig("gs-copper-need")), true, true).build());
            inv.setItem(39, new ItemBuilder(Material.IRON_INGOT, StringParser.parse(needText, Repo.getConfig("gs-iron-need")), true, true).build());
            inv.setItem(40, new ItemBuilder(Material.GOLD_INGOT, StringParser.parse(needText, Repo.getConfig("gs-gold-need")), true, true).build());
            inv.setItem(41, new ItemBuilder(Material.LAPIS_LAZULI, StringParser.parse(needText, Repo.getConfig("gs-lapis-need")), true, true).build());
            inv.setItem(42, new ItemBuilder(Material.REDSTONE, StringParser.parse(needText, Repo.getConfig("gs-redstone-need")), true, true).build());
            inv.setItem(43, new ItemBuilder(Material.NETHERITE_INGOT, StringParser.parse(needText, Repo.getConfig("gs-netherite-need")), true, true).build());
            player.openInventory(inv);
        } catch (NullPointerException ex) {
            GeneratorPlayerManager.cobbleData.put(player, new GeneratorPlayerData(player, EnergyData.getPlayerData(player).get("storedEnergy")));
            createPanel(player);
        }
    }

    /*

    Yükseltmeler:
        Her ore için gerken kırma miktarını 5 azalt
        Her kırma işlemi için gerekli enerjiyi 1 w azalt
        Her üretimi 1 arttır(1 yerine 2 demir gelsin gibisinden)

     */

}
