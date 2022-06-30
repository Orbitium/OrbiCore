package com.orbi.orbimc.commands.root;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ArgumentCompleter implements TabCompleter {

    Map<Integer, List<String>> arguments = new HashMap<>();

    public ArgumentCompleter set(int arg, String... value) {
        arguments.put(arg, List.of(value));
        return this;
    }

    public ArgumentCompleter add(int arg, String... value) {
        arguments.get(arg).addAll(List.of(value));
        return this;
    }

    public ArgumentCompleter set(int arg, Collection<? extends Player> players) {
        List<String> playerNames = new ArrayList<>();
        for (Player p : players)
            playerNames.add(p.getName());
        arguments.put(arg, playerNames);
        return this;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return arguments.get(args.length - 1);
    }
}
