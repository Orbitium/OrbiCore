package com.orbi.orbimc.villager;

import com.fasterxml.jackson.core.type.TypeReference;
import com.orbi.orbimc.OrbiCore;
import com.orbi.orbimc.systems.season.SeasonCycle;
import com.orbi.orbimc.util.StaticItems;
import org.bukkit.Material;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TradeItem {

    public static Map<String, Map<Integer, List<TradeItem>>> villagerTrades = new HashMap<>();
    public static Map<Integer, TradeItem> tradesWithID = new HashMap<>();

    public static void reloadGivens() {
        for (Map<Integer, List<TradeItem>> entry : villagerTrades.values()) {
            for (List<TradeItem> tradeItems : entry.values()) {
                for (TradeItem tradeItem : tradeItems) {
                    tradeItem.updateGiven();
                }
            }
        }
    }

    private static int lastID = -1;
    private final int id;
    private final ItemStack given;
    private final ItemStack received;
    private final int[] givenAmount;
    private final int increaseAmount;

    public TradeItem(int id, ItemStack given, ItemStack received, int[] givenAmount, int increaseGiven) {
        this.id = id;
        this.given = given;
        this.received = received;
        this.increaseAmount = increaseGiven;
        this.givenAmount = givenAmount;
    }

    public ItemStack getGiven() {
        return given;
    }

    public void updateGiven() {
        given.setAmount(givenAmount[SeasonCycle.season]);
    }

    public ItemStack getReceived() {
        return received;
    }

    public int getIncreaseAmount() {
        return increaseAmount;
    }

    public int getID() {
        return id;
    }

    public static TradeItem deserialize(String data) {
        String[] sp = data.split(" ");

        ItemStack given;
        ItemStack received;

        if (sp[0].equals("coin")) {
            given = StaticItems.setAmount(StaticItems.villagerCoin, Integer.parseInt(sp[2]));
            received = new ItemStack(Material.getMaterial(sp[1]));
        } else {
            given = new ItemStack(Material.getMaterial(sp[0]));
            received = StaticItems.setAmount(StaticItems.villagerCoin, Integer.parseInt(sp[2]));
        }

        int[] prices;
        int increaseGiven = Integer.parseInt(sp[7]);

        prices = new int[]{Integer.parseInt(sp[3]), Integer.parseInt(sp[4]), Integer.parseInt(sp[5]), Integer.parseInt(sp[6])};

        TradeItem tradeItem = new TradeItem(++lastID, given, received, prices, increaseGiven);

        tradesWithID.put(lastID, tradeItem);

        return tradeItem;
    }

    public static TradeItem getWithId(int id) {
        return tradesWithID.get(id);
    }


    public static void loadTradeItems() {
        File tradeItemFile = new File(OrbiCore.getInstance().getDataFolder() + "/Systems/VillagerTrade/Trades.yml");
        try {
            Map<String, Map<Integer, List<String>>> items = OrbiCore.getOB().readValue(tradeItemFile, new TypeReference<>() {
            });

            for (String key : items.keySet()) {
                Map<Integer, List<TradeItem>> map = new HashMap<>();
                for (Map.Entry<Integer, List<String>> entry : items.get(key).entrySet()) {
                    List<TradeItem> trades = new ArrayList<>();
                    for (String s : entry.getValue())
                        trades.add(deserialize(s));
                    map.put(entry.getKey(), trades);
                }
                villagerTrades.put(key, map);
                reloadGivens();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<TradeItem> getRandomTrade(String type, int level, int amount) {
        List<TradeItem> trades = new ArrayList<>();
        List<TradeItem> possibilities = villagerTrades.get(type).get(level);
        while (amount > 0) {
            TradeItem selected = possibilities.get(OrbiCore.getRandom().nextInt(possibilities.size()));
            if (!trades.contains(selected)) {
                trades.add(selected);
                amount -= 1;
            }
        }
        if (trades.isEmpty())
            throw new NullPointerException();
        return trades;
    }

    public static Villager.Profession getProfession(String s) {
        switch (s) {
            case "Çiftçi":
                return Villager.Profession.FARMER;
            case "Kasap":
                return Villager.Profession.BUTCHER;
            case "Aletçi": //Aletleri tamir edebilir, gelişmiş aletlerin kırılmaması için
                return Villager.Profession.TOOLSMITH;
            case "Kütüphaneci":
                return Villager.Profession.LIBRARIAN;
            case "Zırhçı":
                return Villager.Profession.ARMORER;
            case "Çoban":
                return Villager.Profession.SHEPHERD;
            case "Rahip":
                return Villager.Profession.CLERIC;
            case "Okçu":
                return Villager.Profession.FLETCHER;
            case "Mason":
                return Villager.Profession.MASON;
        }
        throw new NullPointerException();
    }


}
