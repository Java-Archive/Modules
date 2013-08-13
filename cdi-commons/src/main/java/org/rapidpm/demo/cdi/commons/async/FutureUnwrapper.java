package org.rapidpm.demo.cdi.commons.async;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * User: Sven Ruppert
 * Date: 31.07.13
 * Time: 10:55
 */
public class FutureUnwrapper<V extends Future<Object>> implements Future<Object> {
    //future which wraps a future
    private final V wrappingFuture;

    FutureUnwrapper(V future) {
        this.wrappingFuture = future;
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        return this.wrappingFuture.cancel(mayInterruptIfRunning);
    }

    public Object get() throws InterruptedException, ExecutionException {
        Object result = wrappingFuture.get();

        if (result instanceof Future) {
            return ((Future<Object>) result).get();
        }
        return result;
    }

    public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        Object result = wrappingFuture.get(timeout, unit);

        if (result instanceof Future) {
            return ((Future<Object>) result).get();
        }
        return result;
    }

    public boolean isCancelled() {
        return wrappingFuture.isCancelled();
    }

    public boolean isDone() {
        return wrappingFuture.isDone();
    }
}