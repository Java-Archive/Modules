package org.rapidpm.demo.cdi.commons.contextresolver;

import org.rapidpm.demo.cdi.commons.CDINotMapped;

/**
 * User: Sven Ruppert
 * Date: 24.10.13
 * Time: 13:47
 */
@CDINotMapped
public interface CDIContext {

    public boolean isMockedModusActive();

}
