package com.orbi.orbimc.commands.needop;

import com.orbi.orbimc.OrbiCore;
import com.orbi.orbimc.commands.root.Command;
import com.orbi.orbimc.database.Repo;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InfoCommand extends Command {

    public InfoCommand(JavaPlugin m) {
        super(m);
        addAlias("infoadd");
        setUsage("/info");
        setPermission(9999);
        setAvailable(false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws Exception {
        ItemStack i = ((Player) sender).getInventory().getItemInMainHand();
        BookMeta bm = (BookMeta) i.getItemMeta();

        File configFile = new File(OrbiCore.getInstance().getDataFolder() + "/Systems/CarbonData/info.yml");

        Repo.infoCache.put(bm.getTitle(), bm.getPages());

        try {
            OrbiCore.getOB().writeValue(configFile, Repo.infoCache);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
