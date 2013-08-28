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

package org.rapidpm.demo.cdi.commons.async;

import java.util.concurrent.CountDownLatch;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert
 * Date: 31.07.13
 * Time: 11:02
 */
@ApplicationScoped
public class TestService1 {
    static final CountDownLatch TEST_LATCH = new CountDownLatch(1);

    //private static final Logger logger = Logger.getLogger(TestService1.class);
    private
    @Inject
    @CDILogger
    Logger logger;

    private boolean called = false;

    @Async
    public void call() {
        try {
            Thread.sleep(1 * 1000);
            if (logger.isDebugEnabled()) {
                logger.debug("wach again");
            }
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
        this.called = true;
        if (logger.isDebugEnabled()) {
            logger.debug("called - " + true);
        }
        TEST_LATCH.countDown();
    }

    public boolean isCalled() {
        return called;
    }
}
