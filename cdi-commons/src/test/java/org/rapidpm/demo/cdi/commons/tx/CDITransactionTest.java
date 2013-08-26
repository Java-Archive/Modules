package org.rapidpm.demo.cdi.commons.tx;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.New;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert
 * Date: 12.07.13
 * Time: 12:46
 */
@RunWith(Arquillian.class)
public class CDITransactionTest {

    private
    @Inject
    @CDILogger
    Logger logger;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "org.rapidpm.demo.cdi.commons")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }


    @Inject Instance<CDITransactionA> cdiTransactionAInstance;
    @Inject Instance<CDITransactionB> cdiTransactionBInstance;

    public static class CDITransactionA extends AbstractCDITransaction {

        @Inject @New Instance<DemoClassA> convClassAInstance;

        @Override
        public void doIt() {
            final DemoClassA a = convClassAInstance.get();
            final DemoClassA b = convClassAInstance.get();

            Assert.assertNotNull(a);
            Assert.assertNotNull(b);

            Assert.assertNotEquals(a.getVersion(), b.getVersion());
        }
    }

    public static class CDITransactionB extends AbstractCDITransaction {

        @Inject Instance<DemoClassA> convClassBInstance;
        @Inject @New Instance<DemoClassA> convClassBInstanceNonTX;

        @Override
        public void doIt() {
            final DemoClassA a = convClassBInstance.get();
            final DemoClassA b = convClassBInstance.get();
            Assert.assertNotNull(a);
            Assert.assertNotNull(b);

            Assert.assertEquals(a.getVersion(), b.getVersion());

            final DemoClassA c = convClassBInstanceNonTX.get();
            Assert.assertNotNull(c);
            Assert.assertNotEquals(a.getVersion(), c.getVersion());
            Assert.assertNotEquals(c.getVersion(), b.getVersion());
        }
    }

    @Test
    public void testNonTxScope() throws Exception {
        final CDITransactionA tx = cdiTransactionAInstance.get();
        tx.execute();
    }


    @Test
    public void testTxScope() throws Exception {
        final CDITransactionB tx = cdiTransactionBInstance.get();
        tx.execute();
    }


}
