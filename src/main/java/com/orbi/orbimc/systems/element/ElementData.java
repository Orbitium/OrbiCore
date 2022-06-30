package com.orbi.orbimc.systems.element;

import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.database.MongoBase;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ElementData {

    public static Map<String, Integer> createPlayerData() {
        Map<String, Integer> dataMap = new HashMap<>();
        dataMap.put("protonAmount", 0);
        dataMap.put("neutronAmount", 0);
        dataMap.put("electronAmount", 0);
        dataMap.put("energyResistanceLevel", 0);
        dataMap.put("temperatureResistanceLevel", 1);
        dataMap.put("radiationResistanceLevel", 1);
        return dataMap;
    }

    public static Map<String, Integer> createLabData(Map<String, Integer> playerData) {
        Map<String, Integer> dataMap = new HashMap<>();
        dataMap.put("energyResistance", playerData.get("energyResistanceLevel") * Repo.getConfig("es-energy-upgrade-multiplier") + Repo.getConfig("es-min-energy-resistance"));
        dataMap.put("temperatureResistance", playerData.get("temperatureResistanceLevel") * Repo.getConfig("es-temperature-upgrade-multiplier") + Repo.getConfig("es-min-temperature-resistance"));
        dataMap.put("radiationResistance", playerData.get("radiationResistanceLevel") * Repo.getConfig("es-radiation-upgrade-multiplier") + Repo.getConfig("es-min-radiation-resistance"));
        return dataMap;
    }

    public static Map<String, Integer> createLabStorageData(Map<String, Integer> playerData) {
        Map<String, Integer> dataMap = new HashMap<>();
        dataMap.put("protonAmount", playerData.get("protonAmount"));
        dataMap.put("maxProtonAmount", Repo.getConfig("es-min-proton-limit") + playerData.get("energyResistanceLevel"));
        dataMap.put("neutronAmount", playerData.get("neutronAmount"));
        dataMap.put("maxNeutronAmount", Repo.getConfig("es-min-neutron-limit") + playerData.get("radiationResistanceLevel"));
        dataMap.put("electronAmount", playerData.get("electronAmount"));
        dataMap.put("maxElectronAmount", Repo.getConfig("es-min-electron-limit") + playerData.get("temperatureResistanceLevel"));
        return dataMap;
    }

    public static Map<String, Integer> createRepairLabData() {
        Map<String, Integer> dataMap = new HashMap<>();
        dataMap.put("woodAmount", Repo.getConfig("es-repair-wood"));
        dataMap.put("stoneAmount", Repo.getConfig("es-repair-stone"));
        dataMap.put("ironAmount", Repo.getConfig("es-repair-iron"));
        dataMap.put("goldAmount", Repo.getConfig("es-repair-gold"));
        dataMap.put("netheriteAmount", Repo.getConfig("es-repair-netherite"));
        return dataMap;
    }


    public static Map<String, Integer> getPlayerData(Player player) {
        return (Map<String, Integer>) MongoBase.getValue(player, "elementSystem");
    }

}
