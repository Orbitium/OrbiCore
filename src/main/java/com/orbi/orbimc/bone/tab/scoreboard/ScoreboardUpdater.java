package com.orbi.orbimc.bone.tab.scoreboard;

import com.orbi.orbimc.OrbiCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScoreboardUpdater {

    private static final List<Player> updateRequest = new ArrayList<>();

    private static void startTask() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(OrbiCore.getInstance(), new Runnable() {
            @Override
            public void run() {
                for (Player p : updateRequest) {
                    CreateScoreboard.createScoreBoard(p, p.getScoreboard());
                }
                updateRequest.clear();
            }
        }, 20L);
    }

    public static void addRequest(Player player) {
        if (updateRequest.size() == 0)
            startTask();
        updateRequest.add(player);
    }

}
