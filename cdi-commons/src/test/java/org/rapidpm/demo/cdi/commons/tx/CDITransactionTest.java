/*
 * Copyright [2013] [www.rapidpm.org / Sven Ruppert (sven.ruppert@rapidpm.org)]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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
