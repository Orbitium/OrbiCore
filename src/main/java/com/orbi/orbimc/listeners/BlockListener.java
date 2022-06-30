package com.orbi.orbimc.listeners;

import com.orbi.orbimc.OrbiCore;
import com.orbi.orbimc.bone.logger.DiscordChannel;
import com.orbi.orbimc.systems.autopickup.Pickup;
import com.orbi.orbimc.systems.fly.FlyManager;
import com.orbi.orbimc.systems.season.farm.CropGrowController;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Sapling;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.block.BlockFertilizeEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class BlockListener implements Listener {

    @EventHandler
    public static void onBlockGrow(BlockGrowEvent e) {
        CropGrowController.handle(e);
    }

    @EventHandler
    public static void onBlockSpread(BlockSpreadEvent e) {
        CropGrowController.handle(e);
    }

}
