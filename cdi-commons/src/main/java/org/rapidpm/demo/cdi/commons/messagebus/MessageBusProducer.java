package org.rapidpm.demo.cdi.commons.messagebus;

import javax.enterprise.inject.Produces;

/**
 * User: Sven Ruppert
 * Date: 31.07.13
 * Time: 14:37
 */
public class MessageBusProducer {


    @Produces
    @CDIMessageBus public MessageBus create(MessageBus messageBus){
        return messageBus;

    }

}
