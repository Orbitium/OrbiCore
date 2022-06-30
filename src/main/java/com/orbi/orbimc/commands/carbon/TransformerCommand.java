package com.orbi.orbimc.commands.carbon;

import com.orbi.orbimc.commands.root.Command;
import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.util.inventory.InventoryManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TransformerCommand extends Command {
    public TransformerCommand(JavaPlugin m) {
        super(m);
        addAlias("ayrıştırıcı");
        addAlias("ayristirici");
        addAlias("a");
        setUsage("/ayrıştırıcı");
        setPermission(0);
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws Exception {
        InventoryManager.open((Player) sender, "transformer-title");
    }
}