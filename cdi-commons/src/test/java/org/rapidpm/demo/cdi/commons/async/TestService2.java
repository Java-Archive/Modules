package org.rapidpm.demo.cdi.commons.async;

import java.util.concurrent.Future;

import javax.enterprise.context.ApplicationScoped;

/**
 * User: Sven Ruppert
 * Date: 31.07.13
 * Time: 11:02
 */
@ApplicationScoped
public class TestService2 {
    private boolean called = false;

    @Async
    public Future<Boolean> call()
    {
        try
        {
            System.out.println("TestService2 called = " + called);
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            throw new IllegalStateException(e);
        }
        this.called = true;
        return new AsyncTestResult<Boolean>(called);
    }

    public boolean isCalled()
    {
        return called;
    }
}
