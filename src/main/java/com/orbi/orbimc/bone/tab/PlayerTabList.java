package com.orbi.orbimc.bone.tab;

import com.orbi.orbimc.OrbiCore;
import com.orbi.orbimc.bone.tab.scoreboard.CreateScoreboard;
import com.orbi.orbimc.bone.security.authority.Permission;
import com.orbi.orbimc.bone.security.authority.PermissionController;

import com.orbi.orbimc.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.Map;

public class PlayerTabList {

    //private static final Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    //private static final Map<String, Team> teamMap = new HashMap<>();
    public static String defaultNameFormat;
    public static String scoreBoardHeader;
    public static String scoreBoardFooter;

    public static void alignPlayer(Player player) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(OrbiCore.getInstance(), new Runnable() {
            @Override
            public void run() {
                Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
                Map<String, Team> teamMap = new HashMap<>();


                for (Permission permission : Permission.values()) {
                    Team team = sb.registerNewTeam(permission.getScoreboardValue() + permission.getPermissionName());
                    team.setPrefix(String.format(defaultNameFormat, Color.translateHex(permission.getPermissionName())));
                    team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
                    teamMap.put(permission.getScoreboardValue(), team);
                }

                Permission permission = PermissionController.getPermission(player.getName());
                teamMap.get(permission.getScoreboardValue()).addEntry(player.getName());

                for (Player otherP : Bukkit.getOnlinePlayers()) {
                    Permission permission1 = PermissionController.getPermission(otherP.getName());
                    teamMap.get(permission1.getScoreboardValue()).addEntry(otherP.getName());
                }

                player.setPlayerListName(String.format(defaultNameFormat, Color.translateHex(permission.getPermissionName())) + player.getName());

                CreateScoreboard.createScoreBoard(player, sb);

                player.setScoreboard(sb);
            }
        }, 5L);
    }


}

