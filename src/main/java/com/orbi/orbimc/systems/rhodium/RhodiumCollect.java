package com.orbi.orbimc.systems.rhodium;

import com.orbi.orbimc.database.MongoBase;
import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.util.StringParser;
import com.orbi.orbimc.util.TimeController;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RhodiumCollect {

    public static void checkCollect(Player player) {
        long spacedTime = TimeController.calculateTimeSpacing(RhodiumData.getLastCollect(player));
        if (spacedTime >= 86400) {
            MongoBase.setValue(player, "availableRhodium", RhodiumData.getRhodium(player) + (int) MongoBase.getValue(player, "permission") + 1);
            player.sendMessage(Repo.getMSG("rs-success-collect"));
            MongoBase.setValue(player, "lastRhodiumCollect", TimeController.dateToTimeValue());
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.US);
            String dateString = formatter.format(new Date(spacedTime * 1000L));
            player.sendMessage(StringParser.parse(Repo.getMSG("rs-warn-collect"), dateString));
        }
    }

}
