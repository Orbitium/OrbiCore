package com.orbi.orbimc.bone.tab;

import com.orbi.orbimc.OrbiCore;
import com.orbi.orbimc.bone.chat.ChatController;
import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabAnimation {


    static List<String> serverNameList =
            new ArrayList<>(Arrays.asList(Repo.getText("player-tab-list-header0").split("\n")));
    static String hexList =
            "#6010ef\n" +
                    "#610ce4\n" +
                    "#6109da\n" +
                    "#6106d0\n" +
                    "#6004c5\n" +
                    "#5f02bb\n" +
                    "#5d01b2\n" +
                    "#5b01a8\n" +
                    "#570195\n" +
                    "#54018c\n" +
                    "#570195\n" +
                    "#59009e\n" +
                    "#5d01b2\n" +
                    "#5f02bb\n" +
                    "#6004c5\n" +
                    "#6106d0\n" +
                    "#6109da\n" +
                    "#610ce4\n" +
                    "#6010ef\n";
    static int order = 0;
    static int pingOrder = -1;
    static int tpsOrder = -1;
    static int onlinePlayerOrder = -1;

    public static void tabAnimation() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(OrbiCore.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (Bukkit.getOnlinePlayers().size() != 0)
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        //Header animation
                        StringBuilder str = new StringBuilder();
                        for (String s : serverNameList) {
                            str.append(s).append("\n");
                        }
                        StringBuilder newHeader = new StringBuilder();
                        for (char c : (">>> " + Repo.getText("server-name") + " <<<").toCharArray()) {
                            if (c != ' ')
                                order = order != hexList.split("\n").length - 1 ? order + 1 : 0;
                            newHeader.append(hexList.split("\n")[order]).append(c);
                        }
                        serverNameList.set(1, Color.translateHex(newHeader.toString()));
                        Tab.tabHeader = str.substring(0, str.length() - 1);
                        //Footer animation
                        String[] splittedFooter = Tab.tabFooter.split(" ");
                        splittedFooter[0] = Color.translateHex(hexList.split("\n")[order] + "");
                        splittedFooter[pingOrder] = player.getPing() + "";
                        splittedFooter[tpsOrder] = Math.round(getRecentTps()[0]) + "";
                        splittedFooter[onlinePlayerOrder] = ChatController.players.size() + "";
                        Tab.tabFooter = "";
                        for (String s : splittedFooter) {
                            Tab.tabFooter += s + " ";
                        }
                        player.setPlayerListHeaderFooter(Tab.tabHeader, Tab.tabFooter);
                    }
            }
        }, 4, 4);
    }

    private static Object minecraftServer;
    private static Field recentTps;

    public static double[] getRecentTps() {
        try {
            if (minecraftServer == null) {
                Server server = Bukkit.getServer();
                Field consoleField = server.getClass().getDeclaredField("console");
                consoleField.setAccessible(true);
                minecraftServer = consoleField.get(server);
            }
            if (recentTps == null) {
                recentTps = minecraftServer.getClass().getSuperclass().getDeclaredField("recentTps");
                recentTps.setAccessible(true);
            }
            return (double[]) recentTps.get(minecraftServer);
        } catch (IllegalAccessException | NoSuchFieldException ignored) {
        }
        return new double[]{20, 20, 20};
    }

}
