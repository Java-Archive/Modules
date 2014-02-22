package org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer.model;

import java.io.Serializable;

/**
 * Created by Alexander Bischof on 21.02.14.
 */
public class Persistence implements Serializable {
    String bezeichnung;

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
}
