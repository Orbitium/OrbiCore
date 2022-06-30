package com.orbi.orbimc.commands.chat;

import com.orbi.orbimc.bone.chat.ChatController;
import com.orbi.orbimc.commands.root.Command;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class NearbyChatCommand extends Command {
    public NearbyChatCommand(JavaPlugin m) {
        super(m);
        addAlias("yakın");
        addAlias("yakin");
        setUsage("/yakın");
        setPermission(0);
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws Exception {
        ChatController.nearPlayer.add((Player) sender);
        sender.sendMessage(ChatColor.GREEN + "İşlem başarılı!");
    }
}
