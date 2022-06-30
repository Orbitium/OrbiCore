package com.orbi.orbimc.util;

import com.orbi.orbimc.database.Repo;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class InfoBuilder {

    public static void buildInfo(Player player, String infoText) {
        ItemStack itemStack = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) itemStack.getItemMeta();
        bookMeta.setTitle("Bilgi");
        bookMeta.setAuthor("OrbiCore");
        bookMeta.setPages(Repo.getInfo(infoText));
        bookMeta.setGeneration(BookMeta.Generation.ORIGINAL);
        itemStack.setItemMeta(bookMeta);
        player.openBook(itemStack);
    }

    public static void buildInfo(HumanEntity player, String infoText) {
        ItemStack itemStack = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) itemStack.getItemMeta();
        bookMeta.setTitle("Bilgi");
        bookMeta.setAuthor("OrbiCore");
        bookMeta.setPages(Repo.getInfo(infoText));
        bookMeta.setGeneration(BookMeta.Generation.ORIGINAL);
        itemStack.setItemMeta(bookMeta);
        ((Player) player).openBook(itemStack);
    }


}
