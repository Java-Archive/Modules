/*
 * Copyright [2014] [www.rapidpm.org / Sven Ruppert (sven.ruppert@rapidpm.org)]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer.model;

import org.rapidpm.module.iot.tinkerforge.sensor.singlevalue.TinkerForgeSensorSingleValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenaernst on 22.02.14.
 */
public class SensorConfiguration {
    private final Sensor sensor;
    private List<Persistence> persistenceList = new ArrayList<>();
    private boolean connected;
    private Thread thread;
    private TinkerForgeSensorSingleValue<?> tinkerForgeSensorSingleValueConnection;

    private List<SensorValueActionConfiguration> sensorValueActionConfigurations = new ArrayList<>();

    public SensorConfiguration(Sensor sensor) {
        this.sensor = sensor;
    }

    public void addPersistence(Persistence persistence) {
        persistenceList.add(persistence);
    }

    public void removePersistence(Persistence persistence) {
        persistenceList.remove(persistence);
    }

    public Sensor getSensor() {
        return sensor;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public TinkerForgeSensorSingleValue<?> getTinkerForgeSensorSingleValueConnection() {
        return tinkerForgeSensorSingleValueConnection;
    }

    public void setTinkerForgeSensorSingleValueConnection(TinkerForgeSensorSingleValue<?> tinkerForgeSensorSingleValueConnection) {
        this.tinkerForgeSensorSingleValueConnection = tinkerForgeSensorSingleValueConnection;
    }

    public List<SensorValueActionConfiguration> getSensorValueActionConfigurations() {
        return sensorValueActionConfigurations;
    }

    public void addSensorValueAction(TinkerForgeSensorSingleValue.SensorValueAction sensorValueAction) {
        getSensorValueActionConfigurations().add(new SensorValueActionConfiguration(sensorValueAction));
    }

    public void setSensorValueActionConfigurations(List<SensorValueActionConfiguration> sensorValueActionConfigurations) {
        this.sensorValueActionConfigurations = sensorValueActionConfigurations;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }
}
