package org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer.service;

import org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer.model.Sensor;
import org.rapidpm.module.iot.tinkerforge.sensor.Barometer;
import org.rapidpm.module.iot.tinkerforge.sensor.Temperature;
import org.rapidpm.module.iot.tinkerforge.sensor.TinkerForgeSensor;

/**
 * Created by Alexander Bischof on 22.02.14.
 */
public class SensorConnector {
    public <T extends TinkerForgeSensor<?>> T connect(Sensor sensor) {
        //TODO
        if (Temperature.class.equals(sensor.getConnectionClass()))
            return (T) new Temperature(sensor.getUID(), sensor.getCallbackPeriod(), sensor.getPort(), sensor.getHost());
        else if (Barometer.class.equals(sensor.getConnectionClass())) {
            return (T) new Barometer(sensor.getUID(), sensor.getCallbackPeriod(), sensor.getPort(), sensor.getHost());

        }
        return null;
    }
}
