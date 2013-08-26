package org.rapidpm.demo.cdi.commons.async;

import java.util.concurrent.CountDownLatch;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert
 * Date: 31.07.13
 * Time: 11:01
 */
@ApplicationScoped
public class TestObserver {
    static final CountDownLatch TEST_LATCH = new CountDownLatch(1);
    private static final Logger logger = Logger.getLogger(TestObserver.class);

    private boolean called = false;

    @Async
    public void observeAsync(@Observes AsyncTestEvent testEvent) {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("testEvent " + testEvent);
            }
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
        this.called = true;
        TEST_LATCH.countDown();
    }

    public boolean isCalled() {
        return called;
    }
}
