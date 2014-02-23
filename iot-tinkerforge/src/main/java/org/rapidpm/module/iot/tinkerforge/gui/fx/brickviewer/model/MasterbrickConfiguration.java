package org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lenaernst on 22.02.14.
 */
public class MasterbrickConfiguration {
    private Masterbrick masterbrick;
    private List<PersistenceConfiguration> persistenceConfigurationList = new ArrayList<>();
    private List<SensorConfiguration> sensorConfigurationList = new ArrayList<>();

    public MasterbrickConfiguration(Masterbrick masterbrick) {
        this.masterbrick = masterbrick;
    }

    public PersistenceConfiguration addPersistence(Persistence persistence) {
        PersistenceConfiguration persistenceConfiguration = new PersistenceConfiguration(persistence);
        persistenceConfigurationList.add(persistenceConfiguration);

        //Fügt allen Sensoren-Configurationen die Persistence hinzu
        for (SensorConfiguration sensorConfiguration : sensorConfigurationList) {
            sensorConfiguration.addPersistence(persistence);
        }

        return persistenceConfiguration;
    }

    public PersistenceConfiguration getPersistenceConfigurationFor(Persistence persistence) {
        return persistenceConfigurationList.stream().filter(e -> e.getPersistence().equals(persistence)).findFirst().get();
    }

    public void removePersistence(Persistence persistence) {
        for (Iterator<PersistenceConfiguration> iterator = persistenceConfigurationList.iterator(); iterator.hasNext(); ) {
            PersistenceConfiguration next = iterator.next();
            if (next.getPersistence().equals(persistence)) {
                iterator.remove();
            }
        }
    }

    public SensorConfiguration addSensorConfiguration(Sensor sensor) {
        //TODO check auf Konsistenz
        SensorConfiguration sensorConfiguration = new SensorConfiguration(sensor);
        sensorConfigurationList.add(sensorConfiguration);

        //Fügt allen Sensoren-Configurationen die Persistence hinzu
        for (PersistenceConfiguration persistenceConfiguration : persistenceConfigurationList) {
            sensorConfiguration.addPersistence(persistenceConfiguration.getPersistence());
        }

        return sensorConfiguration;
    }

    public void removeSensorConfiguration(Sensor sensor) {
        for (Iterator<SensorConfiguration> iterator = sensorConfigurationList.iterator(); iterator.hasNext(); ) {
            SensorConfiguration next = iterator.next();
            if (next.getSensor().equals(sensor)) {
                iterator.remove();
            }
        }
    }

    public SensorConfiguration getSensorConfigurationFor(Sensor sensor) {
        return sensorConfigurationList.stream().filter(e -> e.getSensor().equals(sensor)).findFirst().get();
    }

    public List<PersistenceConfiguration> getPersistenceConfigurationList() {
        return persistenceConfigurationList;
    }

    public List<SensorConfiguration> getSensorConfigurationList() {
        return sensorConfigurationList;
    }
}
