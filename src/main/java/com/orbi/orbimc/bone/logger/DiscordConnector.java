package com.orbi.orbimc.bone.logger;

import com.orbi.orbimc.bone.security.account.PlayerAccount;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

import javax.security.auth.login.LoginException;
import java.awt.*;


public class DiscordConnector {

    public static JDA jda;

    public DiscordConnector() {
        if (PlayerAccount.isTest == 1)
            return;
        try {
            jda = JDABuilder.createDefault("NzYxNDY0NDgxMjA4ODYwNzEy.X3a_Dw.l8tyMmyfkV5RGqdol0Etit_nGao").build();
            jda.awaitReady();
            jda.addEventListener(new ChatListener());
        } catch (InterruptedException | LoginException e) {
            e.printStackTrace();
        }
    }

    public void print(MessageEmbed msg, DiscordChannel dc) {
        if (PlayerAccount.isTest == 1)
            return;
        TextChannel txtChannel = jda.getTextChannelById(dc.getChannelID());
        txtChannel.sendMessageEmbeds(msg).queue();
    }
}
