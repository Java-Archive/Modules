package org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer.service;


import org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer.model.Masterbrick;
import org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer.model.Sensor;
import org.rapidpm.module.iot.tinkerforge.sensor.Barometer;
import org.rapidpm.module.iot.tinkerforge.sensor.Temperature;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander Bischof on 21.02.14.
 */
public class MasterBrickService {

    private static int callbackPeriod = 10000;

    public List<Masterbrick> findMasterbricks() {

        List<Masterbrick> masterbricks = new ArrayList<>();

        Masterbrick masterbrick = new Masterbrick().setName("My Masterbrick");

        //Temperature temperatureSensor = new Temperature("dXj", 10000, 4223, "192.168.0.200");
        Sensor sensor = new Sensor("dXj", callbackPeriod, 4223, "192.168.0.200", Temperature.class);
        Sensor sensor2 = new Sensor("jY4", callbackPeriod, 4223, "192.168.0.200", Barometer.class);
        masterbrick.setSensors(sensor, sensor2);

        masterbricks.add(masterbrick);

        return masterbricks;
    }
}
