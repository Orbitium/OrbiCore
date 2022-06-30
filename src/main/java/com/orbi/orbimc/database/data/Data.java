package com.orbi.orbimc.database.data;

import com.orbi.orbimc.database.MongoBase;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class Data {
    public Data(String dataName, Object minDataValue) {
        MongoBase.dataList.put(dataName, minDataValue);
    }

    public Data(String dataName, Object minDataValue, Consumer<Player> trigger) {
        MongoBase.dataList.put(dataName, minDataValue);
        MongoBase.updateTriggers.put(dataName, trigger);
    }
}
