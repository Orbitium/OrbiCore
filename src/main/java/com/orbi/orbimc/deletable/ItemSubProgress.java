package com.orbi.orbimc.deletable;

import com.orbi.orbimc.OrbiCore;
import com.orbi.orbimc.util.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public enum ItemSubProgress {
    KRITIK(1, "Kritik vuruş", 20, 4, 13, p -> {
        if (!(p instanceof EntityDamageByEntityEvent))
            return;
        EntityDamageByEntityEvent p1 = (EntityDamageByEntityEvent) p;
        p1.setDamage(50);
        p1.getDamager().sendMessage("sa");
      //  earnXP(getByID(1), ((Player) p1.getDamager()).getInventory().getItemInMainHand(), 1);
    }),

    RUHSOMURUSU(2, "Ruh sömürüsü", 20, 4, 5, null);

    private final int id;
    private final String name;
    private final int minXP;
    private final int xpMultiplier;
    private final int maxLevel;
    private final Consumer<Object> event;
    private final List<NamespacedKey> keys = new ArrayList<>();

    ItemSubProgress(int id, String name, int minXP, int xpMultiplier, int maxLevel, Consumer<Object> event) {
        this.id = id;
        this.name = name;
        this.minXP = minXP;
        this.xpMultiplier = xpMultiplier;
        this.maxLevel = maxLevel;
        this.event = event;
        keys.add(new NamespacedKey(OrbiCore.getInstance(), "xp" + id));
        keys.add(new NamespacedKey(OrbiCore.getInstance(), "xpNeed" + id));
        keys.add(new NamespacedKey(OrbiCore.getInstance(), "maxLevel" + id));
    }

    public String getName() {
        return name;
    }

    public int getMinXP() {
        return minXP;
    }

    public int getXpMultiplier() {
        return xpMultiplier;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void registerKeys(PersistentDataContainer pdc) {
        for (NamespacedKey n : keys) {
            pdc.set(n, PersistentDataType.INTEGER, 0);
        }
    }


    public String lore() {
        return Color.translateHex("#7426e0✦ " + name + "#d6d6d6: #ffff55[#AA0000" + "□".repeat(Math.max(0, maxLevel)) + "#ffff55]");
    }


    public Consumer<Object> getEvent() {
        return event;
    }


    public static ItemSubProgress getByID(int id) {
        for (ItemSubProgress isb : values()) {
            if (isb.id == id)
                return isb;
        }
        throw new NullPointerException(id + " li bir ItemSubProgress bulunamadı");
    }


    public static void earnXP(ItemSubProgress ib, ItemStack itemStack, int eXP) {
        ItemMeta im = itemStack.getItemMeta();
        PersistentDataContainer pdc = im.getPersistentDataContainer();
        int cXP = pdc.get(ib.keys.get(0), PersistentDataType.INTEGER) + eXP;
        if (cXP < pdc.get(ib.keys.get(1), PersistentDataType.INTEGER)) {
            pdc.set(ib.keys.get(0), PersistentDataType.INTEGER, cXP);
        }
        System.out.println("XP kazandırıldı, yeni XP: " + cXP);
        itemStack.setItemMeta(im);
    }

    private static void getLevel() {

    }
}
