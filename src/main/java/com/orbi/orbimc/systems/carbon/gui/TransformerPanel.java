package com.orbi.orbimc.systems.carbon.gui;

import com.orbi.orbimc.database.Cache;
import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.systems.carbon.CarbonData;
import com.orbi.orbimc.util.InfoBuilder;
import com.orbi.orbimc.systems.carbon.CarbonData.CarbonItem;
import com.orbi.orbimc.util.StaticItems;
import com.orbi.orbimc.util.inventory.*;
import com.orbi.orbimc.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Map;

public class TransformerPanel implements Inventory {

    @Override
    public void initializeInventory() {

        InventoryBuilder2 inventory = new InventoryBuilder2(Pattern.FILL, 54, "transformer-title");
        inventory.setItem(0, new ItemBuilder(Material.CHEST, Repo.getText("transformer-open-composter-menu-name")));
        inventory.setItem(4, StaticItems.infoItem);
        inventory.setItem(8, new ItemBuilder(Material.EMERALD, Repo.getText("transformer-compos-all-items")));

        for (Map.Entry<String, CarbonItem> entry : CarbonData.itemToCarbonValues.entrySet()) {
            CarbonItem carbonItem = entry.getValue();
            inventory.setItem(carbonItem.getSlot(), carbonItem.build(Material.getMaterial(entry.getKey())));
        }

        inventory.setEvent(0, e -> InventoryManager.open(e.getWhoClicked(), "composter-title"));
        inventory.setEvent(4, e -> InfoBuilder.buildInfo(e.getWhoClicked(), "transformer-info"));

        inventory.build();

    }

    /*
    /dönüştürücü -> dönüştürücü panelini açar, dönüştürücü panelinde her şeyi ayrıştır butonu bulunur ve ayrıştırıcı kutusu gibi de bir şey olur
    /dönüştür -> oyuncunun elindeki eşyaları dönüştürür
    /dönüştür * -> tüm envanterini dönüştürür
     */
}
