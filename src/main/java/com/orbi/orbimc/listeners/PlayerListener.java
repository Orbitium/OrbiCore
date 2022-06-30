package com.orbi.orbimc.listeners;

import com.orbi.orbimc.OrbiCore;
import com.orbi.orbimc.bone.chat.ChatController;
import com.orbi.orbimc.bone.logger.DiscordChannel;
import com.orbi.orbimc.bone.security.account.PlayerAccount;
import com.orbi.orbimc.commands.root.CommandManager;
import com.orbi.orbimc.database.Cache;
import com.orbi.orbimc.database.MongoBase;
import com.orbi.orbimc.systems.autopickup.CoarseDirtNerf;
import com.orbi.orbimc.systems.autopickup.Pickup;
import com.orbi.orbimc.systems.fly.FlyManager;
import com.orbi.orbimc.systems.generator.GeneratorPlayerManager;
import com.orbi.orbimc.util.custommessages.CustomDeathMessages;
import com.orbi.orbimc.villager.Parchment;
import com.orbi.orbimc.villager.VillagerIQ;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

import java.awt.*;

public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public static void onBlockBreak(BlockBreakEvent e) {
        if (e.getBlock().getWorld().getName().equals("world") && !e.getPlayer().isOp())
            e.setCancelled(true);
        else if (e.getBlock().getType().equals(Material.STONE) || e.getBlock().getType().equals(Material.COBBLESTONE))
            GeneratorPlayerManager.updatePlayerData(e);
        if (e.getBlock().getType().equals(Material.DIRT) || e.getBlock().getType().equals(Material.GRASS_BLOCK))
            OrbiCore.getDC().print(new EmbedBuilder().setColor(java.awt.Color.GREEN).
                    setTitle(e.getPlayer().getName() + " adlı oyuncu bir blok kırdı!").
                    addField("Kırılan blok", e.getBlock().getType().name(), true).
                    addField("Lokasyon", String.valueOf(e.getBlock().getLocation()), true).build(), DiscordChannel.BREAKBLOCK);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public static void onBlockPlace(BlockPlaceEvent e) {
        if (e.getBlock().getWorld().getName().equals("world") && !e.getPlayer().isOp())
            e.setCancelled(true);
        if (!e.getBlock().getType().equals(Material.COBBLESTONE_SLAB) && !e.getBlock().getType().equals(Material.COBBLESTONE))
            OrbiCore.getDC().print(new EmbedBuilder().setColor(java.awt.Color.GREEN).
                    setTitle(e.getPlayer().getName() + " adlı oyuncu bir blok koydu!").
                    addField("Koyulan blok", e.getBlock().getType().name(), true).
                    addField("Lokasyon", String.valueOf(e.getBlock().getLocation()), true).build(), DiscordChannel.PLACEBLOCK);
    }


    @EventHandler
    public static void onPlayerDeath(PlayerDeathEvent e) {
        if (FlyManager.flyingPlayers.containsKey(e.getEntity()))
            FlyManager.removePlayer(e.getEntity(), false);
        CustomDeathMessages.sendCustomDeathMessages(e);
    }

    @EventHandler
    public static void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getItem() == null)
            return;
        else if (e.getItem().getType() == Material.MAP)
            Parchment.cancelMapConvert(e);
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR) {
            if (e.getItem().getType() == Material.VILLAGER_SPAWN_EGG) {
                Parchment.handleVillagerSpawnEgg(e.getPlayer(), e.getItem());
            }
        } else if (e.getClickedBlock() != null && e.getClickedBlock().getType().equals(Material.COMPOSTER)) {
            Material i = e.getPlayer().getInventory().getItemInMainHand().getType();
            if (i.equals(Material.ROTTEN_FLESH) || i.equals(Material.BAMBOO)) {
                try {
                    BlockData bd = e.getClickedBlock().getBlockData();
                    ((Levelled) bd).setLevel(((Levelled) bd).getLevel() + 1);
                    e.getClickedBlock().setBlockData(bd);
                    e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                    e.setCancelled(true);
                } catch (IllegalArgumentException ignored) {
                }
            }
        } else
            //todo karbon paketi patladı sanırım, -> PackageObserver.onInteractEvent(e);
            CoarseDirtNerf.onClickDirt(e);
    }

    @EventHandler
    public static void onPlayerDropItem(PlayerDropItemEvent e) {
        if (!ChatController.players.contains(e.getPlayer()))
            e.setCancelled(true);
    }

    @EventHandler
    public static void onPlayerBucketEmpty(PlayerBucketEmptyEvent e) {
        if (e.getBlock().getWorld().getName().equals("world") && !e.getPlayer().isOp())
            e.setCancelled(true);
    }

    @EventHandler
    public static void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent e) {
        if (e.getRightClicked().getType() == EntityType.VILLAGER) {
            if (e.getPlayer().isSneaking()) {
                VillagerIQ.printVillagerInfo(e);
                e.setCancelled(true);
                return;
            }
            Parchment.checkVillager(e);
        }
    }

    @EventHandler
    public static void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        ChatController.playerChatEvent(e);
    }

    @EventHandler
    public static void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
        CommandManager.onPreCommand(e);
        String[] message = e.getMessage().split(" ");
        String command = message[0];

        if (!command.equals("/giriş") && !command.equals("/kayıt") && !command.equals("/giris") && !command.equals("/kayit"))
            OrbiCore.getDC().print(new EmbedBuilder().setColor(Color.GREEN).
                    setTitle(e.getPlayer().getName() + " adlı oyuncu bir komut kullandı!")
                    .addField("Bir komut kullandı", e.getMessage(), true).build(), DiscordChannel.COMMAND);
    }

    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        PlayerAccount.checkAccount(e.getPlayer());

    }

    @EventHandler
    public static void onPlayerQuit(PlayerQuitEvent e) {
        PlayerAccount.checkPlayerTimeOut(e.getPlayer());
        if (FlyManager.flyingPlayers.containsKey(e.getPlayer()))
            FlyManager.removePlayer(e.getPlayer(), false);
        PlayerAccount.leave(e.getPlayer());
        ChatController.players.remove(e.getPlayer());
        e.setQuitMessage(null);
    }

    @EventHandler
    public static void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent e) {
        if (MongoBase.getPlayerData(e.getName()).first() == null)
            MongoBase.createUserData(e.getName());
        Cache.registerPlayerData(e.getName());
    }

    @EventHandler
    public static void onPlayerChangedWorld(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        if (e.getPlayer().getWorld().getName().equals("world"))
            if (FlyManager.flyingPlayers.containsKey(e.getPlayer()))
                FlyManager.removePlayer(player, false);
    }

    @EventHandler
    public static void onBlockDropItem(BlockDropItemEvent e) {
        Pickup.checkPlayerInventory(e);
    }
}
