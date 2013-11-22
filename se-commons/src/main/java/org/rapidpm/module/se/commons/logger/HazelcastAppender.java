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

package org.rapidpm.module.se.commons.logger;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

import java.io.Serializable;

/**
 * User: Sven Ruppert Date: 18.09.13 Time: 14:22
 */
public class HazelcastAppender extends AppenderSkeleton implements Serializable {


    /**
     * Subclasses of <code>AppenderSkeleton</code> should implement this method to perform actual logging. See also {@link #doAppend AppenderSkeleton.doAppend} method.
     *
     * @since 0.9.0
     */
    @Override protected void append(LoggingEvent event) {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    /**
     * Release any resources allocated within the appender such as file handles, network connections, etc.
     *
     * <p>It is a programming error to append to a closed appender.
     *
     * @since 0.8.4
     */
    @Override public void close() {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    /**
     * Configurators call this method to determine if the appender requires a layout. If this method returns <code>true</code>, meaning that layout is required, then the configurator will configure an
     * layout using the configuration information at its disposal.  If this method returns <code>false</code>, meaning that a layout is not required, then layout configuration will be skipped even if
     * there is available layout configuration information at the disposal of the configurator..
     *
     * <p>In the rather exceptional case, where the appender implementation admits a layout but can also work without it, then the appender should return <code>true</code>.
     *
     * @since 0.8.4
     */
    @Override public boolean requiresLayout() {
        return true;
    }
}
