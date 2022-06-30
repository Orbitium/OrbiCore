package com.orbi.orbimc.util;

import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Color {

    public static String translateColor(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    private static final Pattern pattern = Pattern.compile("(#[a-fA-F0-9]{6})");

    public static String translateHex(String message) {
        Matcher matcher = pattern.matcher(message); // Creates a matcher with the given pattern & message

        while (matcher.find()) { // Searches the message for something that matches the pattern
            String color = message.substring(matcher.start(), matcher.end()); // Extracts the color from the message
            message = message.replace(color, net.md_5.bungee.api.ChatColor.of(color) + "");
            matcher = pattern.matcher(message);
        }

        return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', message); // Returns the message
    }

}
