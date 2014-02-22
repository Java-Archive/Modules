package org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer.model;

import javafx.collections.ObservableList;
import org.rapidpm.module.iot.tinkerforge.sensor.TinkerForgeSensor;

import java.io.Serializable;

/**
 * Created by Alexander Bischof on 21.02.14.
 */
public class Sensor implements Serializable {

    private String UID;
    private int callbackPeriod;
    private int port;
    private String host;
    private Class<? extends TinkerForgeSensor<?>> connectionClass;

    private transient ObservableList seriesData;

    public Sensor() {
    }

    public Sensor(String UID, int callbackPeriod, int port, String host, Class<? extends TinkerForgeSensor<?>> connectionClass) {
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

    public Class<? extends TinkerForgeSensor<?>> getConnectionClass() {
        return connectionClass;
    }

    @Override
    public String toString() {
        return connectionClass.getSimpleName() + "{" +
                "UID='" + UID + '\'' +
                '}';
    }
}
