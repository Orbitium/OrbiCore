package com.orbi.orbimc.systems.element.laboratory;

import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.math.ConvertType;
import com.orbi.orbimc.math.Converter;

import java.util.*;

public enum UpgradeCost {

    ENERGY("es-energy-upgrade-min-", "es-min-energy-resistance", "es-energy-upgrade-multiplier"),
    TEMPERATURE("es-temperature-upgrade-min-", "es-min-temperature-resistance", "es-temperature-upgrade-multiplier"),
    RADIATION("es-radiation-upgrade-min-", "es-min-radiation-resistance", "es-radiation-upgrade-multiplier");

    public String minItemType;
    public String minResistance;
    public String resistanceMultiplier;

    UpgradeCost(String minItemType, String minResistance, String resistanceMultiplier) {
        this.minItemType = minItemType;
        this.minResistance = minResistance;
        this.resistanceMultiplier = resistanceMultiplier;
    }

    public Map<String, Object> getByMap(int playerMultiplier, ConvertType convertType) {
        List<String> itemList = new ArrayList<>(Arrays.asList("carbon", "stone", "iron", "gold", "netherite"));
        List<Object> multipliers = Repo.getMultipleConfig("es-upgrade-carbon-multiplier", "es-upgrade-stone-multiplier",
                "es-upgrade-iron-multiplier", "es-upgrade-gold-multiplier", "es-upgrade-netherite-multiplier");
        Map<String, Object> dataMap = new HashMap<>();
        int i = 0;
        for (String s : itemList) {
            dataMap.put(s + "Amount", Repo.getConfig(minItemType + s) + Repo.getConfig(minItemType + s) * (Math.round((int) multipliers.get(i) * 0.1) * (playerMultiplier - 1)));
            i++;
        }
        dataMap.put("before", Converter.formatValue(Repo.getConfig(minResistance) + Repo.getConfig(resistanceMultiplier) * playerMultiplier, convertType));
        dataMap.put("after", Converter.formatValue(Repo.getConfig(minResistance) + Repo.getConfig(resistanceMultiplier) * (playerMultiplier + 1), convertType));
        return dataMap;
    }

    public Map<String, Integer> getByMap(int playerMultiplier) {
        List<String> itemList = new ArrayList<>(Arrays.asList("carbon", "stone", "iron", "gold", "netherite"));
        List<Object> multipliers = Repo.getMultipleConfig("es-upgrade-carbon-multiplier", "es-upgrade-stone-multiplier",
                "es-upgrade-iron-multiplier", "es-upgrade-gold-multiplier", "es-upgrade-netherite-multiplier");
        Map<String, Integer> dataMap = new HashMap<>();
        int i = 0;
        for (String s : itemList) {
            dataMap.put(s + "Amount", (int) (Repo.getConfig(minItemType + s) + Repo.getConfig(minItemType + s) * (Math.round((int) multipliers.get(i) * 0.1) * (playerMultiplier - 1))));
            i++;
        }
        dataMap.put("before", Repo.getConfig(minResistance) + Repo.getConfig(resistanceMultiplier) * playerMultiplier);
        dataMap.put("after", Repo.getConfig(minResistance) + Repo.getConfig(resistanceMultiplier) * (playerMultiplier + 1));
        return dataMap;
    }


}
