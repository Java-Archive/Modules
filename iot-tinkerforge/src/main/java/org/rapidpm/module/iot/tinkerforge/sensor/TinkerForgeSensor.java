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
import org.rapidpm.module.iot.tinkerforge.SensorDataElement;

import java.io.IOException;
import java.util.Date;

/**
 * Created by Sven Ruppert on 21.02.14.
 */
public abstract class TinkerForgeSensor<T extends Device> implements Runnable {

  public String UID;
  public int callbackPeriod;

  public int port;
  public String host;

  public IPConnection ipcon = new IPConnection();

  public T bricklet;

  public String masterUID;
  public String brickletUID;
  public String brickletType;


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
      masterUID = bricklet.getIdentity().connectedUid;
      brickletUID = bricklet.getIdentity().uid;
      brickletType = bricklet.getIdentity().deviceIdentifier + "";
      initBricklet();
    } catch (IOException
        | AlreadyConnectedException
        | TimeoutException
        | NotConnectedException e) {
      e.printStackTrace();
    }
  }

  public SensorDataElement getNextSensorDataElement() {
    final SensorDataElement data = new SensorDataElement();
    data.setMasterUID(masterUID);
    data.setBrickletUID(brickletUID);
    data.setBrickletType(brickletType);
    data.setDate(new Date());
//        data.setSensorValue(sensorvalue);
    return data;
  }


  public abstract void initBricklet();

  public abstract T getBrickletInstance();

  @FunctionalInterface
  public static interface SensorValueAction {
    public void workOnValue(int sensorvalue);
  }
}
