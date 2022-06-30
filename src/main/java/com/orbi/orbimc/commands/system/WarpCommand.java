package com.orbi.orbimc.commands.system;

import com.orbi.orbimc.bone.warps.Warp;
import com.orbi.orbimc.commands.root.Command;
import com.orbi.orbimc.commands.root.CommandManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class WarpCommand extends Command {
    public WarpCommand(JavaPlugin m) {
        super(m);
        addAlias("warp");
        setUsage("/warp <bilgi/hayvan/mob>");
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws Exception {
        if (args.length < 1) {
            CommandManager.insufficientArgs(sender);
            return;
        }
        else ((Player) sender).teleport(Warp.warps.get(args[0]));
    }
}
