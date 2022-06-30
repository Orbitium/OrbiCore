package com.orbi.orbimc.bone.chat;

import com.orbi.orbimc.util.Color;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ChatChannel {

    private List<Player> players = new ArrayList<>();

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public void sendMessage(Player sender, String message) {
        String msg = String.format(ChatController.defaultNameFormat, sender.getPlayerListName(), message);
        players.forEach(player -> player.sendMessage(msg));
    }
}
