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

package org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer.model;

import javafx.collections.ObservableList;
import org.rapidpm.module.iot.tinkerforge.sensor.singlevalue.TinkerForgeSensorSingleValue;

import java.io.Serializable;

/**
 * Created by Alexander Bischof on 21.02.14.
 */
public class Sensor implements Serializable {

    private String UID;
    private int callbackPeriod;
    private int port;
    private String host;
    private Class<? extends TinkerForgeSensorSingleValue<?>> connectionClass;

    private transient ObservableList seriesData;

    public Sensor() {
    }

    public Sensor(String UID, int callbackPeriod, int port, String host, Class<? extends TinkerForgeSensorSingleValue<?>> connectionClass) {
        this.UID = UID;
        this.callbackPeriod = callbackPeriod;
        this.port = port;
        this.host = host;
        this.connectionClass = connectionClass;
    }

    public ObservableList getSeriesData() {
        return seriesData;
    }

    public void setSeriesData(ObservableList seriesData) {
        this.seriesData = seriesData;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public int getCallbackPeriod() {
        return callbackPeriod;
    }

    public void setCallbackPeriod(int callbackPeriod) {
        this.callbackPeriod = callbackPeriod;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Class<? extends TinkerForgeSensorSingleValue<?>> getConnectionClass() {
        return connectionClass;
    }

    @Override
    public String toString() {
        return connectionClass.getSimpleName() + "{" +
                "UID='" + UID + '\'' +
                '}';
    }
}
