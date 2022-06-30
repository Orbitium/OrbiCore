package com.orbi.orbimc.systems.mob;

import com.orbi.orbimc.database.Cache;
import com.orbi.orbimc.database.MongoBase;
import com.orbi.orbimc.item.ItemKeys;
import com.orbi.orbimc.util.StaticItems;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class MobSoul {

    public static void earnSoul(Player p, double soul) {
        Cache.increaseDoubleValue(p, "soulPoint", soul);
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(soul + " ruh kazanıldı. Yeni ruh miktarı: " + Cache.getAsDouble(p, "soulPoint")));
    }

    public static void check(EntityDeathEvent e) {
        if (e.getEntity().getPersistentDataContainer().has(ItemKeys.noDrop, PersistentDataType.INTEGER))
            for (ItemStack drop : e.getDrops()) {
                drop.setAmount(0);
            }
    }


}
