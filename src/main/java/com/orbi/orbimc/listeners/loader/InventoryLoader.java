package com.orbi.orbimc.listeners.loader;

import com.orbi.orbimc.listeners.loader.root.Loader;
import com.orbi.orbimc.util.inventory.Inventory;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class InventoryLoader implements Loader {

    @Override
    public void load() {
        Reflections reflections = new Reflections("com.orbi.orbimc.systems");
        Set<Class<? extends Inventory>> classes = reflections.getSubTypesOf(Inventory.class);

        for (Class<? extends Inventory> cls : classes) {
            try {
                cls.getDeclaredConstructor().newInstance().initializeInventory();

            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
