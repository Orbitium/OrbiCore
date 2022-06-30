package com.orbi.orbimc.systems.autopickup;

import org.bukkit.Material;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.Damageable;

public class CoarseDirtNerf {

    public static void onClickDirt(PlayerInteractEvent e) {
        if (e.getClickedBlock() != null && e.getClickedBlock().getType().equals(Material.COARSE_DIRT)) {
            if (e.getItem() == null || e.getItem().getType().equals(Material.WOODEN_HOE) ||
                    e.getItem().getType().equals(Material.STONE_HOE))
                e.setCancelled(true);
            else {
                Damageable dmg = (Damageable) e.getItem().getItemMeta();
                dmg.setDamage(dmg.getDamage() + 4);
                e.getItem().setItemMeta(dmg);
            }
        }
    }

}
