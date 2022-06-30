package com.orbi.orbimc.systems.mob.gui;

import com.orbi.orbimc.database.Cache;
import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.systems.mob.MobData;
import com.orbi.orbimc.util.ItemBuilder;
import com.orbi.orbimc.util.StaticItems;
import com.orbi.orbimc.util.inventory.Inventory;
import com.orbi.orbimc.util.inventory.InventoryBuilder2;
import com.orbi.orbimc.util.inventory.InventoryManager;
import com.orbi.orbimc.util.inventory.Pattern;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BuyMobItem implements Inventory {
    String soulHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWFlOGI5ZThlZTUwNWEwODRlMTk1ODg2NjhhOGVjN2ZkODM3YjhiNDk0MjA5ODc0N2I0YTAyZWE2MDkwNTNjIn19fQ";

    @Override
    public void initializeInventory() {
        InventoryBuilder2 inventory = new InventoryBuilder2(Pattern.FILL, 54, "buy-mob-item-title");
        inventory.setItem(1, new ItemBuilder(Material.DIAMOND_SWORD, true));
        inventory.setItem(3, new ItemStack(Material.EGG));
        inventory.setItem(5, new ItemStack(Material.CHICKEN_SPAWN_EGG));
        inventory.setItem(7, new ItemStack(Material.NETHER_STAR));

        int f = 0;
        for (int x = 11; x < 45; x++) {
            MobData.MobItem m = MobData.mobItems.get(f);
            f++;
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GOLD + "Sol tık 1x(" + m.getCost() + " YR harcar) üretim yapar");
            lore.add(ChatColor.GOLD + "Sağ tık 16x(" + m.getCost() * 16 + " YR harcar) üretim yapar");
            ItemBuilder b = new ItemBuilder(m.getM());
            b.setLore(lore);

            if (x % 9 == 0)
                x += 2;

            inventory.setItem(x - 1, b.build());

            clickEvent(inventory, m, x - 1);
        }
        inventory.setEvent(3, e -> InventoryManager.open(e.getWhoClicked(), "buy-animal-item-title"));
        inventory.setEvent(5, e -> InventoryManager.open(e.getWhoClicked(), "spawn-creature-item-title"));

        inventory.setOpenEvent(e -> {
            Player p = (Player) e.getPlayer();
            inventory.setItem(49, new ItemBuilder(soulHead, ChatColor.DARK_PURPLE + "Mevcut yaratık ruhu: " + Cache.getAsDouble(p, "availableSoulPoint")));
        });
        inventory.build();
    }

    public void clickEvent(InventoryBuilder2 inventory, MobData.MobItem m, int i) {
        inventory.setEvent(i, e -> {
            Player p = (Player) e.getWhoClicked();
            int multiplier = e.getClick().isLeftClick() ? 1 : 16;
            int cost = multiplier * m.getCost();
            if (Cache.getAsDouble(p, "availableSoulPoint") >= cost) {
                p.getInventory().addItem(new ItemStack(m.getM(), multiplier));
                Cache.decreaseDoubleValue(p, "availableSoulPoint", cost);
                inventory.setItem(49, new ItemBuilder(soulHead, ChatColor.DARK_PURPLE + "Mevcut yaratık ruhu: " + Cache.getAsDouble(p, "availableSoulPoint")));
            } else {
                p.sendMessage("Yetersiz yaratık ruhu");
                p.closeInventory();
                p.updateInventory();
            }
        });
    }
}
