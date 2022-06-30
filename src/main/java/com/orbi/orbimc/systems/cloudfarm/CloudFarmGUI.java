package com.orbi.orbimc.systems.cloudfarm;

import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.util.ItemBuilder;
import com.orbi.orbimc.util.LoreBuilder;
import com.orbi.orbimc.util.InventoryBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CloudFarmGUI {

    public static void createPanel(Player player) {
        try {
            Map<String, Integer> dataMap = CloudFarmData.getPlayerData(player.getUniqueId().toString());

            Inventory inv = InventoryBuilder.createCustomInventory(Repo.getText("cfs-panel-title"), InventoryType.PLAYER);

            int workerPower = dataMap.get("workerAmount") * (Repo.getConfig("cfs-base-crop-per-worker") + dataMap.get("workerEfficiency"));
            int dirtInfo = dataMap.get("dirtAmount") > workerPower ? workerPower : dataMap.get("dirtAmount");

            List<String> infoLore = new ArrayList<>();
            infoLore.add(String.format(Repo.getText("cfs-info-lore0"), dataMap.get(CloudFarmDataNames.dirtAmount), dataMap.get(CloudFarmDataNames.dirtLimit)));
            infoLore.add(String.format(Repo.getText("cfs-info-lore1"), dataMap.get(CloudFarmDataNames.workerAmount), dataMap.get(CloudFarmDataNames.workerLimit)));
            infoLore.add(String.format(Repo.getText("cfs-info-lore2"), dataMap.get(CloudFarmDataNames.workerAmount) * (dataMap.get(CloudFarmDataNames.workerEfficiency) + Repo.getConfig("cfs-base-crop-per-worker"))));
            infoLore.add(String.format(Repo.getText("cfs-info-lore3"), dirtInfo, Repo.getConfig("cfs-base-produce-time") - dataMap.get("dirtEfficiency") * Repo.getConfig("dirt-upgrade-multiplier")));
            infoLore.add(String.format(Repo.getText("cfs-info-lore4"), dataMap.get("storageLimit")));
            infoLore.add(String.format(Repo.getText("cfs-info-lore5"), 1 + dataMap.get(CloudFarmDataNames.dirtEfficiency) * 0.033));
            infoLore.add(String.format(Repo.getText("cfs-info-lore6"), 1 + dataMap.get(CloudFarmDataNames.workerEfficiency) * 0.1));

            List<String> buyDirtLore = createLore(dataMap, "cfs-panel-buy-dirt-lore0", PriceList.dirtAmount, CloudFarmDataNames.dirtAmount, 1);
            List<String> buyWorkerLore = createLore(dataMap, "cfs-panel-buy-worker-lore0", PriceList.workerAmount, CloudFarmDataNames.workerAmount, 1);
            List<String> changeItemLore = new LoreBuilder(Repo.getText("cfs-panel-change-plant-lore0")).build();
            List<String> storageLore = new LoreBuilder(String.format(Repo.getText("cfs-panel-storage-lore0"), CloudFarmData.calculateProduce(player.getUniqueId().toString()))).build();
            List<String> upgradeDirtLimitLore = createLore(dataMap, "cfs-panel-upgrade-dirt-limit-lore0", PriceList.dirtLimit, CloudFarmDataNames.dirtLimit, 1);
            List<String> upgradeWorkerLimitLore = createLore(dataMap, "cfs-panel-upgrade-worker-limit-lore0", PriceList.workerLimit, CloudFarmDataNames.workerLimit, 1);
            List<String> upgradeStorageLimitLore = createLore(dataMap, "cfs-panel-upgrade-storage-limit-lore0", PriceList.storageLimit, CloudFarmDataNames.storageLimit, 5);
            List<String> upgradeDirtEfficiencyLore = createLore(dataMap, "cfs-panel-upgrade-dirt-efficiency-lore0", PriceList.dirtEfficiency, CloudFarmDataNames.dirtEfficiency, true); //TODO _> burası patlıyo düzgün hesaplanmıyor %f yüzünden

            List<String> upgradeWorkerEfficiencyLore = new ArrayList<>();
            int wp = Repo.getConfig("cfs-base-crop-per-worker") + dataMap.get("workerEfficiency");
            upgradeWorkerEfficiencyLore.add(String.format(Repo.getText("cfs-panel-upgrade-worker-efficiency-lore0"), wp, wp + 1));
            upgradeWorkerEfficiencyLore.add(String.format(Repo.getText("cfs-panel-purchase-price-lore"), CloudFarmData.calculatePrice(PriceList.workerEfficiency, dataMap.get("workerEfficiency"))));

            String workerHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjQzNTJiOTc5YTQ4OWRmYzhiYTdiMWUxMjU5NzYzODQzZDc3MmYzOWYxNDIwZjg2NzI1MzdhYTc1MjJkYzFiZiJ9fX0=";

            inv.setItem(4, new ItemBuilder(Material.DIAMOND_HOE, Repo.getText("cfs-info-name"), infoLore).build());
            inv.setItem(10, new ItemBuilder(Material.GRASS_BLOCK, Repo.getText("cfs-panel-buy-dirt-name"), buyDirtLore).build());
            inv.setItem(12, new ItemBuilder(workerHead, Repo.getText("cfs-panel-buy-worker-name"), buyWorkerLore).build());
            inv.setItem(14, new ItemBuilder(Material.BAMBOO, Repo.getText("cfs-panel-change-plant-name"), changeItemLore).build());
            inv.setItem(16, new ItemBuilder(Material.CHEST, String.format(Repo.getText("cfs-panel-storage-name"), CloudFarmData.calculateProduce(player.getUniqueId().toString())), storageLore).build());

            String upgradeDirtLimitHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWEyYmYxNzQ3YTYxYmI3YzZkMDU3NmM4ZTMzZjE3MDMxNGNiYTBjYmViOTBiZDUwNzZlMWFiZDQyZTE5YjUxIn19fQ==";
            String upgradeWorkerLimitHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Y3Y2RlZWZjNmQzN2ZlY2FiNjc2YzU4NGJmNjIwODMyYWFhYzg1Mzc1ZTlmY2JmZjI3MzcyNDkyZDY5ZiJ9fX0=";
            String upgradeStorageLimitHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjUyMmVjNTcwOWVkOWQ1ZDE2MjRjNjc5ZThkM2UxNmM3ZjdjZmJiZGI3OGY4OTAzNjFiZjFmMmFhMDRiODNmNSJ9fX0=";
            String upgradeDirtEHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmQ0Mjk2YjMzM2QyNTMxOWFmM2YzMzA1MTc5N2Y5ZTZkODIxY2QxOWEwMTRmYjcxMzdiZWI4NmE0ZTllOTYifX19";
            String upgradeWorkerEHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZThiOGM2YTQ2ZDg3Y2Y4NmE1NWRmMjE0Y2Y4NGJmNDVjY2EyNWVkYjlhNjc2ZTk2MzY0ZGQ2YTZlZWEyMzViMyJ9fX0=";

            inv.setItem(29, new ItemBuilder(upgradeDirtLimitHead, Repo.getText("cfs-panel-upgrade-dirt-limit-name"), upgradeDirtLimitLore).build());
            inv.setItem(30, new ItemBuilder(upgradeWorkerLimitHead, Repo.getText("cfs-panel-upgrade-worker-limit-name"), upgradeWorkerLimitLore).build());
            inv.setItem(31, new ItemBuilder(upgradeStorageLimitHead, Repo.getText("cfs-panel-upgrade-storage-limit-name"), upgradeStorageLimitLore).build());
            inv.setItem(32, new ItemBuilder(upgradeDirtEHead, Repo.getText("cfs-panel-upgrade-dirt-efficiency-name"), upgradeDirtEfficiencyLore).build());
            inv.setItem(33, new ItemBuilder(upgradeWorkerEHead, Repo.getText("cfs-panel-upgrade-worker-efficiency-name"), upgradeWorkerEfficiencyLore).build());

            player.openInventory(inv);
        } catch (NullPointerException e) {
            //CloudFarmData.cratePlayerData(player);
            //createPanel(player, false);
            e.printStackTrace();
        }
    }

    public static void updateSingle(Player player) {
        createPanel(player);
    }

    public static List<String> createLore(Map<String, Integer> dataMap, String repoText, String configValue, String value, int multiplier) {
        List<String> lore = new ArrayList<>();
        lore.add(String.format(Repo.getText(repoText), dataMap.get(value), dataMap.get(value) + multiplier));
        lore.add(String.format(Repo.getText("cfs-panel-purchase-price-lore"), CloudFarmData.calculatePrice(configValue, dataMap.get(value))));
        return lore;
    }

    public static List<String> createLore(Map<String, Integer> dataMap, String repoText, String configValue, String value, boolean isCustom) {
        List<String> lore = new ArrayList<>();
        int baseProduceTime = Repo.getConfig("cfs-base-produce-time") - (dataMap.get(value) * Repo.getConfig("dirt-upgrade-multiplier"));
        lore.add(String.format(Repo.getText(repoText), baseProduceTime, baseProduceTime - Repo.getConfig("dirt-upgrade-multiplier")));
        lore.add(String.format(Repo.getText("cfs-panel-purchase-price-lore"), CloudFarmData.calculatePrice(configValue, dataMap.get(value))));
        return lore;
    }


}
