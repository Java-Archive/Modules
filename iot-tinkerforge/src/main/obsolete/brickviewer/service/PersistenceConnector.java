package org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer.service;

import org.rapidpm.module.iot.tinkerforge.gui.fx.brickviewer.model.Persistence;

/**
 * Created by lenaernst on 22.02.14.
 */
public class PersistenceConnector {
    public <T> T connect(Persistence persistence) {
        T ret = null;

        //TODO
//        if (ArangoDBRemote.class.equals(persistence.getConnectionClass())) {
//            ret = (T) new SensorDataRepository(ArangoDBRemote.database);
//        }
        return ret;
    }
}
