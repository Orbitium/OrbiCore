package com.orbi.orbimc.bone.chat;

import com.orbi.orbimc.OrbiCore;
import com.orbi.orbimc.bone.logger.DiscordChannel;
import com.orbi.orbimc.bone.security.authority.Permission;
import com.orbi.orbimc.bone.security.authority.PermissionController;
import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.util.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;

public class ChatController {

    public static List<Player> players = new ArrayList<>();
    public static List<Player> nearPlayer = new ArrayList<>();
    public static String defaultNameFormat;

    public static void playerChatEvent(AsyncPlayerChatEvent e) {
        if (!players.contains(e.getPlayer()))
            return;
        String msg = String.format(defaultNameFormat, Color.translateHex(e.getPlayer().getPlayerListName()), e.getMessage());
        if (nearPlayer.contains(e.getPlayer()))
            Bukkit.getScheduler().scheduleSyncDelayedTask(OrbiCore.getInstance(), new Runnable() {
                @Override
                public void run() {
                    e.getPlayer().sendMessage(msg);
                    e.getPlayer().getNearbyEntities(125, 256, 125).forEach(entity -> entity.sendMessage(msg));
                }
            }, 1L);
        else
            players.forEach(player -> player.sendMessage(msg));

        OrbiCore.getDC().print(new EmbedBuilder().setColor(java.awt.Color.GREEN).
                setTitle(e.getPlayer().getName() + " adlı oyuncu bir mesaj gönderdi!").
                addField("Mesaj içeriği", e.getMessage(), true).build(), DiscordChannel.CHAT);
    }

}
