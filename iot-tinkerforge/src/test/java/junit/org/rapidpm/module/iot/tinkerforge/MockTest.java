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

import com.tinkerforge.BrickletTemperature;
import com.tinkerforge.Device;
import com.tinkerforge.IPConnection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

/**
 * Created by Sven Ruppert on 05.06.2014.
 */
public class MockTest {
  public static void main(String[] args) {

    long frequence = 5000l;


    IPConnection ipConnection = new IPConnection();
    BrickletTemperature brickletTemperature = new BrickletTemperature("kjh6", ipConnection);
    byte callbackIndex = BrickletTemperature.CALLBACK_TEMPERATURE;
    Class<IPConnection> ipConnectionClass = IPConnection.class;
    try {
      final Method callDeviceListener = ipConnectionClass
          .getDeclaredMethod("callDeviceListener",
              Device.class, byte.class, byte[].class);
      callDeviceListener.setAccessible(true);

      new Thread(() -> {
        try {
          Random random = new Random();
          do {
            int randomDiff = random.nextInt(3) - 1;

            int startValue = 10;
            callDeviceListener.invoke(ipConnection, brickletTemperature,
                callbackIndex,
                new byte[]{0, 0, 0, 0, 0, 0, 0, 0, (byte) (startValue + randomDiff), 0});

            Thread.sleep(frequence);
          } while (true);
        } catch (IllegalAccessException | InvocationTargetException | InterruptedException e) {
          e.printStackTrace();
        }
      }).start();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }

    //nicht im Test
    //ipConnection.connect("localhost", 4229);

    brickletTemperature.addTemperatureListener(
        temperature
            -> System.out.println("temperature = " + temperature));

  }
}
