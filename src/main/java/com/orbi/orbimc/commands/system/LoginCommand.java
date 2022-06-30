package com.orbi.orbimc.commands.system;

import com.orbi.orbimc.bone.chat.ChatController;
import com.orbi.orbimc.bone.security.account.PlayerAccount;
import com.orbi.orbimc.commands.root.Command;
import com.orbi.orbimc.database.MongoBase;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class LoginCommand extends Command {

    public LoginCommand(JavaPlugin m) {
        super(m);
        addAlias("giriş");
        addAlias("giris");
        setUsage("/giriş <şifre>");
        setPermission(0);
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws Exception {
        Player player = (Player) sender;
    }
}
