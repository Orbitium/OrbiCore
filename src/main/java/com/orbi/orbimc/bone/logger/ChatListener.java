package com.orbi.orbimc.bone.logger;

import com.orbi.orbimc.OrbiCore;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class ChatListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getChannel().getIdLong() == DiscordChannel.COMMAND.getChannelID()) {
            if (event.getAuthor().getIdLong() == 412604073737715713L) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(OrbiCore.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), event.getMessage().getContentRaw());
                    }
                },1L);
            }
        }
    }
}
