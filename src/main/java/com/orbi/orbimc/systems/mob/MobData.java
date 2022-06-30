package com.orbi.orbimc.systems.mob;

import com.orbi.orbimc.OrbiCore;
import com.orbi.orbimc.database.Cache;
import com.orbi.orbimc.database.MongoBase;
import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.database.data.Data;
import com.orbi.orbimc.database.data.DataContainer;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class MobData implements DataContainer {

    public static class MobItem {
        private final Material m;
        private final int cost;
        private String name;

        public MobItem(Material m, int cost) {
            this.m = m;
            this.cost = cost;
        }

        public MobItem(Material m, int cost, String name) {
            this.m = m;
            this.cost = cost;
            this.name = name;
        }

        public Material getM() {
            return m;
        }

        public int getCost() {
            return cost;
        }

        public String getName() {
            return name;
        }
    }

    public static List<MobItem> mobItems = new ArrayList<>();

    public static void loadMobDataPrices() {
        try {
            List<String> sm = OrbiCore.getOB().readValue(new File(OrbiCore.getInstance().getDataFolder() + "/Systems/MobData/Items.yml"), List.class);
            for (String s : sm) {
                String[] sp = s.split("\\.");
                if (sp.length == 2)
                    mobItems.add(new MobItem(Material.getMaterial(sp[0]), Integer.parseInt(sp[1])));
                else
                    mobItems.add(new MobItem(Material.getMaterial(sp[0]), Integer.parseInt(sp[1]), sp[2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void loadData() {
        new Data("soulPoint", 0.0D);
    }
}
