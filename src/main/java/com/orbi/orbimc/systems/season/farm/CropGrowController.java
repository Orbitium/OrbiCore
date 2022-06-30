package com.orbi.orbimc.systems.season.farm;

import com.orbi.orbimc.OrbiCore;
import com.orbi.orbimc.systems.season.SeasonCycle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CropGrowController {


    public static Map<Material, int[]> crops = new HashMap<>();

    public static void loadCorps() {
        try {
            List<String> data = OrbiCore.getOB().readValue(new File(OrbiCore.getInstance().getDataFolder() + "/Systems/SeasonData/Crops.yml"), List.class);
            for (String s : data) {
                String[] sp = s.split(" ");
                int[] possibilities = new int[5];
                for (int i = 1; i < sp.length; i++) {
                    possibilities[i - 1] = Integer.parseInt(sp[i]);
                }
                crops.put(Material.getMaterial(sp[0]), possibilities);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void handle(BlockGrowEvent e) {
        int r = OrbiCore.getRandom().nextInt(100);
        Bukkit.getScheduler().runTaskLater(OrbiCore.getInstance(), () -> {
            BlockState st = e.getNewState();

            int possibility = crops.get(st.getType())[SeasonCycle.season];
            if (possibility > 0) {
                if (possibility >= r) {
                    switch (st.getType()) {
                        case WHEAT:
                        case CARROTS:
                        case POTATOES:
                        case BEETROOTS:
                        case MELON_STEM:
                        case PUMPKIN_STEM:
                        case COCOA:
                        case SWEET_BERRY_BUSH:
                        case NETHER_WART:
                            Bukkit.getScheduler().runTaskLater(OrbiCore.getInstance(), () -> {
                                Ageable ageable = (Ageable) st.getBlockData();
                                if (ageable.getAge() != ageable.getMaximumAge()) {
                                    ageable.setAge(ageable.getAge() + 1);
                                }
                                st.setBlockData(ageable);
                            }, 10 * 20);
                            break;
                        case PUMPKIN:
                        case CACTUS:
                        case SUGAR_CANE:
                        case BAMBOO:
                        case KELP:
                            Location lc = st.getLocation();
                            if (lc.add(0, 1, 0).getBlock().isEmpty()) {
                                if (!st.getLocation().getBlock().isEmpty()) {
                                    Bukkit.getScheduler().runTaskLater(OrbiCore.getInstance(), () -> {
                                        lc.getBlock().setType(e.getBlock().getType());
                                    }, 10 * 20);
                                    return;
                                }
                            }
                            st.getWorld().dropItemNaturally(st.getLocation(), new ItemStack(e.getBlock().getType()));
                    }
                }
            } else {
                if (Math.abs(possibility) >= r) {
                    switch (st.getType()) {
                        case WHEAT:
                        case CARROTS:
                        case POTATOES:
                        case BEETROOTS:
                        case MELON_STEM:
                        case PUMPKIN_STEM:
                        case COCOA:
                        case SWEET_BERRY_BUSH:
                        case NETHER_WART:
                            Ageable ageable = (Ageable) st.getBlockData();
                            if (ageable.getAge() != ageable.getMaximumAge()) {
                                ageable.setAge(ageable.getAge() - 1);
                            }
                            st.setBlockData(ageable);
                            break;
                        case PUMPKIN:
                        case CACTUS:
                        case SUGAR_CANE:
                        case BAMBOO:
                        case KELP:
                            st.getLocation().getBlock().setType(Material.AIR);
                            break;
                    }
                }
            }
        }, 1L);
    }
}
