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


import com.tinkerforge.AlreadyConnectedException;
import com.tinkerforge.BrickletTemperature;
import com.tinkerforge.IPConnection;

import java.io.IOException;

/**
 * Created by Sven Ruppert on 05.06.2014.
 */
public class OrigApi {
  public static void main(String[] args) {

    IPConnection ipcon = new IPConnection();
    ipcon.setAutoReconnect(true);
    int timeoutMS = 2500;
    ipcon.setTimeout(timeoutMS);

    BrickletTemperature temp = new BrickletTemperature("uid", ipcon);
    temp.addTemperatureListener(temperature -> {
      int temp1 = temperature / 100;
      System.out.println("temp = " + temp1);
    });

    try {
      ipcon.connect("localhost", 4229);
    } catch (IOException | AlreadyConnectedException e) {
      e.printStackTrace();
    }

  }
}

//    temp.addTemperatureListener(new BrickletTemperature.TemperatureListener() {
//          @Override
//          public void temperature(short temperature) {
//            int temp = temperature / 100;
//            System.out.println("temp = " + temp);
//          }
//        });
