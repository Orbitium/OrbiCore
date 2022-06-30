package com.orbi.orbimc.database.data;

import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class DataLoader {

    public static void load() {
        Reflections reflections = new Reflections("com.orbi.orbimc.systems");
        Set<Class<? extends DataContainer>> classes = reflections.getSubTypesOf(DataContainer.class);
        for (Class<?> cls : classes) {
            try {
                cls.getMethod("loadData").invoke(cls.getConstructor().newInstance());
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}
