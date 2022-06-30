package com.orbi.orbimc.systems.customcraft;

import com.orbi.orbimc.OrbiCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;


import java.util.Map;

public class CustomShapedRecipe {

    NamespacedKey namespacedKey;
    String[] shape;
    String needs;
    ItemStack result;

    public CustomShapedRecipe(String namespacedKey, String shape, String needs, ItemStack result) {
        this.namespacedKey = new NamespacedKey(OrbiCore.getInstance(), namespacedKey);
        this.shape = shape.split(" ");
        this.needs = needs;
        this.result = result;
        buildCraft();
    }

    public CustomShapedRecipe(String namespacedKey, String shape, String needs, Material result) {
        this.namespacedKey = new NamespacedKey(OrbiCore.getInstance(), namespacedKey);
        this.shape = shape.split(" ");
        this.needs = needs;
        this.result = new ItemStack(result);
        buildCraft();
    }


    void buildCraft() {
        ShapedRecipe shapedRecipe = new ShapedRecipe(namespacedKey, result);
        shapedRecipe.shape(shape[0], shape[1], shape[2]);


        String[] splittedNeeds = needs.split(" ");

        for (String splittedNeed : splittedNeeds) {
            shapedRecipe.setIngredient(splittedNeed.charAt(0), Material.getMaterial(splittedNeed.substring(1)));
        }

        RecipeController.recipes.add(namespacedKey);
        Bukkit.addRecipe(shapedRecipe);
    }
}
