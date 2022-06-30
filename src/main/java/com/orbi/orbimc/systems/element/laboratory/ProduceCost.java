package com.orbi.orbimc.systems.element.laboratory;

import com.orbi.orbimc.database.Repo;

public interface ProduceCost {
    int protonProductionEnergyCost = Repo.getConfig("es-proton-produce-energy");
    int neutronProductionEnergyCost = Repo.getConfig("es-neutron-produce-energy");
    int electronProductionEnergyCost = Repo.getConfig("es-electron-produce-energy");

    int protonProductionTemperatureAltitude = Repo.getConfig("es-proton-produce-temperature");
    int neutronProductionTemperatureAltitude = Repo.getConfig("es-neutron-produce-temperature");
    int electronProductionTemperatureAltitude = Repo.getConfig("es-electron-produce-temperature");

    int protonProduceRadiationAltitude = Repo.getConfig("es-proton-produce-radiation");
    int neutronProduceRadiationAltitude = Repo.getConfig("es-neutron-produce-radiation");
    int electronProduceRadiationAltitude = Repo.getConfig("es-electron-produce-radiation");



}
