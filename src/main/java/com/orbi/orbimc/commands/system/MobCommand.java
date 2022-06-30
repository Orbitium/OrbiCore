package com.orbi.orbimc.commands.system;

import com.orbi.orbimc.commands.root.Command;
import com.orbi.orbimc.util.inventory.InventoryManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MobCommand extends Command {

    public MobCommand(JavaPlugin m) {
        super(m);
        addAlias("yaratık");
        addAlias("yaratik");
        setUsage("/yaratık");
        setPermission(0);
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws Exception {
        InventoryManager.open((Player) sender, "buy-mob-item-title");
    }
}