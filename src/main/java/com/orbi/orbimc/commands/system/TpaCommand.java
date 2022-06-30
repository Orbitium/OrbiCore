package com.orbi.orbimc.commands.system;

import com.orbi.orbimc.bone.warps.Warp;
import com.orbi.orbimc.commands.root.Command;
import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.systems.energy.EnergyGUI;
import com.orbi.orbimc.util.tpacontroller.TpaController;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class TpaCommand extends Command {

    public TpaCommand(JavaPlugin m) {
        super(m);
        addAlias("tpa");
        setUsage("/tpa");
        setPermission(0);
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws Exception {
        if (args.length < 1) {
            sender.sendMessage("Yetersiz argüman");
        } else if (args.length == 2 && args[0].equals("gönder") || args.length == 2 && args[0].equals("gonder"))
            TpaController.sendTpaRequest((Player) sender, Bukkit.getPlayer(args[1]));
        else if (args.length == 1 & args[0].equals("kabul"))
            TpaController.confirmRequest((Player) sender);
    }
}
