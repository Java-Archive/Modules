package org.rapidpm.demo.cdi.commons.contextresolver;

import java.io.Serializable;

import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.rapidpm.demo.cdi.commons.CDICommonsMocked;
import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert
 * Date: 24.10.13
 * Time: 13:31
 */
@CheckMockedContext
@Interceptor
public class MockedInterceptor implements Serializable{


    @Inject CDIContext context;
    private @Inject @CDILogger Logger logger;

    @AroundInvoke
    public Object checkMockedMode(InvocationContext ctx) throws Exception {
        if(context.isMockedModusActive()){
            if (logger.isDebugEnabled()) {
                logger.debug("MockedInterceptor-> MockedModus active");
            }
            return new AnnotationLiteral<CDICommonsMocked>(){};
        } else{
            return ctx.proceed();
        }
    }
}
