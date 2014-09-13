package org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer.model;

import org.rapidpm.module.iot.tinkerforge.sensor.TinkerForgeSensorSingleValue;

/**
 * Created by lenaernst on 22.02.14.
 */
public class SensorValueActionConfiguration {
    private final TinkerForgeSensorSingleValue.SensorValueAction sensorValueAction;
    private Thread thread;
    private boolean connected;

    public SensorValueActionConfiguration(TinkerForgeSensorSingleValue.SensorValueAction sensorValueAction) {
        this.sensorValueAction = sensorValueAction;
    }

    public TinkerForgeSensorSingleValue.SensorValueAction getSensorValueAction() {
        return sensorValueAction;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}
