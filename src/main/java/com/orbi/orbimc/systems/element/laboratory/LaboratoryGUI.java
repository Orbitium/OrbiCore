package com.orbi.orbimc.systems.element.laboratory;

import com.orbi.orbimc.database.Repo;
import com.orbi.orbimc.math.ConvertType;
import com.orbi.orbimc.math.Converter;
import com.orbi.orbimc.bone.security.authority.Permission;
import com.orbi.orbimc.bone.security.authority.PermissionController;
import com.orbi.orbimc.systems.element.ElementData;
import com.orbi.orbimc.util.Color;
import com.orbi.orbimc.util.InventoryBuilder;
import com.orbi.orbimc.util.ItemBuilder;
import com.orbi.orbimc.util.StringParser;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LaboratoryGUI {

    //todo laboratuvar depolamasına, laboratuvar seviyelerine göre sınırlandırma getirilecek
    public static void createPanel(Player player) {
        try {
            Map<String, Integer> playerData = ElementData.getPlayerData(player);

            if (playerData.get("energyResistanceLevel") == 0) {
                createBrokenLabPanel(player);
                return;
            }

            Inventory inv = InventoryBuilder.createCustomInventory(Repo.getText("es-panel-title"), InventoryType.PLAYER);

            String produceProtonHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzRiNjYyZDNiNTI5YTE4NzI2MWNhYjg2YzZlNTY0MjNiZjg3NmFhMjQ5ZDAzMGZhZWFmMzQzNjJmMzQ0NzI3NyJ9fX0=";
            String neutronProtonHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjg3OTQzNjM4NWJkYjBlZmQxM2IxZDJjMDk0YTMxMWVhODFlM2I4MzUyMGY5MTkyZDg5ZTY0NDJiNmZkZjIifX19";
            String electronProtonHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTY3OTkxOGU1MmYzZjhmMmNhYmJiZWFjNmE5NzY4MWYyZjhhYTEwYzBiMmU4MTg1OTI4ODVhNGEwZTlkMjI3In19fQ==";
            String labStorageHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDBjNmMzYzk1MzM3MzY1Nzk5YzZjNTAyZDZmYzM0YTViMTk3NWNmNzM2NGJhMmRlOGZkYjBhZjUxZTZhMjhjIn19fQ==";
            String labHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDJjOGU3NmZjZGRlOWExNWFlMzkyOWI4NGJmNmZmYTRjMTc3N2IxZTI2YjdkNmQ4OTM4ZjBlZjA0ZTg0ODc0YyJ9fX0=";
            String energyResistanceHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGQyYzE5YjQ0MjU0MTM1MWE2YjgxZWViNmNiZWY0MTk2NmZmYjdkYmU0YzEzNmI4N2Y1YmFmOWQxNGEifX19";
            String temperatureResistanceHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWMyZTlkODM5NWNhY2Q5OTIyODY5YzE1MzczY2Y3Y2IxNmRhMGE1Y2U1ZjNjNjMyYjE5Y2ViMzkyOWM5YTExIn19fQ==";
            String radiationResistanceHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTk4OTgwMmY1ZDk0YTBjNzRlMDQ4OTIyMGVjYThkM2VlZTBhOWQwOTcxOWU5MjBlNTQ1YTk2ZDUxYzBlOTgwZCJ9fX0=";
            String goEnergySystemHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzc0MDBlYTE5ZGJkODRmNzVjMzlhZDY4MjNhYzRlZjc4NmYzOWY0OGZjNmY4NDYwMjM2NmFjMjliODM3NDIyIn19fQ==";
            String goItemSystemHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2UyMjM5MWUzNWEzZTViY2VlODlkYjMxMmU4NzRmZGM5ZDllN2E2MzUxMzE0YjgyYmRhOTdmYmQyYmU4N2ViOCJ9fX0=";

            List<String> produceProtonLore = createProduceLore(ProduceCost.protonProductionEnergyCost, ProduceCost.protonProductionTemperatureAltitude, ProduceCost.protonProduceRadiationAltitude);
            List<String> produceNeutronLore = createProduceLore(ProduceCost.neutronProductionEnergyCost, ProduceCost.neutronProductionTemperatureAltitude, ProduceCost.neutronProduceRadiationAltitude);
            List<String> produceElectronLore = createProduceLore(ProduceCost.electronProductionEnergyCost, ProduceCost.electronProductionTemperatureAltitude, ProduceCost.electronProduceRadiationAltitude);
            List<String> labStorageLore = StringParser.parse(ElementData.createLabStorageData(playerData), Repo.getText("es-lab-storage-lore0"), Repo.getText("es-lab-storage-lore1"), Repo.getText("es-lab-storage-lore2"));
            List<String> labLore = StringParser.parse(ElementData.createLabData(playerData), 0, ConvertType.ENERGY, Repo.getText("es-lab-lore0"), Repo.getText("es-lab-lore1"), Repo.getText("es-lab-lore2"));

            List<String> needList = new ArrayList<>(List.of(Repo.getMultipleText("es-panel-upgrade-needed-list", "es-upgrade-carbon-cost", "es-upgrade-stone-cost", "es-upgrade-iron-cost", "es-upgrade-gold-cost", "es-upgrade-netherite-cost")));

            needList.add(Repo.getText("es-upgrade-energy-resistance-lore"));
            List<String> upgradeEnergyResistanceLore = StringParser.parse(UpgradeCost.ENERGY.getByMap(playerData.get("energyResistanceLevel"), ConvertType.ENERGY), needList);
            needList.remove(Repo.getText("es-upgrade-energy-resistance-lore"));
            needList.add(Repo.getText("es-upgrade-temperature-resistance-lore"));
            List<String> upgradeTemperatureResistanceLore = StringParser.parse(UpgradeCost.TEMPERATURE.getByMap(playerData.get("temperatureResistanceLevel")), needList);
            needList.remove(Repo.getText("es-upgrade-temperature-resistance-lore"));
            needList.add(Repo.getText("es-upgrade-radiation-resistance-lore"));
            List<String> upgradeRadiationResistanceLore = StringParser.parse(UpgradeCost.RADIATION.getByMap(playerData.get("radiationResistanceLevel")), needList);

            Permission permission = PermissionController.getPermission(player.getUniqueId().toString());
            List<String> elementUpLore = StringParser.parse(ElementUpCost.values()[permission.getPermissionLevel()].getByMap(), 1, ConvertType.ENERGY,
                    Repo.getMultipleText("es-element-up-next-element", "es-element-up-power-cost", "es-element-up-temperature-resistance", "es-element-up-radiation-resistance", "es-element-up-proton-cost",
                            "es-element-up-neutron-cost", "es-element-up-electron-cost"));

            inv.setItem(1, new ItemBuilder(produceProtonHead, Repo.getText("es-produce-proton-name"), produceProtonLore).build());
            inv.setItem(10, new ItemBuilder(neutronProtonHead, Repo.getText("es-produce-neutron-name"), produceNeutronLore).build());
            inv.setItem(19, new ItemBuilder(electronProtonHead, Repo.getText("es-produce-electron-name"), produceElectronLore).build());
            inv.setItem(9, new ItemBuilder(labStorageHead, Repo.getText("es-lab-storage-name"), labStorageLore).build());
            inv.setItem(4, new ItemBuilder(labHead, Repo.getText("es-lab-name"), labLore).build());
            inv.setItem(12, new ItemBuilder(energyResistanceHead, Repo.getText("es-upgrade-energy-resistance-name"), upgradeEnergyResistanceLore).build());
            inv.setItem(13, new ItemBuilder(temperatureResistanceHead, Repo.getText("es-upgrade-temperature-resistance-name"), upgradeTemperatureResistanceLore).build());
            inv.setItem(14, new ItemBuilder(radiationResistanceHead, Repo.getText("es-upgrade-radiation-resistance-name"), upgradeRadiationResistanceLore).build());
            inv.setItem(16, new ItemBuilder(goEnergySystemHead, Repo.getText("es-go-energy-system-name")).build());
            inv.setItem(25, new ItemBuilder(goItemSystemHead, Repo.getText("es-go-item-system-name")).build());
            inv.setItem(31, new ItemBuilder(Material.NETHER_STAR, Color.translateHex(StringParser.parse(Repo.getText("es-currently-element"), permission.getPermissionName())), elementUpLore).build());

            player.openInventory(inv);
        } catch (NullPointerException e) {
            //ElementData.createPlayerData(player);
            //createPanel(player);
            e.printStackTrace();
        }
    }

    public static void createBrokenLabPanel(Player player) {
        Inventory inv = InventoryBuilder.createCustomInventory(Repo.getText("es-broken-panel-title"), InventoryType.CHEST);

        List<String> needList = new ArrayList<>(List.of(Repo.getMultipleText("es-upgrade-wood-cost", "es-upgrade-stone-cost", "es-upgrade-iron-cost", "es-upgrade-gold-cost", "es-upgrade-netherite-cost", "es-repair-lab-lore0")));

        String brokenLabHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjcxMmE4OTc2NjNlYjZlNzM1MzBjNzNjZWYzM2I1MGQ4MmE3ODdmNDU0NWUwNTZiYzE2YjkwNWMyZDE2M2Q3NiJ9fX0=";

        List<String> lore = StringParser.parse(ElementData.createRepairLabData(), needList);

        inv.setItem(13, new ItemBuilder(brokenLabHead, Repo.getText("es-repair-lab-name"), lore).build());

        player.openInventory(inv);
    }

    public static List<String> createProduceLore(int energyCost, int temperature, int radiation) {
        List<String> lore = new ArrayList<>();
        lore.add(Repo.getText("es-panel-produce-needed-list"));
        lore.add(StringParser.parse(Repo.getText("es-produce-energy-cost"), Converter.formatValue(energyCost, ConvertType.ENERGY)));
        lore.add(StringParser.parse(Repo.getText("es-produce-temperature"), temperature));
        lore.add(StringParser.parse(Repo.getText("es-produce-radiation"), radiation));
        return lore;
    }

}
