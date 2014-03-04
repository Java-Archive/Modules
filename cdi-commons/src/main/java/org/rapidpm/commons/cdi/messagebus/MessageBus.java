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

package org.rapidpm.commons.cdi.messagebus;

import com.google.common.eventbus.EventBus;
import org.rapidpm.commons.cdi.logger.CDILogger;
import org.rapidpm.module.se.commons.logger.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serializable;

/**
 * User: Sven Ruppert
 * Date: 31.07.13
 * Time: 10:16
 */
@ApplicationScoped //zu hart def
public class MessageBus implements Serializable {

    //    private EventBus eventBus = new AsyncEventBus(this.getClass().getName(), Executors.newCachedThreadPool());
    private EventBus eventBus = new EventBus(this.getClass().getName());

    private
    @Inject
    @CDILogger
    Logger logger;

    public void registerCallBack(MessageBusCallback callBack) {
        if (logger.isDebugEnabled()) {
            logger.debug("registerCallBack " + callBack);
        }
        eventBus.register(callBack);
    }

    public void destroyCallBack(MessageBusCallback callBack) {
        if (logger.isDebugEnabled()) {
            logger.debug("destroyCallBack " + callBack);
        }
        eventBus.unregister(callBack);
    }

    public void post(Message message) {
        if (logger.isDebugEnabled()) {
            logger.debug("post " + message);
        }
        eventBus.post(message);
    }
}
