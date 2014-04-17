package org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Alexander Bischof on 21.02.14.
 */
public class Masterbrick implements Serializable {
    String name;
    List<Sensor> sensorList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public Masterbrick setName(String name) {
        this.name = name;
        return this;
    }

    public List<Sensor> getSensorList() {
        return sensorList;
    }

    public Masterbrick setSensors(Sensor... sensors) {
        sensorList.clear();
        sensorList.addAll(Arrays.asList(sensors));
        return this;
    }
}
