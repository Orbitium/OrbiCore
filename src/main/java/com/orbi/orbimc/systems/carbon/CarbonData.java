package com.orbi.orbimc.systems.carbon;

import com.fasterxml.jackson.core.type.TypeReference;
import com.orbi.orbimc.OrbiCore;
import com.orbi.orbimc.bone.tab.scoreboard.ScoreboardUpdater;
import com.orbi.orbimc.database.Cache;
import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.database.data.Data;
import com.orbi.orbimc.database.data.DataContainer;
import com.orbi.orbimc.util.ItemBuilder;
import com.orbi.orbimc.util.StringParser;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarbonData implements DataContainer {

    public static class CarbonItem {

        Material representative;
        String friendlyName;
        int slot;
        int amount;
        int contents;
        long energyCost = 0L;

        public CarbonItem(String friendlyName, int slot, int amount, int contents) {
            this.friendlyName = friendlyName;
            this.slot = slot;
            this.amount = amount;
            this.contents = contents;
        }

        public CarbonItem(String friendlyName, int slot, int amount, int contents, int energyCost) {
            this.friendlyName = friendlyName;
            this.slot = slot;
            this.amount = amount;
            this.contents = contents;
            this.energyCost = energyCost;
        }

        public CarbonItem() {
        }

        public ItemStack build(Material representative) {
            this.representative = representative;
            List<String> lore = new ArrayList<>();
            lore.add(StringParser.parse(Repo.getText("cs-ci-lore0"), contents));
            if (energyCost != 0)
                lore.add(StringParser.parse(Repo.getText("cs-ci-lore1"), energyCost));
            ItemStack i = new ItemBuilder(representative, amount).setLore(lore).build();
            return i;
        }

        public Material getRepresentative() {
            return representative;
        }

        public int getSlot() {
            return slot;
        }

        public int getAmount() {
            return amount;
        }

        public int getContents() {
            return contents;
        }

        public long getEnergyCost() {
            return energyCost;
        }

        public String getFriendlyName() {
            return friendlyName;
        }
    }

    public static Map<String, CarbonItem> itemToCarbonValues;
    private static final Map<String, CarbonItem> withFriendlyName = new HashMap<>();

    @Override
    public void loadData() {
        new Data("availableCarbon", 0L, ScoreboardUpdater::addRequest);

        File itemFile = new File(OrbiCore.getInstance().getDataFolder() + "/Systems/CarbonData/CarbonItem.yml");

        try {
            itemToCarbonValues = OrbiCore.getOB().readValue(itemFile, new TypeReference<Map<String, CarbonItem>>() {
            });

            for (CarbonItem c : itemToCarbonValues.values()) {
                withFriendlyName.put(c.getFriendlyName(), c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static long getCarbon(Player player) {
        return (long) Cache.getAsLong(player.getName(), "availableCarbon");
    }

    public static CarbonItem getCarbonItem(Material itemType) {
        return itemToCarbonValues.get(itemType.name());
    }

    public static CarbonItem getCarbonItem(String friendlyName) {
        return withFriendlyName.get(friendlyName.substring(1));
    }

}
