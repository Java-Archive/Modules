package org.rapidpm.module.se.hazelcast;

import com.hazelcast.config.ClasspathXmlConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.IdGenerator;

/**
 * Created by ts40 on 21.03.2014.
 */
public class Main {

    public static void main(String[] args) {
        Config config = new ClasspathXmlConfig("hazelcast.xml");
        HazelcastInstance hz = Hazelcast.newHazelcastInstance(config);





        IdGenerator idGenerator = hz.getIdGenerator ("idGenerator ");
        IMap someMap = hz.getMap("somemap-"+ idGenerator . newId ());



    }


}
