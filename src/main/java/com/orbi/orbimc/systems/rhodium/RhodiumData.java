package com.orbi.orbimc.systems.rhodium;

import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.database.MongoBase;
import com.orbi.orbimc.util.TimeController;
import org.bukkit.entity.Player;

import java.util.List;

public class RhodiumData {

    public static long createPlayerData() {
        return 0L;
    }

    public static long getRhodium(Player player) {
        return (long) MongoBase.getValue(player, "availableRhodium");
    }

    public static int getLastCollect(Player player) {
        return (int) MongoBase.getValue(player, "lastRhodiumCollect");
    }

    public static int calculateRhodiumProduction(Player player) {
        return ((int) MongoBase.getValue(player, "permission") + 1) * Repo.getConfig("rhodium-produce-multiplier");
    }

}
