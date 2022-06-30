package com.orbi.orbimc.listeners;

import com.orbi.orbimc.OrbiCore;
import com.orbi.orbimc.item.OnPlayerKillEntity;
import com.orbi.orbimc.item.P2;
import com.orbi.orbimc.systems.mob.MobSoul;
import com.orbi.orbimc.villager.Parchment;
import com.orbi.orbimc.villager.VillagerIQ;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EntityListener implements Listener {

    @EventHandler
    public static void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity().getType().equals(EntityType.PLAYER)) {
            if (e.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
                e.setCancelled(true);
                ((Player) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 3 * 20, 256));
                Bukkit.dispatchCommand(e.getEntity(), "is go");
            }
            if (e.getEntity().getWorld().getName().equals("world"))
                if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    e.setCancelled(true);
                    return;
                }
        } else if (e.getEntity().getType().equals(EntityType.DROPPED_ITEM)) {
            if (e.getCause().equals(EntityDamageEvent.DamageCause.CONTACT) && ((Item) e.getEntity()).getItemStack().getType().equals(Material.CACTUS)) {
                e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation().add(-1, 0, 0), new ItemStack(Material.CACTUS));
                e.getEntity().remove();
            }

        }
    }

    @EventHandler
    public static void onEntityDeath(EntityDeathEvent e) {
        OnPlayerKillEntity.event1(e);
        MobSoul.check(e);
    }

    @EventHandler
    public static void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager().getType() == EntityType.PLAYER) {
            OnPlayerKillEntity.event2(e);
            //ItemBreakObserver.handleWeaponBreak(e);
        }
    }

    @EventHandler
    public static void onEntityTransform(EntityTransformEvent e) {
        if (e.getTransformReason() == EntityTransformEvent.TransformReason.CURED)
            VillagerIQ.zombieToVillager(e);
    }

    @EventHandler
    public static void onCreatureSpawn(CreatureSpawnEvent e) {
        if (e.getEntity().getType() == EntityType.VILLAGER &&
                e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.BREEDING || e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)
            VillagerIQ.villagerSpawnHandle(e);
    }

    @EventHandler
    public static void onEntityBreed(EntityBreedEvent e) {
        VillagerIQ.bornHandler(e);
    }

    @EventHandler
    public static void onVillagerCareerChange(VillagerCareerChangeEvent e) {
        Bukkit.getScheduler().runTaskLater(OrbiCore.getInstance(), () -> {
            if (!e.getEntity().getPersistentDataContainer().has(Parchment.trades, PersistentDataType.INTEGER_ARRAY))
                e.setCancelled(true);
        }, 3L);
    }

}
