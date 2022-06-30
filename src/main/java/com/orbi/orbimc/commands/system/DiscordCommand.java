package com.orbi.orbimc.commands.system;

import com.orbi.orbimc.commands.root.Command;
import com.orbi.orbimc.database.Repo;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class DiscordCommand extends Command {
    public DiscordCommand(JavaPlugin m) {
        super(m);
        addAlias("discord");
        setUsage("/discord");
        setPermission(0);
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws Exception {
        TextComponent msg = new TextComponent(Repo.getMSG("discord-link"));
        msg.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/MskC25B4dC"));
        sender.spigot().sendMessage(msg);
    }
}
