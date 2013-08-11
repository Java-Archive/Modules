package org.rapidpm.demo.cdi.commons.async;

import java.util.concurrent.CountDownLatch;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.rapidpm.demo.cdi.commons.logger.Logger;
import org.rapidpm.demo.cdi.commons.logger.CDILogger;

/**
 * User: Sven Ruppert
 * Date: 31.07.13
 * Time: 11:02
 */
@ApplicationScoped
public class TestService1 {
    static final CountDownLatch TEST_LATCH = new CountDownLatch(1);

    //private static final Logger logger = Logger.getLogger(TestService1.class);
    private
    @Inject
    @CDILogger
    Logger logger;

    private boolean called = false;

    @Async
    public void call()
    {
        try
        {
            Thread.sleep(1 * 1000);
            if (logger.isDebugEnabled()) {
                logger.debug("wach again");
            }
        }
        catch (InterruptedException e)
        {
            throw new IllegalStateException(e);
        }
        this.called = true;
        if (logger.isDebugEnabled()) {
            logger.debug("called - " + true);
        }
        TEST_LATCH.countDown();
    }

    public boolean isCalled()
    {
        return called;
    }
}
