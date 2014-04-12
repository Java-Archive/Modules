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

package org.rapidpm.module.iot.tinkerforge;


import org.rapidpm.module.iot.tinkerforge.actor.LCD20x4;
import org.rapidpm.module.iot.tinkerforge.gui.cml.WaitForQ;
import org.rapidpm.module.iot.tinkerforge.sensor.Barometer;
import org.rapidpm.module.iot.tinkerforge.sensor.Humidity;
import org.rapidpm.module.iot.tinkerforge.sensor.Light;
import org.rapidpm.module.iot.tinkerforge.sensor.Temperature;

import static org.rapidpm.module.iot.tinkerforge.sensor.TinkerForgeSensor.SensorValueAction;

/**
 * Created by Sven Ruppert on 15.02.14.
 */
public class WeatherStationRemote {

    public static final String HOST = "192.168.0.200";  //wetterstation
    public static final int PORT = 4223;
    private static int callbackPeriod = 10000;

    private static final LCD20x4 lcd20x4 = new LCD20x4("jvX", "192.168.0.202", PORT);

    public static void main(String args[]) throws Exception {
        final Temperature temperature = new Temperature("dXj", callbackPeriod, PORT, HOST);
        temperature.actionTemperature = new SensorValueAction() {
            @Override
            public void workOnValue(int sensorvalue) {
                final double tempNorm = sensorvalue / 100.0;
                final String text = "Temp  : " + tempNorm + " °C";
                lcd20x4.printLine(0, text);
            }
        };
        new Thread(temperature).start();

        final Barometer barometer = new Barometer("jY4", callbackPeriod, PORT, HOST);
        barometer.actionAirPressure = new SensorValueAction() {
            @Override
            public void workOnValue(int sensorvalue) {
            final String text = "Air   : " + sensorvalue / 1000.0 + " mbar";
                lcd20x4.printLine(1, text);
            }
        };
        new Thread(barometer).start();

        final Light light = new Light("jy2", callbackPeriod, PORT, HOST);
        light.actionAmbientLight = new SensorValueAction() {
            @Override
            public void workOnValue(int sensorvalue) {
                final double lux = sensorvalue / 10.0;
                final String text = "Lux   : " + lux + " Lux";
                lcd20x4.printLine(3, text);
            }
        };

        new Thread(light).start();

        final Humidity humidity = new Humidity("kfd", callbackPeriod, PORT, HOST);
        humidity.actionHumidity = new SensorValueAction() {
            @Override
            public void workOnValue(int sensorvalue) {
                final double tempNorm = sensorvalue / 10.0;
                final String text = "RelHum: " + tempNorm + " %RH";
                lcd20x4.printLine(2, text);
            }
        };
        new Thread(humidity).start();

        WaitForQ.waitForQ();
    }
}
