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

package junit.org.rapidpm.module.iot.tinkerforge;

import org.rapidpm.module.iot.tinkerforge.data.SensorDataElement;
import org.rapidpm.module.iot.tinkerforge.sensor.singlevalue.Temperature;

/**
 * Created by Sven Ruppert on 05.06.2014.
 */
public class TempSesnorTest {


  public static void main(String[] args) {
    Temperature temperature = new Temperature("xyz", 5000, 4299, "localhost");

    temperature.addSensorDataAction(sensorValue
        -> System.out.println("sensorValue = " + sensorValue));

    temperature.addSensorDataAction(sensorValue -> {
      SensorDataElement dataElement = temperature.getNextSensorDataElement();
      dataElement.setSensorValue(sensorValue);
      saveValue(dataElement);
    });

    //..
    temperature.run();
  }

  private static void saveValue(SensorDataElement dataElement) {
    //zeitkritisch..
  }
}
