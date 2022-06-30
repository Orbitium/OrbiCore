package com.orbi.orbimc.systems.carbon;


import com.orbi.orbimc.systems.playerreference.PlayerReferenceData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CarbonReference {

    public static void onCarbonEarn(Player player, long earnedCarbon) {

        String referencedPlayer = PlayerReferenceData.getPlayerReferenced(player);

        if (referencedPlayer.equals("null")) {
            long referenceShare = 1 + Math.round((earnedCarbon * 0.0005) * PlayerReferenceData.getPlayerReferencers(player).size());
            //CarbonData.increaseCarbon(player, earnedCarbon + referenceShare);
            return;
        }

        long referencedShare = 1 + Math.round(earnedCarbon * 0.02); //Oyuncunun referans ettiği kişi
        long referenceShare = 1 + Math.round((earnedCarbon * 0.005) * PlayerReferenceData.getPlayerReferencers(player).size());
        //Oyuncuyu referans eden kişi başına +%0.05 ekstra karbon

        //CarbonData.increaseCarbon(player, earnedCarbon + referenceShare); //İşlemi yapan oyuncuyu referans alan kişi başına
        //CarbonData.increaseCarbon(referencedPlayer, referencedShare);//Referans aldığı oyuncuya %2.5 karbon

    }

}
