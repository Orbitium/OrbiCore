package com.orbi.orbimc.systems.playeritem;

import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.util.StringParser;

import java.util.HashMap;
import java.util.Map;

public class PlayerItemNameConverter {

    static Map<String, String> nameMap = new HashMap<>();

    public static void loadNameMap() {
        nameMap.put("wood", "odun");
        nameMap.put("stone", "taş");
        nameMap.put("iron", "demir");
        nameMap.put("gold", "altın");
        nameMap.put("netherite", "netherite");
    }

    public static String convert(String name) {
        loadNameMap();
        return StringParser.parse(Repo.getMSG("is-insufficient-item"), nameMap.get(name));
    }

}
