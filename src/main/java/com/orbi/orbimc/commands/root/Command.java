package com.orbi.orbimc.commands.root;


import com.orbi.orbimc.util.Color;
import com.orbi.orbimc.util.inventory.InventoryManager;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class Command {

    protected JavaPlugin pl;

    private final List<String> aliases = new ArrayList<>();
    private int minPermission;
    private String usage;
    private String info;
    private boolean isAvailable = true;
    private TabCompleter tabCompleter;

    public Command(JavaPlugin m) {
        pl = m;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void addAlias(String a) {
        aliases.add(a);
    }

    public void setPermission(int permLevel) {
        minPermission = permLevel;
    }

    public int getMinPermission() {
        return minPermission;
    }

    public String getUsage() {
        return usage;
    }

    public String getInfo() {
        return info;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public void setInfo(String info) {
        this.info = Color.translateHex(info);
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public TabCompleter getTabCompleter() {
        return tabCompleter;
    }

    public void setTabCompleter(TabCompleter tabCompleter) {
        this.tabCompleter = tabCompleter;
    }

    public abstract void execute(CommandSender sender, String[] args) throws Exception;
}

