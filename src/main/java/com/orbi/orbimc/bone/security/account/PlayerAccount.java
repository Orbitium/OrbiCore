package com.orbi.orbimc.bone.security.account;

import com.orbi.orbimc.OrbiCore;
import com.orbi.orbimc.bone.chat.ChatController;
import com.orbi.orbimc.bone.logger.DiscordChannel;
import com.orbi.orbimc.bone.tab.Tab;
import com.orbi.orbimc.bone.warps.Warp;
import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.database.MongoBase;
import com.orbi.orbimc.systems.generator.GeneratorPlayerManager;
import com.orbi.orbimc.systems.rhodium.RhodiumCollect;
import com.orbi.orbimc.util.StringParser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.awt.*;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class PlayerAccount {

    static Map<Player, Integer> timeOutMap = new HashMap<>();
    static Map<InetAddress, Player> trustedIPS = new HashMap<>();

    public static String baseJoinMessage;
    public static String baseLeaveMessage;

    public static int isTest = Repo.getConfig("is-test-server");


    public static void checkAccount(Player player) {
        if (MongoBase.getValue(player, "password") == null)
            player.sendMessage(Repo.getMSG("make-register"));
        else player.sendMessage(Repo.getMSG("make-login"));
        /*
        else{
            if (trustedIPS.containsKey(player.getAddress().getAddress())) {
                login(player, true);
                return;
            }
        }

        int task = Bukkit.getScheduler().scheduleSyncDelayedTask(OrbiCore.getInstance(),
                () -> player.kickPlayer(Repo.getMSG("register-timeout")), 30 * 20);

        timeOutMap.put(player, task);
        player.teleport(Warp.warps.get("loginArea"));
         */


        for (Player player1 : Bukkit.getOnlinePlayers()) {
            player1.hidePlayer(OrbiCore.getInstance(), player);
            player.hidePlayer(OrbiCore.getInstance(), player1);
        }

        if (isTest == 1)
            login(player);
    }

    public static void login(Player player) {
        player.teleport(Warp.warps.get("spawn"));
        player.sendMessage(Repo.getMSG("success-login"));
        ChatController.players.add(player);
        //trustedIPS.put(player.getAddress().getAddress(), player);
        //Bukkit.getScheduler().cancelTask(timeOutMap.get(player));
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            player1.showPlayer(OrbiCore.getInstance(), player);
            //player1.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(StringParser.parse(baseJoinMessage, player.getDisplayName())));
            player.showPlayer(OrbiCore.getInstance(), player1);
        }
        //RhodiumCollect.checkCollect(player);
        Tab.addPlayerToTab(player);
        OrbiCore.getDC().print(new EmbedBuilder().setColor(Color.GREEN).
                setTitle(player.getName() + " adlı oyuncu sunucuya girdi!").build(), DiscordChannel.INOUT);
    }

    public static void leave(Player player) {
        try {
            GeneratorPlayerManager.cobbleData.get(player).save();
        } catch (NullPointerException ignored) {
        }
        player.setGameMode(GameMode.SURVIVAL);
        OrbiCore.getDC().print(new EmbedBuilder().setColor(Color.RED).
                setTitle(player.getName() + " adlı oyuncu sunucudan çıktı!").build(), DiscordChannel.INOUT);
        if (ChatController.players.contains(player)) {
            for (Player player1 : Bukkit.getOnlinePlayers()) {
              //  player1.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(StringParser.parse(baseLeaveMessage, player.getDisplayName())));
            }
        }
    }
    /* oto login
    public static void login(Player player, boolean isAutomatic) {
        if (!isAutomatic)
            return;
        player.teleport(Warp.warps.get("spawn"));
        player.sendMessage(Repo.getMSG("success-login"));
        ChatChannel.players.add(player);

        for (Player player1 : Bukkit.getOnlinePlayers()) {
            player1.showPlayer(OrbiCore.getInstance(), player);
            player1.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(StringParser.parse(baseJoinMessage, player.getDisplayName())));
            player.showPlayer(OrbiCore.getInstance(), player1);
        }
        RhodiumCollect.checkCollect(player);
    }*/

    public static void wrongPassword(Player player) {
        Bukkit.getScheduler().cancelTask(timeOutMap.get(player));
        player.kickPlayer(Repo.getMSG("wrong-password"));
        timeOutMap.remove(player);
    }

    public static void checkPlayerTimeOut(Player player) {
        timeOutMap.remove(player);
    }

}
