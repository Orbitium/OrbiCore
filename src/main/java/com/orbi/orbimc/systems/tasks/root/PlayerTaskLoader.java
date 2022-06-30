package com.orbi.orbimc.systems.tasks.root;


import com.orbi.orbimc.systems.tasks.PlayerTaskData;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class PlayerTaskLoader {

    public static void loadTasks() {
        Reflections reflections = new Reflections("com.orbi.orbimc.systems.tasks.tasklist");
        Set<Class<? extends PlayerTask>> classes = reflections.getSubTypesOf(PlayerTask.class);

        for (Class<? extends PlayerTask> cls : classes) {
            try {
                PlayerTask playerTask = cls.getDeclaredConstructor().newInstance();
                PlayerTaskData.taskList.put(playerTask.getTaskName(), playerTask);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

}
