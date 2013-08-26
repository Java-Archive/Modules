package org.rapidpm.demo.cdi.commons.async;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import javax.interceptor.InvocationContext;

import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert
 * Date: 31.07.13
 * Time: 10:51
 */
public class AsyncExecutor implements Callable<Future<Object>> {
    private final InvocationContext invocationContext;
    private static final Logger logger = new Logger(AsyncExecutor.class);
//    private final ContextControl contextControl;

    //    AsyncExecutor(InvocationContext invocationContext, Instance<ContextControl> contextControlInstance)
    public AsyncExecutor(InvocationContext invocationContext) {
        this.invocationContext = invocationContext;

    }

    @Override
    public Future<Object> call() throws Exception {
        try {
            Object result = invocationContext.proceed();
            if (result instanceof Future) {
                return (Future) result;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("e = " + e);
//            throw ExceptionUtils.throwAsRuntimeException(e);
        } finally {

        }
        return null;
    }
}