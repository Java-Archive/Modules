package org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer.model;

import java.io.Serializable;

/**
 * Created by Alexander Bischof on 21.02.14.
 */
public class Persistence implements Serializable {
    String bezeichnung;

    private int port;
    private String host;
    private String username;
    private String password;
    private Class connectionClass;

    public Persistence() {
    }

    public Persistence(String bezeichnung) {

        this.bezeichnung = bezeichnung;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Class getConnectionClass() {
        return connectionClass;
    }

    public void setConnectionClass(Class connectionClass) {
        this.connectionClass = connectionClass;
    }
}
