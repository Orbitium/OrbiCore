package com.orbi.orbimc.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class MongoBase {
    public static MongoClient client;
    public static MongoDatabase database;
    public static MongoCollection<Document> collection;

    public static void initializeDB() {
        MongoClientURI uri = new MongoClientURI("mongodb://localhost:27017/admin");
        client = new MongoClient(uri);
        MongoClientOptions.builder().connectTimeout(Integer.MAX_VALUE);
        database = client.getDatabase("orbibase");
        collection = database.getCollection("test");
    }

    public static Map<String, Object> dataList = new HashMap<>();
    public static Map<String, Consumer<Player>> updateTriggers = new HashMap<>();

    public static void createUserData(String playerName) {
        Document user = new Document("_id", playerName);
        for (Map.Entry<String, Object> singleData : dataList.entrySet()) {
            user.append(singleData.getKey(), singleData.getValue());
        }
                /*
                .append("availableCarbon", 0)
                .append("carbonSystemEfficiency", 0)
                .append("totalCarbon", 0)
                .append("permission", 0)
                .append("password", null)
                .append("cloudFarmSystem", CloudFarmData.cratePlayerData())
                .append("elementSystem", ElementData.createPlayerData())
                .append("energySystem", EnergyData.createPlayerData())
                .append("itemSystem", PlayerItemData.createPlayerData())
                .append("availableRhodium", RhodiumData.createPlayerData())
                .append("lastRhodiumCollect", TimeController.dateToTimeValue())
                .append("taskLevel", PlayerTaskData.cratePlayerData())
                .append("availableFlyTime", FlyData.createPlayerData());
                 */
        collection.insertOne(user);
    }

    public static FindIterable<Document> getPlayerData(String playerName) {
        return collection.find(Filters.eq("_id", playerName));
    }

    public static void checkField(String id, String fieldName) {
        if (collection.find(Filters.eq("_id", id)).filter(Filters.exists(fieldName)).first() == null) {
            Document doc = new Document().append("_id", id);
            Document updatedDoc = new Document().append("$set", new Document().append(fieldName, null));
            collection.updateMany(doc, updatedDoc);
        }
    }

    public static Object getValue(Player player, String field) {
        FindIterable<Document> data = collection.find(Filters.eq("_id", player.getUniqueId().toString()));
        checkField(player.getUniqueId().toString(), field);
        for (Document value : data) {
            return (value.get(field));
        }
        return null;
    }

    public static Object getValue(String id, String field) {
        FindIterable<Document> data = collection.find(Filters.eq("_id", id));
        checkField(id, field);
        for (Document value : data) {
            return (value.get(field));
        }
        throw new NullPointerException(data + " database üzerinde bulunamadı");
    }

    public static Object getValue(Player player, String field, Object ifNull) {
        FindIterable<Document> data = collection.find(Filters.eq("_id", player.getUniqueId().toString()));
        checkField(player.getUniqueId().toString(), field);
        for (Document value : data) {
            if (value.get(field) != null)
                return (value.get(field));
        }
        collection.updateOne(Filters.eq("_id", player.getUniqueId().toString()), Updates.set(field, ifNull));
        return ifNull;
    }

    public static void setValue(Player player, String valueName, Object value) {
        checkField(player.getUniqueId().toString(), valueName);
        collection.updateOne(Filters.eq("_id", player.getUniqueId().toString()), Updates.set(valueName, value));
    }

    public static void setValue(String id, String valueName, Object value) {
        checkField(id, valueName);
        collection.updateOne(Filters.eq("_id", id), Updates.set(valueName, value));
    }

}