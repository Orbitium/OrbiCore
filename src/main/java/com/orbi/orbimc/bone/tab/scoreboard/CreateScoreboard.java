package com.orbi.orbimc.bone.tab.scoreboard;

import com.orbi.orbimc.database.Cache;
import com.orbi.orbimc.math.ConvertType;
import com.orbi.orbimc.systems.carbon.CarbonData;
import com.orbi.orbimc.math.Converter;
import com.orbi.orbimc.systems.energy.EnergyData;
import com.orbi.orbimc.systems.season.SeasonCycle;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class CreateScoreboard {

    public static void createScoreBoard(Player player, Scoreboard sb) {
        Objective objective;
        if (sb.getObjective("test") != null)
            sb.getObjective("test").unregister();

        objective = sb.registerNewObjective("test", "test", "Test scoreboard");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score carbonAmount = objective.getScore("Mevcut karbon: " + Converter.formatValue(CarbonData.getCarbon(player)));
        carbonAmount.setScore(2);

        Score energyAmount = objective.getScore("Mevcut Enerji: " + Converter.formatValue(Cache.getAsLong(player.getName(), "storedEnergy"), ConvertType.ENERGY));
        energyAmount.setScore(1);

        Score season = objective.getScore("Mevsim: " + SeasonCycle.seasonMeaning.get(SeasonCycle.season));
        season.setScore(0);

        /*
        Score footer = objective.getScore(scoreBoardFooter);
        Score row1 = objective.getScore(Repo.getText("scoreboard-row"));
        Score space1 = objective.getScore(" ");
        Score space2 = objective.getScore(" ");
        //Score availableCarbon = objective.getScore(ChatColor.GREEN + "Mevcut karbon: " + Converter.formatValue(CarbonData.getCarbon(player)));
        Score availableRhodium = objective.getScore(ChatColor.GREEN + "Mevcut rodyum: " + RhodiumData.getRhodium(player));
        Score availableEnergy = objective.getScore(ChatColor.GREEN + "Mevcut enerji: " + Converter.formatValue(EnergyData.getPlayerData(player).get("storedEnergy"), ConvertType.ENERGY));//byrasu ve aşağısı yap
        Score availableStonePoint = objective.getScore(ChatColor.GREEN + "Mevcut taş puanı: " + GeneratorData.getPlayerData(player).get("stonePoint"));
        Score availablePullLevel = objective.getScore(ChatColor.GREEN + "Mevcut çekim gücü: " + PlayerItemData.getPlayerData(player).get("level"));
        row1.setScore(7);
        //availableCarbon.setScore(6);
        availableRhodium.setScore(5);
        availableEnergy.setScore(4);
        availableStonePoint.setScore(3);
        availablePullLevel.setScore(2);
        space2.setScore(1);
        footer.setScore(0);
         */
        //Update olarak bunların gözükmesini açma/kapama olabilir knks
    }
}
