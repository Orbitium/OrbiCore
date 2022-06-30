package com.orbi.orbimc.item;

import com.orbi.orbimc.util.ItemBuilder;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;

public class OnPlayerKillEntity {

    public static void event2(EntityDamageByEntityEvent e) {
        if (e.getDamager().getType() == EntityType.PLAYER) {
            Player p = (Player) e.getDamager();
            ItemStack i = p.getInventory().getItemInMainHand();
            if (EnchantmentTarget.WEAPON.includes(i))
                P2.t(p, e.getEntity(), 1, 0, 9, e, 4, 6, 7, 8);
        }
    }

    public static void event1(EntityDeathEvent e) {
        if (e.getEntity().getKiller() != null) {
            ItemStack i = e.getEntity().getKiller().getInventory().getItemInMainHand();
            if (EnchantmentTarget.WEAPON.includes(i))
                P2.t(e.getEntity().getKiller(), e.getEntity(), 1, 0, 9, e, 0, 1, 2, 3, 5);
        }
    }
}
