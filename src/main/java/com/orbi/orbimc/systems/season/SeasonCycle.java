package com.orbi.orbimc.systems.season;

import com.orbi.orbimc.OrbiCore;
import com.orbi.orbimc.bone.tab.scoreboard.ScoreboardUpdater;
import com.orbi.orbimc.database.Cache;
import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.villager.TradeItem;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class SeasonCycle {

    public static int season;
    public static int year;
    public static Map<Integer, String> seasonMeaning = new HashMap<>();
    private int task;

    public void startCycle() {
        seasonMeaning.put(0, "İlkbahar");
        seasonMeaning.put(1, "Yaz");
        seasonMeaning.put(2, "Sonbahar");
        seasonMeaning.put(3, "Kış");
        season = Repo.getConfig("season");
        year = Repo.getConfig("year");
        LocalDateTime lc = LocalDateTime.now();

        long taskSecond = 0;
        long wSecond = 691200;

        taskSecond += lc.getSecond();
        taskSecond += lc.getMinute() * 60;
        taskSecond += lc.getHour() * 3600;
        taskSecond += lc.getDayOfWeek().getValue() * 86400L;

        wSecond -= taskSecond;

        task = Bukkit.getScheduler().scheduleSyncDelayedTask(OrbiCore.getInstance(), () -> {
            season += 1 % 4;
            TradeItem.reloadGivens();
            task = Bukkit.getScheduler().scheduleSyncRepeatingTask(OrbiCore.getInstance(), () -> {
                season += 1 % 4;
                TradeItem.reloadGivens();
                Bukkit.getOnlinePlayers().forEach(ScoreboardUpdater::addRequest);
                Repo.setConfig("season", season);
            }, 691200 * 20, 691200 * 20);
        }, wSecond * 20);
    }

    public void stopCycle() {
        Bukkit.getScheduler().cancelTask(task);
    }

}
