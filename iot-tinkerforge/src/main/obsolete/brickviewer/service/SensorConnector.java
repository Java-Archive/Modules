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

package org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer.service;

import org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer.model.Sensor;
import org.rapidpm.module.iot.tinkerforge.sensor.singlevalue.Barometer;
import org.rapidpm.module.iot.tinkerforge.sensor.singlevalue.Temperature;
import org.rapidpm.module.iot.tinkerforge.sensor.singlevalue.TinkerForgeSensorSingleValue;

/**
 * Created by Alexander Bischof on 22.02.14.
 */
public class SensorConnector {
    public <T extends TinkerForgeSensorSingleValue<?>> T connect(Sensor sensor) {
        //TODO
        if (Temperature.class.equals(sensor.getConnectionClass()))
            return (T) new Temperature(sensor.getUID(), sensor.getCallbackPeriod(), sensor.getPort(), sensor.getHost());
        else if (Barometer.class.equals(sensor.getConnectionClass())) {
            return (T) new Barometer(sensor.getUID(), sensor.getCallbackPeriod(), sensor.getPort(), sensor.getHost());

        }
        return null;
    }
}
