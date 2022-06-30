package com.orbi.orbimc.deletable;

import com.orbi.orbimc.deletable.ItemMainProgress;
import com.orbi.orbimc.deletable.ItemSubProgress;
import com.orbi.orbimc.item.ItemKeys;
import com.orbi.orbimc.util.Color;
import com.orbi.orbimc.util.ItemBuilder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemProgressController {

    public static void check(Object p, ItemStack i, ItemMainProgress it) {
        ItemMeta im = i.getItemMeta();
        PersistentDataContainer pdc = im.getPersistentDataContainer();

        if (!pdc.has(ItemKeys.progressID, PersistentDataType.INTEGER)) {

            pdc.set(ItemKeys.progressID, PersistentDataType.INTEGER, it.getProgressID());
            pdc.set(ItemKeys.progressXPNeed, PersistentDataType.INTEGER, it.getNeedXP());
            pdc.set(ItemKeys.progressXPAdvance, PersistentDataType.INTEGER, 1);
            pdc.set(ItemKeys.isMainProgress, PersistentDataType.INTEGER, 1);

            String coloredCondition = Color.translateHex(it.getConditionText()) + 1;
            saveItem(i, im, coloredCondition);
            return;
        } else {
            if (pdc.has(ItemKeys.isMainProgress, PersistentDataType.INTEGER)) { //Eğer ana progress ise
                ItemMainProgress itemProgress = ItemMainProgress.getByID(pdc.get(ItemKeys.progressID, PersistentDataType.INTEGER));

                int progressXPAdvance = pdc.get(ItemKeys.progressXPAdvance, PersistentDataType.INTEGER) + 1;

                if (itemProgress.getAntiProgressID() != it.getProgressID()) {
                    int progressXPNeed = pdc.get(ItemKeys.progressXPNeed, PersistentDataType.INTEGER);

                    if (progressXPAdvance >= progressXPNeed) {
                        pdc.remove(ItemKeys.isMainProgress);
                        pdc.remove(ItemKeys.progressXPNeed);
                        pdc.remove(ItemKeys.progressXPAdvance);

                        // Özellikler kaydedildi
                        List<String> newLore = new ArrayList<>();
                        for (ItemSubProgress sb : itemProgress.getSubProgresses()) {
                            newLore.add(sb.lore());
                            sb.registerKeys(pdc);
                        }
                        saveItem(i, im, newLore);
                    } else {
                        pdc.set(ItemKeys.progressXPAdvance, PersistentDataType.INTEGER, progressXPAdvance);

                        String coloredCondition = Color.translateHex(it.getConditionText()) + progressXPAdvance;
                        saveItem(i, im, coloredCondition);
                    }
                } else {
                    progressXPAdvance -= 2;
                    if (progressXPAdvance <= 0) {
                        pdc.remove(ItemKeys.progressID);
                        saveItem(i, im, "");
                    } else {
                        pdc.set(ItemKeys.progressXPAdvance, PersistentDataType.INTEGER, progressXPAdvance);
                        String lore = Color.translateHex(itemProgress.getConditionText()) + progressXPAdvance;
                        saveItem(i, im, lore);
                    }
                }
                return;
            }
        }
        ItemMainProgress itemProgress = ItemMainProgress.getByID(pdc.get(ItemKeys.progressID, PersistentDataType.INTEGER));

        if (itemProgress.getAntiProgressID() != it.getProgressID())
            run(p, pdc);
    }

    public static void run(Object p, PersistentDataContainer pdc) {
        ItemMainProgress itemProgress = ItemMainProgress.getByID(pdc.get(ItemKeys.progressID, PersistentDataType.INTEGER));
        itemProgress.runSubProgresses(p);
    }

    public static void saveItem(ItemStack i, ItemMeta im, String lore) {
        i.setItemMeta(im);
        ItemBuilder ib = new ItemBuilder(i);
        List<String> list = lore.equals("") ? null : Collections.singletonList(lore);
        ib.setLore(list);
        ib.build();
    }

    public static void saveItem(ItemStack i, ItemMeta im, List<String> lore) {
        i.setItemMeta(im);
        ItemBuilder ib = new ItemBuilder(i);
        ib.setLore(lore);
        ib.build();
    }

}
