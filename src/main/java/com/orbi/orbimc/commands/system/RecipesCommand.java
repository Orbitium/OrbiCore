package com.orbi.orbimc.commands.system;

import com.orbi.orbimc.commands.root.Command;
import com.orbi.orbimc.systems.customcraft.RecipeController;
import com.orbi.orbimc.systems.generator.GeneratorGUI;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class RecipesCommand extends Command {

    public RecipesCommand(JavaPlugin m) {
        super(m);
        addAlias("üretimler");
        addAlias("uretimler");
        setUsage("/üretim");
        setPermission(0);
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws Exception {
        ((Player) sender).discoverRecipes(RecipeController.recipes);
        sender.sendMessage(ChatColor.GREEN + "İşlem başarılı!");
    }
}
