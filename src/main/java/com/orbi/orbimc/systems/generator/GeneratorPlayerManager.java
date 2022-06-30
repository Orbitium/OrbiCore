package com.orbi.orbimc.systems.generator;

import com.orbi.orbimc.database.MongoBase;
import com.orbi.orbimc.systems.energy.EnergyData;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;
import java.util.Map;

public class GeneratorPlayerManager {

    public static Map<Player, GeneratorPlayerData> cobbleData = new HashMap<>();

    public static void updatePlayerData(BlockBreakEvent e) {
        try {
            cobbleData.get(e.getPlayer()).chooseProduce();
        } catch (NullPointerException ex) {
            cobbleData.put(e.getPlayer(), new GeneratorPlayerData(e.getPlayer(), EnergyData.getPlayerData(e.getPlayer()).get("storedEnergy")));
            updatePlayerData(e);
        }
    }

    public static void removePlayer(Player p) {
        if (cobbleData.containsKey(p)) {
            EnergyData.getPlayerData(p);
            Map<String, Double> playerData = GeneratorData.getPlayerData(p);
            playerData.put("availableStonePoint", GeneratorPlayerManager.cobbleData.get(p).availableStonePoint);
            MongoBase.setValue(p, "generatorSystem", playerData);
            cobbleData.remove(p);
        }
    }
}
