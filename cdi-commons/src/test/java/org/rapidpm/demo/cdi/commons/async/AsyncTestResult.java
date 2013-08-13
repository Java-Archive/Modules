package org.rapidpm.demo.cdi.commons.async;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * User: Sven Ruppert
 * Date: 31.07.13
 * Time: 11:00
 */
public class AsyncTestResult<V> implements Future<V>
{
    private final V result;

    public AsyncTestResult(V result) {
        this.result = result;
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        throw new IllegalStateException();
    }

    public V get() throws InterruptedException, ExecutionException
    {
        return result;
    }

    public V get(long timeout, TimeUnit unit) throws InterruptedException,
            ExecutionException, TimeoutException
    {
        return result;
    }

    public boolean isCancelled() {
        return false;
    }

    public boolean isDone() {
        return true;
    }
}