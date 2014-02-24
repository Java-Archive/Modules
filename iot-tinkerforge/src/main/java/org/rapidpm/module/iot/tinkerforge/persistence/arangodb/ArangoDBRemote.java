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

import org.arangodb.objectmapper.ArangoDb4JException;
import org.arangodb.objectmapper.Database;
import org.arangodb.objectmapper.http.ArangoDbHttpClient;

/**
 * Created by Sven Ruppert on 21.02.14.
 */
public class ArangoDBRemote {

    public static ArangoDbHttpClient client;
    public static Database database;

    static {
        try {
            client = new ArangoDbHttpClient.Builder().host("192.168.0.106") //TODO IP dynamisch zuweisen
                    .port(8529)
                    .username("root")
                    .password("")
                    .build();

            database = new Database(client, "_system");
        } catch (ArangoDb4JException e) {
            e.printStackTrace();
        }

    }

}
