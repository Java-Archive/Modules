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
 * Created by Sven Ruppert on 09.02.14.
 */
public abstract class Temperature implements Runnable {

    private String UID;
    private int callbackPeriod;

    private int port;
    private String host;

    public Temperature(final String UID, int callbackPeriod, int port, String host) {
        this.UID = UID;
        this.callbackPeriod = callbackPeriod;
        this.port = port;
        this.host = host;
    }

    public abstract void workOnSensorValue(int temperature);

    @Override
    public void run() {
        IPConnection ipcon = new IPConnection();
        BrickletTemperature temp = new BrickletTemperature(UID, ipcon);

        try {
            ipcon.connect(host, port);
            ipcon.setAutoReconnect(true);
            temp.setTemperatureCallbackPeriod(callbackPeriod);
            temp.addTemperatureListener(this::workOnSensorValue);
        } catch (IOException | AlreadyConnectedException | TimeoutException | NotConnectedException e) {
            e.printStackTrace();
        }
    }
}
