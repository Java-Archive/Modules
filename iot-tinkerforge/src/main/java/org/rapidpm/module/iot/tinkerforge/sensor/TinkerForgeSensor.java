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

package org.rapidpm.module.iot.tinkerforge.sensor;

import com.tinkerforge.*;

import java.io.IOException;

/**
 * Created by Sven Ruppert on 21.02.14.
 */
public abstract class TinkerForgeSensor<T extends Device> implements Runnable{

    public String UID;
    public int callbackPeriod;

    public int port;
    public String host;

    public IPConnection ipcon = new IPConnection();

    public T bricklet;

    public TinkerForgeSensor(final String UID, int callbackPeriod, int port, String host) {
        this.UID = UID;
        this.callbackPeriod = callbackPeriod;
        this.port = port;
        this.host = host;
    }

    @Override
    public void run() {
        bricklet = getBrickletInstance();
        try {
            ipcon.connect(host, port);
            initBricklet();
        } catch (IOException | AlreadyConnectedException  e) {
            e.printStackTrace();
        }
    }

    public abstract void initBricklet();

    protected abstract T getBrickletInstance();

    public static interface SensorValueAction {
        public default void workOnValue(int sensorvalue){}
    }
}
