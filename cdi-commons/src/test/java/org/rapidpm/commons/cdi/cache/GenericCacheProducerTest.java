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

package org.rapidpm.commons.cdi.cache;

import java.util.Collection;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rapidpm.lang.cache.generic.Cache;

/**
 * Created by Sven Ruppert on 31.07.13.
 */

@RunWith(Arquillian.class)
public class GenericCacheProducerTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "org.rapidpm.commons")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject @CDIGenericCache(clazz2Cache = CachedClass.class) private Cache cache;

    @Test
    public void testProduce01Test() throws Exception {
        Assert.assertNotNull(cache);
        final CachedClass cachedClass = new CachedClass();
        cachedClass.setTxt("001");
        cachedClass.setValue(1L);

        cache.fillCache(cachedClass);

        final Collection forKey = cache.findForKey("txt", "001");
        Assert.assertNotNull(forKey);
        Assert.assertFalse(forKey.isEmpty());

        for (final Object o : forKey) {
            Assert.assertEquals(cachedClass, o);
        }


    }
}
