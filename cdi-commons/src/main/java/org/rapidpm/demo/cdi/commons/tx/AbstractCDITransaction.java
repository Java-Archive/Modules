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

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.lang.cache.generic.Cacheable;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert
 * Date: 11.07.13
 * Time: 11:30
 */

@Cacheable(primaryKeyAttributeName = "txNumber")
public abstract class AbstractCDITransaction implements CDITransaction, Serializable {

    public String txNumber = System.nanoTime() + "";


    private @Inject @CDILogger
    Logger logger;

    @Inject CDITransactionContext transactionContext;

    @PostConstruct
    public void init() {

    }

    public void execute() {
        begin();
        doIt();
        end();
    }

    public abstract void doIt();

    public void begin() {
        transactionContext.begin();
    }

    public void end() {
        transactionContext.begin();
    }
}
