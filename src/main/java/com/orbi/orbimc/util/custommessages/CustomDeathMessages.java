package com.orbi.orbimc.util.custommessages;

import org.bukkit.ChatColor;
import org.bukkit.event.entity.PlayerDeathEvent;

public class CustomDeathMessages {

    public static void sendCustomDeathMessages(PlayerDeathEvent e) {
        switch (e.getEntity().getLastDamageCause().getCause()) {
            case FALL:
                e.setDeathMessage(e.getEntity().getPlayerListName() + ChatColor.GRAY + " adlı oyuncu uçmayı denedi ama yere çakıldı!");
                break;
            case CONTACT:
                e.setDeathMessage(e.getEntity().getPlayerListName() + ChatColor.GRAY + " adlı oyuncu bitki dikenlerine sarılmaya çalışarak öldü!");
                break;
            case DROWNING:
                e.setDeathMessage(e.getEntity().getPlayerListName() + ChatColor.GRAY + " adlı oyuncu dalış kıyafetlerini unutarak suda boğuldu.");
                break;
            case FALLING_BLOCK:
                e.setDeathMessage(e.getEntity().getPlayerListName() + ChatColor.GRAY + " adlı oyuncunun kafasına sert bir blok darbesi inerek öldü!");
                break;
            case HOT_FLOOR:
                e.setDeathMessage(e.getEntity().getPlayerListName() + ChatColor.GRAY + " adlı oyuncunun ayakları yanarak öldü!");
                break;
            case LAVA:
                e.setDeathMessage(e.getEntity().getPlayerListName() + ChatColor.GRAY + " adlı oyuncu lava hünerlerini göstermeye çalışırken öldü!");
                break;
            case FLY_INTO_WALL:
                e.setDeathMessage(e.getEntity().getPlayerListName() + ChatColor.GRAY + " adlı oyuncunun kafasını sert bir yüzeye çarparak öldü!.");
                break;
            case FIRE:
            case FIRE_TICK:
                e.setDeathMessage(e.getEntity().getPlayerListName() + ChatColor.GRAY + " adlı oyuncu ateşini söndüremeyerek öldü.");
                break;
            case CRAMMING:
                e.setDeathMessage(e.getEntity().getPlayerListName() + ChatColor.GRAY + " adlı oyuncu hareket edecek yer bulamadığı için sıkışarak öldü.");
                break;
            case ENTITY_ATTACK:
            case ENTITY_SWEEP_ATTACK:
            case ENTITY_EXPLOSION:
                e.setDeathMessage(e.getEntity().getPlayerListName() + ChatColor.GRAY + " adlı oyuncunun artık yaratıklarla kötü bir geçmişi var...");
        }
    }

}
