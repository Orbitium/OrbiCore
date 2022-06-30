package com.orbi.orbimc.listeners;

import com.orbi.orbimc.database.Repo;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.world.ChunkLoadEvent;

public class ServerListener implements Listener {

    @EventHandler
    public static void onServerListPing(ServerListPingEvent e) {
        e.setMotd(Repo.getText("server-motd"));
    }

}
