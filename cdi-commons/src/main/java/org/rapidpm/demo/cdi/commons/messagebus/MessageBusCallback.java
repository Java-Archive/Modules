package org.rapidpm.demo.cdi.commons.messagebus;

import com.google.common.eventbus.Subscribe;

/**
 * User: Sven Ruppert
 * Date: 01.08.13
 * Time: 15:01
 */
public abstract class MessageBusCallback<T> {

    @Subscribe
    public abstract void recordCallbackMessage(Message<T> m);
}
