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


import com.tinkerforge.NotConnectedException;
import org.rapidpm.module.se.commons.WaitForQ;
import org.rapidpm.module.iot.tinkerforge.sensor.multivalue.color.Color;

/**
 * Created by Sven Ruppert on 14.09.2014.
 */
public class ColorTest {

  public static void main(String[] args) {
    //oxV
    final Color color = new Color("oxV", 500, 4223, "localhost");

    color.addColorAction((r, g, b, c) -> {
      System.out.println("r = " + r);
      System.out.println("g = " + g);
      System.out.println("b = " + b);
      System.out.println("c = " + c);
      System.out.println("============ ");
    });

    color.addColorTempAction(i -> System.out.println("colorTemp = " + i));

    color.addIlluminanceAction(i -> System.out.println("illuminance = " + i));


    color.run();

    WaitForQ waitForQ = new WaitForQ();

//    waitForQ.addShutDownAction(() -> {
//      try {
//        ipcon.disconnect();
//      } catch (NotConnectedException e) {
//        e.printStackTrace();
//      }
//    });
    waitForQ.addShutDownAction(() -> System.exit(0));

    waitForQ.waitForQ();


  }
}
