package com.orbi.orbimc.systems.tasks;

import com.orbi.orbimc.database.MongoBase;
import com.orbi.orbimc.systems.tasks.root.PlayerTask;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerTaskData {

    public static Map<String, PlayerTask> taskList = new HashMap<>();

    public static boolean isContains(String name) {
        return taskList.containsKey(name);
    }

    public static int cratePlayerData() {
        return 0;
    }

    public static int getPlayerTaskLevel(Player player) {
        return (int) MongoBase.getValue(player, "taskLevel", 0);
    }

}
