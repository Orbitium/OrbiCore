package com.orbi.orbimc.commands.root;

import com.orbi.orbimc.OrbiCore;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class CommandLoader {

    public static void load() {
        Reflections reflections = new Reflections("com.orbi.orbimc");
        Set<Class<? extends Command>> classes = reflections.getSubTypesOf(Command.class);

        for (Class<? extends Command> cls : classes) {
            try {
                CommandManager.commands.add(cls.getDeclaredConstructor(JavaPlugin.class).newInstance(OrbiCore.getInstance()));
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }


}
