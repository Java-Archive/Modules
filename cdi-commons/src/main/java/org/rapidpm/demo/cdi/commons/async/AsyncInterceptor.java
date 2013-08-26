package org.rapidpm.demo.cdi.commons.async;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert
 * Date: 31.07.13
 * Time: 10:54
 */

@Interceptor
@Async
public class AsyncInterceptor implements Serializable {
    private static final long serialVersionUID = 7938266823530974338L;


    //private static final Logger logger = Logger.getLogger(AsyncInterceptor.class);
    private
    @Inject
    @CDILogger
    Logger logger;

    @AroundInvoke
    public Object executeAsynchronous(InvocationContext invocationContext) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        try {
            Future result = executor.submit(new AsyncExecutor(invocationContext));

            final Method method = invocationContext.getMethod();
            Class returnType = method.getReturnType();
            final boolean isVoid = "void".equalsIgnoreCase(returnType.getName());
            final boolean assignableFrom = Void.class.isAssignableFrom(returnType);
            if (isVoid || assignableFrom) {
                return null;
            } else {
                final FutureUnwrapper futureUnwrapper = new FutureUnwrapper(result);
                if (logger.isDebugEnabled()) {
                    logger.debug("futureUnwrapper - " + futureUnwrapper);
                }
                return futureUnwrapper;
            }
        } finally {
            executor.shutdown(); //won't stop immediately
        }
    }
}