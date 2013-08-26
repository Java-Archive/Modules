package org.rapidpm.demo.cdi.commons.logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rapidpm.module.se.commons.logger.Logger;


/**
 * LoggerProducer Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Jun 3, 2013</pre>
 */
@RunWith(Arquillian.class)
public class LoggerProducerTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "org.rapidpm.demo.cdi.commons")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }


    @Inject
    @CDILogger
    Logger logger;

    /**
     * Method: produceLog4JLogger(InjectionPoint injectionPoint)
     */
    @Test
    public void testProduceLog4JLogger() throws Exception {
        Assert.assertNotNull(logger);
        Assert.assertTrue(logger instanceof Logger);

    }


} 
