/*
 * Copyright [2013] [www.rapidpm.org / Sven Ruppert (sven.ruppert@rapidpm.org)]
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

package org.rapidpm.commons.cdi.aggregator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;

import org.rapidpm.commons.cdi.logger.CDILogger;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert
 * Date: 02.09.13
 * Time: 11:35
 */
public abstract class MapAggregator<T, K> {

    private @Inject @CDILogger Logger logger;


    public abstract K getKeyElement(T t);

    public Map<K, List<T>> aggregate(final Collection<T> dataCollection) {
        final Map<K, List<T>> result = new HashMap<>();

        dataCollection.forEach((dataObject)->{
            final K key = getKeyElement(dataObject);
            if (result.containsKey(key)) {
                if (logger.isDebugEnabled()) {
                    logger.debug("key allready available -> " + key);
                }
            } else {
                result.put(key, new ArrayList<T>());
            }
            result.get(key).add(dataObject);

        });

//        for (final T dataObject : dataCollection) {
//            final K key = getKeyElement(dataObject);
//            if (result.containsKey(key)) {
//                if (logger.isDebugEnabled()) {
//                    logger.debug("key allready available -> " + key);
//                }
//            } else {
//                result.put(key, new ArrayList<T>());
//            }
//            result.get(key).add(dataObject);
//        }

        return result;
    }

}
