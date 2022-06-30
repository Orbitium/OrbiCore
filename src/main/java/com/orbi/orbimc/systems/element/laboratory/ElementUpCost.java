package com.orbi.orbimc.systems.element.laboratory;

import com.orbi.orbimc.bone.security.authority.PermissionController;

import java.util.HashMap;
import java.util.Map;

//Todo -> Dengeleme yapılırken, tabdaki ve diğer yerlerdeki element sayısı en az 8'e çıkartılacak // yaptım sanırım

public enum ElementUpCost { //Radyasyon 1x -> 425
    HYDROGEN(6000000, 6000, 1700, 2, 2, 4, 1), // hidrojen -> 0. element
    HELIUM(9000000, 6353, 2975, 3, 4, 7, 2),
    LITHIUM(12000000, 6706, 3825, 4, 5, 9, 3),
    BERYLLIUM(15000000, 7059, 4675, 5, 6, 11, 4),
    BORON(18000000, 7412, 5100, 6, 6, 12, 5),
    CARBON(21000000, 7765, 5950, 7, 7, 14, 6),
    NITROGEN(24000000, 8118, 6800, 8, 8, 16, 7),
    OXYGEN(27000000, 8471, 8075, 9, 10, 19, 8),
    FLUORINE(30000000, 8824, 8500, 10, 10, 20, 9),
    NEON(33000000, 9177, 9775, 11, 12, 23, 10),
    SODIUM(-1, -1, -1, 100, 100, 100, 10);

    private final int power;
    private final int temperature;
    private final int radiation;
    private final int proton;
    private final int neutron;
    private final int electron;
    private final int nextElement;

    ElementUpCost(int power, int temperature, int radiation, int proton, int neutron, int electron, int nextElement) {
        this.power = power;
        this.temperature = temperature;
        this.radiation = radiation;
        this.proton = proton;
        this.neutron = neutron;
        this.electron = electron;
        this.nextElement = nextElement;
    }

    public Map<String, Object> getByMap() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("nextElement", PermissionController.getPermission(nextElement).getPermissionName());
        dataMap.put("storedEnergy", power);
        dataMap.put("temperatureResistance", temperature);
        dataMap.put("radiationResistance", radiation);
        dataMap.put("protonAmount", proton);
        dataMap.put("electronAmount", neutron);
        dataMap.put("neutronAmount", electron);
        return dataMap;
    }
}
