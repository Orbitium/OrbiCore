package com.orbi.orbimc.systems.tasks.root;

import com.orbi.orbimc.database.Repo;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class PlayerTask {
    private String taskName;
    private String howToComplete;
    private int taskLevel;
    private ItemStack[] taskAward;
    private Material representative;
    private String doneMessage;


    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setHowToComplete(String howToComplete) {
        this.howToComplete = howToComplete;
    }

    public void setTaskLevel(int taskLevel) {
        this.taskLevel = taskLevel;
    }

    public void setTaskAward(ItemStack... taskAward) {
        this.taskAward = taskAward;
    }

    public void setRepresentative(Material representative) {
        this.representative = representative;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getHowToComplete() {
        return howToComplete;
    }

    public int getTaskLevel() {
        return taskLevel;
    }

    public ItemStack[] getTaskAward() {
        return taskAward;
    }

    public Material getRepresentative() {
        return representative;
    }

    public String getDoneMessage() {
        return doneMessage;
    }

    public void setDoneMessage(String doneMessage) {
        this.doneMessage = Repo.getMSG("ts-name") + doneMessage;
    }

    public PlayerTask() {

    }

    public abstract void check(Player player);
}
