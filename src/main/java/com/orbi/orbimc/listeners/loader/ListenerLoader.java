package com.orbi.orbimc.listeners.loader;

import com.orbi.orbimc.OrbiCore;
import com.orbi.orbimc.commands.root.Command;
import com.orbi.orbimc.commands.root.CommandManager;
import com.orbi.orbimc.listeners.loader.root.Loader;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ListenerLoader implements Loader {

    @Override
    public void load() {
        Reflections reflections = new Reflections("com.orbi.orbimc.listeners");
        Set<Class<? extends Listener>> classes = reflections.getSubTypesOf(Listener.class);

        for (Class<? extends Listener> cls : classes) {
            try {
                OrbiCore.getInstance().getServer().getPluginManager().registerEvents(cls.getDeclaredConstructor().newInstance(), OrbiCore.getInstance());
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
