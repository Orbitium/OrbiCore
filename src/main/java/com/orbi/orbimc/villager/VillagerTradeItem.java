package com.orbi.orbimc.villager;

import com.orbi.orbimc.util.StaticItems;
import org.bukkit.Material;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;

public enum VillagerTradeItem {
    POTATO(Villager.Profession.FARMER, new ItemStack(Material.POTATO), StaticItems.villagerCoin, 0, 32, 24, 20, 14),
    CARROT(Villager.Profession.FARMER, new ItemStack(Material.CARROT), StaticItems.villagerCoin, 0, 32, 24, 20, 14);

    Villager.Profession profession;
    ItemStack given;
    ItemStack received;
    int level;
    int[] givenAmount;

    VillagerTradeItem(Villager.Profession profession, ItemStack given, ItemStack received, int level, int... givenAmount) {
        this.profession = profession;
        this.given = given;
        this.received = received;
        this.level = level;
        this.givenAmount = givenAmount;
    }

    public Villager.Profession getProfession() {
        return profession;
    }

    public ItemStack getGiven() {
        return given;
    }

    public ItemStack getReceived() {
        return received;
    }

    public int getLevel() {
        return level;
    }

    public int[] getGivenAmount() {
        return givenAmount;
    }

    public static VillagerTradeItem[] getByLevel(Villager.Profession profession, int level) {
        VillagerTradeItem[] items = new VillagerTradeItem[2];
        int i = 0;
        for (VillagerTradeItem villagerTradeItem : values()) {
            if (villagerTradeItem.getProfession() == profession) {
                if (villagerTradeItem.getLevel() == level) {
                    items[i++] = villagerTradeItem;
                    if (i == 2)
                        return items;
                } else if (villagerTradeItem.getLevel() > level)
                    throw new NullPointerException(profession.name() + " ait " + level + " seviyeli satışlar bulunamadı!");
            }
        }
        return items;
    }
}
