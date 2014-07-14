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
import com.tinkerforge.BrickletMotionDetector;
import com.tinkerforge.IPConnection;

import java.io.IOException;
import java.time.Instant;

/**
 * Created by Sven Ruppert on 03.06.2014.
 */
public class MotionDetectorTest {

  public static void main(String[] args) {
    IPConnection con = new IPConnection();
    try {
      BrickletMotionDetector motionDectector = new BrickletMotionDetector("xyz", con);
      con.connect("192.168.0.106",4223);

      motionDectector.addMotionDetectedListener(() -> {
        String pFileName = "pic_" + Instant.now();
        try {
          Runtime run = Runtime.getRuntime();
          Process pr = run.exec("raspistill -o " + pFileName + ".jpg");
          pr.waitFor();
        } catch (IOException
            | InterruptedException ex) {
          System.out.println("ex = " + ex);
        }

      });
    } catch (IOException
        | AlreadyConnectedException e) {
      e.printStackTrace();
    }
  }
}
