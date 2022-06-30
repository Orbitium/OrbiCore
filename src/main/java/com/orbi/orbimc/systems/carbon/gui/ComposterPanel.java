package com.orbi.orbimc.systems.carbon.gui;

import com.orbi.orbimc.database.Cache;
import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.systems.carbon.CarbonData;
import com.orbi.orbimc.util.InfoBuilder;
import com.orbi.orbimc.util.ItemBuilder;
import com.orbi.orbimc.util.LoreBuilder;
import com.orbi.orbimc.util.StaticItems;
import com.orbi.orbimc.util.inventory.Inventory;
import com.orbi.orbimc.util.inventory.InventoryBuilder2;
import com.orbi.orbimc.util.inventory.Pattern;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ComposterPanel implements Inventory {

    @Override
    public void initializeInventory() {
        InventoryBuilder2 inv = new InventoryBuilder2(Pattern.FILL, 27, "composter-title");
        inv.setOpenEvent(e -> {
            inv.setItem(4, new ItemBuilder(Material.NETHER_STAR, Repo.getText("composter-total-earn"),
                    new LoreBuilder(Repo.getMultipleText("composter-lore0", "composter-lore1")).build()));
            inv.setItem(10, StaticItems.negative);
        });
        inv.setItem(16, StaticItems.positive);
        inv.setItem(22, StaticItems.infoItem);

        for (Map.Entry<String, CarbonData.CarbonItem> entry : CarbonData.itemToCarbonValues.entrySet()) {
            CarbonData.CarbonItem carbonItem = entry.getValue();
            Material material = Material.getMaterial(entry.getKey());

            inv.setPEvent(material, e -> {
                ItemStack negativeItem = e.getView().getTopInventory().getItem(10);
                List<String> content = negativeItem.getItemMeta().getLore() == null ? new ArrayList<>(Collections.singletonList("Ayrıştırılacak eşyalar:")) :
                        negativeItem.getItemMeta().getLore();
                int itemAmount = e.getCurrentItem().getAmount();
                int multiplier;
                if (e.getClick() == ClickType.RIGHT) {
                    multiplier = itemAmount / carbonItem.getAmount();
                    e.setCancelled(true);
                } else {
                    if (itemAmount >= carbonItem.getAmount()) {
                        multiplier = 1;
                        e.setCancelled(true);
                    } else {
                        e.getWhoClicked().sendMessage("yetersiz eşya sayısı");
                        return;
                    }
                }
                e.getCurrentItem().setAmount(e.getCurrentItem().getAmount() - multiplier * carbonItem.getAmount());

                int index = 0;
                for (String s : content) {
                    if (s.contains(carbonItem.getFriendlyName())) {
                        String[] sContent = content.get(index).split("x");
                        content.set(index, (Integer.parseInt(sContent[0]) + multiplier * carbonItem.getAmount()) + "x" + sContent[1]);
                        index = -1;
                        break;
                    }
                    index++;
                }
                if (index != -1)
                    content.add(multiplier * carbonItem.getAmount() + "x " + carbonItem.getFriendlyName());

                ItemBuilder carbonEarn = new ItemBuilder(e.getView().getTopInventory().getItem(4));
                List<String> lore = carbonEarn.getLore();
                String[] spLore1 = lore.get(0).split(":");
                String[] spLore2 = lore.get(1).split(":");
                lore.set(0, spLore1[0] + ": " + (Integer.parseInt(spLore1[1].substring(1)) + carbonItem.getContents() * multiplier));
                lore.set(1, spLore2[0] + ": " + (Integer.parseInt(
                        spLore2[1].substring(1, spLore2[1].length() - 5)) + carbonItem.getEnergyCost() * multiplier) + " watt");
                carbonEarn.setLore(lore);
                e.getView().getTopInventory().setItem(4, carbonEarn.build());

                ItemMeta im1 = negativeItem.getItemMeta();
                im1.setLore(content);
                negativeItem.setItemMeta(im1);
                e.getView().getTopInventory().setItem(10, negativeItem);
            });
        }

        inv.setEvent(10, e -> {
            ItemBuilder negativeItem = new ItemBuilder(e.getView().getTopInventory().getItem(4));
            List<String> lore = negativeItem.getLore();
            if (lore != null) {
                Player player = (Player) e.getWhoClicked();
                String es = lore.get(1).split(":")[1];
                long energyCost = Long.parseLong(es.substring(1, es.length() - 5));
                if (energyCost <= Cache.getAsLong(player, "storedEnergy")) {
                    String[] spL = lore.get(0).split(":");
                    long carbon = Long.parseLong(spL[1].substring(1));
                    Cache.increaseLongValue(player, "availableCarbon", carbon);
                    Cache.decreaseLongValue(player, "storedEnergy", energyCost);
                    player.sendMessage("İşlem başarılı");
                    negativeItem.setLore(null);
                    e.getView().getTopInventory().setItem(10, negativeItem.build());
                    player.closeInventory();
                } else {
                    e.getWhoClicked().sendMessage("Yetersiz enerji");
                    e.getWhoClicked().closeInventory();
                }
            }
        });

        inv.setEvent(16, e -> {
            e.getWhoClicked().closeInventory();
            e.getWhoClicked().sendMessage("iptal edildi");
        });

        inv.setEvent(22, (e) -> InfoBuilder.buildInfo(e.getWhoClicked(), "transformer-info"));

        inv.setCloseEvent(e -> {
            ItemStack negativeItem = e.getView().getTopInventory().getItem(10);
            if (negativeItem.getItemMeta().hasLore()) {
                Player p = (Player) e.getPlayer();
                List<String> list = negativeItem.getItemMeta().getLore();
                list.remove(0);
                for (String s : list) {
                    String[] sp = s.split("x");
                    CarbonData.CarbonItem c = CarbonData.getCarbonItem(sp[1]);
                    int amount = Integer.parseInt(sp[0]);
                    p.getInventory().addItem(new ItemStack(c.getRepresentative(), amount));
                }
                p.updateInventory();
            }
        });
        inv.build();
    }
}
