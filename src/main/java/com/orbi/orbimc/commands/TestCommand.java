package com.orbi.orbimc.commands;

import com.orbi.orbimc.OrbiCore;
import com.orbi.orbimc.commands.root.Command;
import com.orbi.orbimc.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class TestCommand extends Command {
    public TestCommand(JavaPlugin m) {
        super(m);
        addAlias("test");
        setUsage("/test");
        setPermission(0);
        setAvailable(false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws Exception {
        Player p = (Player) sender;
        /*
        Entity entity = p.getWorld().spawnEntity(p.getLocation(), EntityType.CHICKEN);
        entity.setCustomName(ChatColor.GRAY + "[Kömür tavuğu]" + ChatColor.YELLOW + " - " + ChatColor.AQUA + "[Durum: aç]");

        EntityChicken c = ((CraftChicken) entity).getHandle();

        entity.addScoreboardTag("0");

        c.bZ = 0;

        ItemBuilder ib = new ItemBuilder(Material.DIAMOND_SWORD);
        ib.setLore(new ArrayList<>(Collections.singletonList(
                StringParser.parse(Repo.getText("item-progress-name-format"), Color.translateHex("#4fd2d6Yaratık avcısı")))));
        ib.setDamage(3000);
        p.getInventory().addItem(ib.build());
        */

        //InventoryManager.open(p, "buy-mob-item-title");
        /*
        int encType = OrbiCore.getRandom().nextInt(Enchantment.values().length);

        ItemStack is = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta im = is.getItemMeta();
        im.addEnchant(Enchantment.values()[encType], 1, false);
        is.setItemMeta(im);

        p.getInventory().addItem(is);

        PersistentDataContainer pdc = p.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer();
        System.out.println(pdc.getKeys());*/



    }
}
