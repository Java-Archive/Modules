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

package org.rapidpm.module.iot.tinkerforge.persistence.arangodb;

import data.SensorDataElement;
import org.arangodb.objectmapper.ArangoDb4JException;
import org.arangodb.objectmapper.ArangoDbRepository;
import org.arangodb.objectmapper.Database;
import org.rapidpm.module.iot.tinkerforge.persistence.ISensorDataRepository;

/**
 * Created by Sven Ruppert on 16.02.14.
 */
public class SensorDataRepository extends ArangoDbRepository<SensorDataElement> implements ISensorDataRepository<SensorDataElement> {
    /**
     * Constructor
     *
     * @param database the ArangoDB database
     */
    public SensorDataRepository(Database database) {
        super(database, SensorDataElement.class);
    }

    @Override
    public SensorDataElement create(SensorDataElement data) {
        try {
            return super.create(data);
        } catch (ArangoDb4JException e) {
            e.printStackTrace();
        }
        return null;
    }
}
