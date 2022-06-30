package com.orbi.orbimc.commands.chat;

import com.orbi.orbimc.bone.chat.ChatChannel;
import com.orbi.orbimc.bone.chat.ChatController;
import com.orbi.orbimc.commands.root.Command;
import com.orbi.orbimc.database.Repo;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class GeneralChatCommand extends Command {
    public GeneralChatCommand(JavaPlugin m) {
        super(m);
        addAlias("genel");
        setUsage("/genel");
        setPermission(0);
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws Exception {
        ChatController.nearPlayer.remove((Player) sender);
        sender.sendMessage(ChatColor.GREEN + "İşlem başarılı!");
    }
}