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
import org.rapidpm.module.iot.tinkerforge.data.SensorDataElement;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Sven Ruppert on 21.02.14.
 */
public abstract class TinkerForgeSensorSingleValue<T extends Device> extends TinkerForgeBaseSensor<T> {

  public TinkerForgeSensorSingleValue(String UID, int callbackPeriod, int port, String host) {
    super(UID, callbackPeriod, port, host);
  }


  private List<SensorDataAction> actionList = new ArrayList<>();
  public void clearActionList(){ actionList.clear(); }

  protected abstract double convertRawValue(int sensorRawValue);

  /**
   * zeitkritische Operation, muss beendet sein bevor ein neuer Wert kommt.
   * @param rawValue
   */
  protected void execute(int rawValue){
    double value = convertRawValue(rawValue);
    actionList.forEach(a -> a.execute(value));
  }


  public void addSensorDataAction(SensorDataAction action){
    actionList.add(action);
  }

  @FunctionalInterface
  public static interface SensorDataAction{
    public void execute(double sensorValue);
  }


}
