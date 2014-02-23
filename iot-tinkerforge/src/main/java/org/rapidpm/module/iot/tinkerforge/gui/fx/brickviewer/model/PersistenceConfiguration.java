package org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer.model;

/**
 * Created by lenaernst on 22.02.14.
 */
public class PersistenceConfiguration {
    private final Persistence persistence;
    private boolean connected;

    public PersistenceConfiguration(Persistence persistence) {
        this.persistence = persistence;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public Persistence getPersistence() {
        return persistence;
    }
}
