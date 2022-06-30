package com.orbi.orbimc.systems.carbon;

import com.orbi.orbimc.database.Repo;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CarbonObserver {

    public static void convertOneType(Player player) {
        CarbonData.CarbonItem carbonItem = CarbonData.getCarbonItem(player.getInventory().getItemInMainHand().getType());
        int multiplier = player.getInventory().getItemInMainHand().getAmount() / (carbonItem == null ? 99 : carbonItem.getAmount());
        convert(player, multiplier, carbonItem);
    }

    public static void convertOneType(Player player, int amount) {
        CarbonData.CarbonItem carbonItem = CarbonData.getCarbonItem(player.getInventory().getItemInMainHand().getType());
        if (player.getInventory().getItemInMainHand().getAmount() <= amount || player.getInventory().getItemInMainHand().getAmount() <= 64)
            convert(player, amount, carbonItem);
        else
            player.sendMessage(Repo.getMSG("cs-to-many-item"));
    }

    public static void convertSameType(Player player) {
        Material mType = player.getInventory().getItemInMainHand().getType();
        CarbonData.CarbonItem carbonItem = CarbonData.getCarbonItem(mType);

        if (carbonItem == null) {
            player.sendMessage(Repo.getMSG("cs-null-item"));
            return;
        }

        int convertReturn;

        for (ItemStack i : player.getInventory().getContents()) {
            if (i.getType() == mType) {

            }
        }

    }

    public static void convert(Player player, int convertMultiplier, CarbonData.CarbonItem carbonItem) {
        /*
        ItemStack playerItem = player.getInventory().getItemInMainHand();

        if (carbonItem == null) {
            player.sendMessage(Repo.getMSG("cs-null-item"));
            return;
        } else if (convertMultiplier == 0) {
            player.sendMessage(Repo.getMSG("cs-insufficient-item"));
            return;
        } else if (carbonItem.getEnergyCost() * convertMultiplier > Cache.getAsLong(player, "storedEnergy")) {
            player.sendMessage(Repo.getMSG("cs-insufficient-energy"));
            return;
        }

        player.getInventory().getItemInMainHand().setAmount(playerItem.getAmount() - carbonItem.getAmount() * convertMultiplier);
        Cache.increaseLongValue(player, "availableCarbon", carbonItem.getContents() * (long) convertMultiplier);
        if (carbonItem.getEnergyCost() > 0) {
            Cache.decreaseLongValue(player, "storedEnergy", carbonItem.getEnergyCost() * (long) convertMultiplier);
            player.sendMessage("işlem başarılı, enerji gitti");
            return;
        }
        player.sendMessage("işlem başarılı");

         */
    }
}
