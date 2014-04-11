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

package org.rapidpm.commons.cdi.tx;

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
import org.rapidpm.commons.cdi.logger.CDILogger;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert
 * Date: 12.07.13
 * Time: 12:46
 */
@RunWith(Arquillian.class)
public class CDITransactionTest {

    private @Inject @CDILogger Logger logger;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "org.rapidpm.commons")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }


//    @Inject Instance<CDITransactionA> cdiTransactionAInstance;
//    @Inject Instance<CDITransactionB> cdiTransactionBInstance;
//    @Inject Instance<CDITransactionC> cdiTransactionCInstance;

    @Inject Instance<CDITransaction> cdiTransactionInstance;

//    @CDITransactionScope
//    public static class CDITransactionA extends CDITransaction.CDITransactionStep {
//
//        @Inject @New Instance<DemoClassA> convClassAInstance;
//
//        public void doIt() {
//            final DemoClassA a = convClassAInstance.get();
//            final DemoClassA b = convClassAInstance.get();
//
//            Assert.assertNotNull(a);
//            Assert.assertNotNull(b);
//
//            Assert.assertNotEquals(a.getVersion(), b.getVersion());
//        }
//    }

    @CDITransactionScope
    public static class CDITransactionB extends CDITransaction.CDITransactionStep {

        @Inject Instance<DemoClassA> convClassBInstance;
        @Inject @New Instance<DemoClassA> convClassBInstanceNonTX;

        private String version;

        @Override
        public void doIt() {
            final DemoClassA a = convClassBInstance.get();
            final DemoClassA b = convClassBInstance.get();
            Assert.assertNotNull(a);
            Assert.assertNotNull(b);

            final String aVersion = a.getVersion();
            final String bVersion = b.getVersion();
            Assert.assertEquals(aVersion, bVersion);
            version = aVersion;

            final DemoClassA c = convClassBInstanceNonTX.get();
            Assert.assertNotNull(c);
            final String cVersion = c.getVersion();
            Assert.assertNotEquals(aVersion, cVersion);
            Assert.assertNotEquals(cVersion, bVersion);
        }

        public String getVersion() {
            return version;
        }
    }
//
//    public static class CDITransactionC extends CDITransaction.CDITransactionStep {
//
//        @Inject Instance<DemoClassA> convClassBInstanceA;
//        @Inject Instance<DemoClassA> convClassBInstanceB;
//        @Inject @New Instance<DemoClassA> convClassBInstanceNonTX;
//
//        @Override
//        public void doIt() {
//            final DemoClassA a = convClassBInstanceA.get();
//            final DemoClassA b = convClassBInstanceB.get();
//            Assert.assertNotNull(a);
//            Assert.assertNotNull(b);
//
//            Assert.assertEquals(a.getVersion(), b.getVersion());
//
//            final DemoClassA c = convClassBInstanceNonTX.get();
//            Assert.assertNotNull(c);
//            Assert.assertNotEquals(a.getVersion(), c.getVersion());
//            Assert.assertNotEquals(c.getVersion(), b.getVersion());
//        }
//    }

//    @Test
//    public void testNonTxScope() throws Exception {
//        final CDITransactionA tx = cdiTransactionAInstance.get();
//        tx.execute();
//    }


//    @Test
//    public void testTxScope() throws Exception {
//        final CDITransaction cdiTransaction = cdiTransactionInstance.get();
//        final CDITransaction.CDITransactionStep step = new CDITransaction.CDITransactionStep() {
//
//            @Inject @New Instance<DemoClassA> convClassAInstance;
//
//            public void doIt() {
//                final DemoClassA a = convClassAInstance.get();
//                final DemoClassA b = convClassAInstance.get();
//
//                Assert.assertNotNull(a);
//                Assert.assertNotNull(b);
//
//                Assert.assertNotEquals(a.getVersion(), b.getVersion());
//            }
//        };
//        cdiTransaction.addCDITransactionStep(step);
//        cdiTransaction.execute();
//    }

//    @Test
//    public void testTxTwoScopes001() throws Exception {
//        final CDITransactionB txA = cdiTransactionBInstance.get();
//        final CDITransactionB txB = cdiTransactionBInstance.get();
//        txA.execute();
//        txB.execute();
//        Assert.assertNotEquals(txA.getTxNumber(), txB.getTxNumber());
//        Assert.assertNotEquals(txA.getVersion(), txB.getVersion());
//    }
//    @Test
//    public void testTxTwoScopes002() throws Exception {
//        final CDITransactionA txA = cdiTransactionAInstance.get();
//        final CDITransactionA txB = cdiTransactionAInstance.get();
//        txA.execute();
//        txB.execute();
//        Assert.assertNotEquals(txA.txNumber, txB.txNumber);
//        Assert.assertNotEquals(txA.getVersion(), txB.getVersion());
//    }
//
//    @Test
//    public void testTxTwoScopes003() throws Exception {
//        final CDITransactionC txA = cdiTransactionCInstance.get();
//        final CDITransactionC txB = cdiTransactionCInstance.get();
//        txA.execute();
//        txB.execute();
//        Assert.assertNotEquals(txA.txNumber, txB.txNumber);
//        Assert.assertNotEquals(txA.getVersion(), txB.getVersion());
//    }

    @Test
    public void testMultipleTransactions() throws Exception {
        final CDITransaction cdiTransactionA = cdiTransactionInstance.get();
        final CDITransaction cdiTransactionB = cdiTransactionInstance.get();

        String versionA = "";
        String versionB = "";

        final CDITransaction.CDITransactionStep step = new CDITransaction.CDITransactionStep() {
            @Inject Instance<DemoClassA> convClassBInstance;
            @Inject @New Instance<DemoClassA> convClassBInstanceNonTX;

            private String version;

            @Override
            public void doIt() {
                final DemoClassA a = convClassBInstance.get();
                final DemoClassA b = convClassBInstance.get();
                Assert.assertNotNull(a);
                Assert.assertNotNull(b);

                final String aVersion = a.getVersion();
                final String bVersion = b.getVersion();
                Assert.assertEquals(aVersion, bVersion);
                version = aVersion;
                System.out.println("version von A = " + version);
                final DemoClassA c = convClassBInstanceNonTX.get();
                Assert.assertNotNull(c);
                final String cVersion = c.getVersion();
                Assert.assertNotEquals(aVersion, cVersion);
                Assert.assertNotEquals(cVersion, bVersion);
            }

            public String getVersion() {
                return version;
            }
        };
        cdiTransactionA.addCDITransactionStep(step);
        cdiTransactionB.addCDITransactionStep(step);


//        final CDITransactionB txA = cdiTransactionBInstance.get();
//        final CDITransactionB txB = cdiTransactionBInstance.get();
        cdiTransactionA.execute();
        cdiTransactionB.execute();

//        Assert.assertNotEquals(txA.getVersion(), txB.getVersion());
    }

}
