package com.orbi.orbimc.bone.warps;

import com.orbi.orbimc.OrbiCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Warp {

    public static Map<String, Location> warps = new HashMap<>();

    public static void loadLocations() {
        try {
            Map<String, String> locations = OrbiCore.getOB().readValue(new File(OrbiCore.getInstance().getDataFolder() + "/Database/Warp/Warps.yml"), Map.class);
            for (Map.Entry<String, String> entry : locations.entrySet()) {
                String[] c = entry.getValue().split(" ");
                warps.put(entry.getKey(), new Location(Bukkit.getWorld(c[0]), Double.parseDouble(c[1]), Double.parseDouble(c[2]), Double.parseDouble(c[3])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
