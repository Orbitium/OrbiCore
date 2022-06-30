package com.orbi.orbimc.database;


import com.orbi.orbimc.bone.chat.ChatController;
import com.orbi.orbimc.bone.security.account.PlayerAccount;
import com.orbi.orbimc.bone.tab.PlayerTabList;
import com.orbi.orbimc.bone.tab.Tab;
import com.orbi.orbimc.systems.mob.MobData;
import com.orbi.orbimc.systems.tasks.PlayerTaskGUI;

import java.util.List;

public class YAMLBase {

    public static void loadValues() {
        Repo.loadAll();
        MobData.loadMobDataPrices();

        //Static metinleri yüklüyor
        ChatController.defaultNameFormat = Repo.getText("default-chatname-name-format");
        PlayerTaskGUI.defaultLore = List.of(Repo.getMultipleText("pt-default-lore", "pt-default-lore1", "pt-default-lore2"));
        Tab.tabFooter = Repo.getText("player-tab-list-footer0");
        PlayerTabList.defaultNameFormat = Repo.getText("default-player-list-name-format");
        PlayerTabList.scoreBoardHeader = Repo.getText("scoreboard-header");
        PlayerTabList.scoreBoardFooter = Repo.getText("scoreboard-footer");
        System.out.println("Loading files...");
    }
}
