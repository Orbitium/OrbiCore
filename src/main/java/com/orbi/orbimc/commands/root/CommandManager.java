package com.orbi.orbimc.commands.root;

import com.orbi.orbimc.bone.chat.ChatController;
import com.orbi.orbimc.bone.security.authority.PermissionController;
import com.orbi.orbimc.database.Repo;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class CommandManager implements CommandExecutor {
    public static ArrayList<Command> commands;

    private JavaPlugin pl;

    public CommandManager(JavaPlugin m) {
        pl = m;
        commands = new ArrayList<>();

        CommandLoader.load();

        registerCommands();
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }

    public void registerCommands() {
        for (Command c : this.getCommands()) {
            try {
                for (String s : c.getAliases()) {
                    pl.getCommand(s).setExecutor(this);
                    if (c.getTabCompleter() != null)
                        pl.getCommand(s).setTabCompleter(c.getTabCompleter());
                }
            } catch (NullPointerException e) {
                throw new NullPointerException(c.getAliases().toString() + " komutunu yml eklemeyi unutmuşsun knk");
            }
        }
    }


    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        for (Command c : getCommands()) {
            if (c.getAliases().contains(label.toLowerCase())) {
                if (!c.isAvailable() && !sender.isOp()) {
                    sender.sendMessage(Repo.getMSG("unavailable-command"));
                    return false;
                } else if (!checkPerm(c, sender)) {
                    sender.sendMessage(Repo.getMSG("insufficient-permission"));
                    return false;
                }
                try {
                    c.execute(sender, args);
                } catch (Exception e) {
                    sender.sendMessage(Repo.getMSG("wrong-command-usage"));
                    sender.sendMessage(Repo.getMSG("true-command-usage") + c.getUsage());
                    e.printStackTrace();
                }
                return true;
            }
        }
        return false;
    }

    public static boolean checkPerm(Command c, CommandSender sender) {
        if ((sender.isOp()))
            return true;
        else
            return c.getMinPermission() <= PermissionController.getPermissionValue(sender.getName());
    }

    public static void onPreCommand(PlayerCommandPreprocessEvent e) {
        String[] message = e.getMessage().split(" ");
        String command = message[0];

        if (!ChatController.players.contains(e.getPlayer())) {
            if (!command.equals("/giriş") && !command.equals("/kayıt") && !command.equals("/giris") && !command.equals("/kayit"))
                e.setCancelled(true);
        }
    }

    public static void insufficientArgs(CommandSender s) {
        s.sendMessage(Repo.getMSG("cmd-insufficient-args"));
    }

}

