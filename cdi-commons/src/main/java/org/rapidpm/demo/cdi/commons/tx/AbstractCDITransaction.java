package org.rapidpm.demo.cdi.commons.tx;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.rapidpm.demo.cdi.commons.logger.Logger;
import org.rapidpm.demo.cdi.commons.logger.CDILogger;
import org.rapidpm.lang.cache.generic.Cacheable;

/**
 * User: Sven Ruppert
 * Date: 11.07.13
 * Time: 11:30
 */

@Cacheable(primaryKeyAttributeName = "txNumber")
public abstract class AbstractCDITransaction implements CDITransaction, Serializable {

    public String txNumber = System.nanoTime()+"";


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
