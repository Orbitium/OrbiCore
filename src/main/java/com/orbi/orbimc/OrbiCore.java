package com.orbi.orbimc;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orbi.orbimc.bone.chat.ChatController;
import com.orbi.orbimc.bone.logger.DiscordConnector;
import com.orbi.orbimc.bone.security.account.PlayerAccount;
import com.orbi.orbimc.bone.warps.Warp;
import com.orbi.orbimc.commands.root.CommandManager;
import com.orbi.orbimc.database.Cache;
import com.orbi.orbimc.database.MongoBase;
import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.database.YAMLBase;
import com.orbi.orbimc.database.data.DataLoader;
import com.orbi.orbimc.listeners.loader.InventoryLoader;
import com.orbi.orbimc.bone.tab.Tab;
import com.orbi.orbimc.listeners.loader.ListenerLoader;
import com.orbi.orbimc.systems.customcraft.crafts.Crafts;
import com.orbi.orbimc.systems.playeritem.PlayerItemData;
import com.orbi.orbimc.systems.season.SeasonCycle;
import com.orbi.orbimc.systems.season.farm.CropGrowController;
import com.orbi.orbimc.systems.tasks.root.PlayerTaskLoader;
import com.orbi.orbimc.util.StaticItems;
import com.orbi.orbimc.villager.Parchment;
import com.orbi.orbimc.villager.TradeItem;
import com.orbi.orbimc.villager.VillagerIQ;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;
import java.util.Random;

public final class OrbiCore extends JavaPlugin {
    private static OrbiCore orbicore;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Random random = new Random();
    private static DiscordConnector dc;
    private static final InventoryHolder inventoryHolder = () -> null;
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.##");

    private static final SeasonCycle seasonCycle = new SeasonCycle();

    //TODO -> HER SİSTEMİN KENDİ KLASÖRÜ VE BU KLASÖRÜN İÇERİSİNDE KENDİNE AİT CONFİG DOSYASI BULUNACAK
    //TODO -> SİSTEMLERİN KENDİ KLASÖRLERİ İÇERİSİNDE ONLARA AİT MESAJLAR VE SUNUCUNUN KULLANDIĞI TEXTLER OLACAK
    //TODO -> CONFİG, MESSAGES GİBİ YML DOSYALARI TAMAMEN SİLİNİP YUKARIDAKİ SİSTEME GEÇİLECEK
    //TODO -> OrbiCore/Text GİBİ KLASÖRLER SİLİNECEK(içindekileri almayı unutmadan)

    // FIXME: 4.04.2022 database işlemlerinde tetiklenmesi gereken fonksiyonlar, tetiklenmiyor, karbon panelleri bitirilecek

    @Override
    public void onEnable() {
        orbicore = this;
        objectMapper.configure(JsonParser.Feature.ALLOW_YAML_COMMENTS, true);
        Warp.loadLocations();
        //CarbonData.loadItemValues();
        MongoBase.initializeDB();
        YAMLBase.loadValues();
        StaticItems.loadItems();
        Tab.initializeTabConfig();
        //DiscordConnection.initializeConnection();
        new CommandManager(this);

        Repo.loadAll();

        DataLoader.load();

        new InventoryLoader().load();
        new ListenerLoader().load();

        PlayerTaskLoader.loadTasks();
        PlayerItemData.loadItemData();


        Crafts.load();
        Parchment.loadCrafts();
        TradeItem.loadTradeItems();

        seasonCycle.startCycle();
        CropGrowController.loadCorps();


        if (Bukkit.getOnlinePlayers().size() >= 1) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (PlayerAccount.isTest != 1)
                    player.kickPlayer("Sunucu yeniden başlatıldı! Tekrar giriş yapınız.");
                else {
                    ChatController.players.add(player);
                    Cache.registerPlayerData(player.getName());
                }
            }
        }

        dc = new DiscordConnector();
    }

    @Override
    public void onDisable() {
        seasonCycle.stopCycle();

    }

    public static OrbiCore getInstance() {
        return orbicore;
    }

    public static ObjectMapper getOB() {
        return objectMapper;
    }

    public static Random getRandom() {
        return random;
    }

    public static DiscordConnector getDC() {
        return dc;
    }

    public static InventoryHolder getInventoryHolder() {
        return inventoryHolder;
    }

    public static DecimalFormat getDecimalFormat() {
        return decimalFormat;
    }
}