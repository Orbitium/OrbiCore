package com.orbi.orbimc.systems.energy;

import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.math.ConvertType;
import com.orbi.orbimc.math.Converter;
import com.orbi.orbimc.util.InventoryBuilder;
import com.orbi.orbimc.util.ItemBuilder;
import com.orbi.orbimc.util.StringParser;
import com.orbi.orbimc.util.TimeController;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.*; //bitti diye tahmin ediyorum

public class EnergyGUI {

    public static void createPanel(Player player) {
        Inventory inv = InventoryBuilder.createCustomInventory(Repo.getText("ens-panel-title"), InventoryType.CHEST);
        Map<String, Long> playerData = EnergyData.getPlayerData(player);

        String offFurnaceHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTgzMDZiZjhlZTQwMzZhNmZkYjQ4NDE2NzBiMzE3Mjg5NDg1MDMxYjU2NTQ1ZDNkMWE1YzBlNTc0ZWNmZDFkNCJ9fX0=";
        inv.setItem(4, new ItemBuilder(offFurnaceHead, Repo.getText("esn-start-production-name"), Collections.singletonList(Repo.getText("esn-star-production-lore0"))).build());

        String onFurnaceHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWZiYjZlZWE1NzU0N2IyNTg4YmFmOGFmNjQ5ZDkxMTZmZjA4Y2FjNTZkZDIxNDBiM2M0OTU3Nzc5OWJhZDdjIn19fQ==";
        if (TimeController.calculateTimeSpacing(playerData.get("timeValue")) < 0) {
            List<String> lore = Arrays.asList(StringParser.parse(Repo.getText("esn-processing-lore0"), Math.abs(TimeController.calculateTimeSpacing(playerData.get("timeValue")))), Repo.getText("esn-processing-lore1"));
            inv.setItem(4, new ItemBuilder(onFurnaceHead, Repo.getText("esn-processing-name"), lore).build());
            player.openInventory(inv);
        } else if (playerData.get("timeValue") != -1)
            EnergyCalculation.calculateProduction(player, playerData);

        String storageHead = " eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzgyNjYyYTE5OWNhMjk0NzlmZmVhOWRlODJjYTYzMDVlZjVjMmI1ZDQ3OGFjNzYyNmE0MjA0NjViYzY5YzViIn19fQ==";
        String efficiencyHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjU2NjM0YjU1NmNhZjUzODJkZTY1MDM4YTEwZTRkNzljN2MxODY5NTA0ODU5OWRmNzRmOWM2N2MxZTFlODczNiJ9fX0=";
        String batteryHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTEzNjFlNTc2YjQ5M2NiZmRmYWUzMjg2NjFjZWRkMWFkZDU1ZmFiNGU1ZWI0MThiOTJjZWJmNjI3NWY4YmI0In19fQ==";
        String storedEnergyHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTE2YjY4Mzc2YzE4MWM5YTczNDNmZWUzNjk3ZmFhY2VjMzUxMjlmYjY0ZGU1OTE0YmRiZjg2OWM2NTJjIn19fQ==";
        Map<String, String> loreDataMap = EnergyData.createUpgradeLoreData(playerData);

        List<String> storageLore = StringParser.parse(loreDataMap, Repo.getMultipleText("esn-fuel-storage-level-lore0", "esn-fuel-storage-level-lore1"));
        List<String> efficiencyLore = StringParser.parse(loreDataMap, Repo.getMultipleText("esn-efficiency-lore0", "esn-efficiency-lore1"));
        List<String> batteryLore = StringParser.parse(loreDataMap, Repo.getMultipleText("esn-battery-level-lore0", "esn-battery-level-lore1"));
        List<String> storedEnergyLore = Collections.singletonList(StringParser.parse(Repo.getText("ens-stored-energy-lore"), Converter.formatValue(playerData.get("batteryCapacity"), ConvertType.ENERGY)));
        inv.setItem(10, new ItemBuilder(Material.COAL, StringParser.parse(Repo.getText("esn-fuel-storage-name"), playerData), Collections.singletonList(Repo.getText("esn-fuel-storage-lore0"))).build());
        inv.setItem(12, new ItemBuilder(storageHead, Repo.getText("esn-fuel-storage-level-name"), storageLore).build());
        inv.setItem(13, new ItemBuilder(efficiencyHead, Repo.getText("esn-efficiency-name"), efficiencyLore).build());
        inv.setItem(14, new ItemBuilder(batteryHead, Repo.getText("esn-battery-level-name"), batteryLore).build());
        inv.setItem(16, new ItemBuilder(storedEnergyHead, StringParser.parse(Repo.getText("ens-stored-energy-name"), Converter.formatValue(playerData.get("storedEnergy"), ConvertType.ENERGY)), storedEnergyLore).build());
        player.openInventory(inv);
    }

    public static void addCoalPanel(Player player) {
        Inventory inv = InventoryBuilder.createPanel1(Repo.getText("esn-add-coal-panel"));
        inv.setItem(49, new ItemBuilder(Material.COAL_BLOCK, Repo.getText("esn-add-coal-name")).build());
        player.openInventory(inv);
    }

}
