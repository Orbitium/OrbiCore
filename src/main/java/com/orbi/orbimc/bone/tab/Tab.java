package com.orbi.orbimc.bone.tab;

import com.orbi.orbimc.database.Repo;
import org.bukkit.entity.Player;

public class Tab {
    public static String tabHeader;
    public static String tabFooter;

    public static void initializeTabConfig() {
        //PlayerTabList.initializeScoreboard();

        String[] splittedFooter = tabFooter.split(" ");
        int order = 0;
        for (String s : splittedFooter) {
            if (s.equals("null")) {
                if (TabAnimation.pingOrder == -1)
                    TabAnimation.pingOrder = order;
                else if (TabAnimation.tpsOrder == -1)
                    TabAnimation.tpsOrder = order;
                else if (TabAnimation.onlinePlayerOrder == -1)
                    TabAnimation.onlinePlayerOrder = order;
            }
            order++;
        }
        TabAnimation.tabAnimation();
    }

    public static void addPlayerToTab(Player p) {
        //PlayerNameFormatter.changePlayerName(p); //DÜZGÜN ÇALIŞMIYOR OLABİLİR
        PlayerTabList.alignPlayer(p);
        p.setPlayerListHeaderFooter(tabHeader, tabFooter);
    }
}
