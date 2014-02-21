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

package gui.org.rapidpm.modul.javafx.chart.pie.drilldown.model;

import java.util.Comparator;

import javax.inject.Inject;

import org.rapidpm.commons.cdi.logger.CDILogger;
import org.rapidpm.module.se.commons.logger.Logger;

/**
 * User: Sven Ruppert
 * Date: 30.08.13
 * Time: 09:23
 */
public class TransientDemoRowComparator implements Comparator<TransientDemoDataRow> {

    private @Inject @CDILogger Logger logger;

    @Override public int compare(TransientDemoDataRow o1, TransientDemoDataRow o2) {
        if (o1 == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("row1 == null");
            }
            if (o2 == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("row2 == null");
                }
                return 0;
            } else {
                return 1;
            }
        } else if (o2 == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("row1 == null");
            }
            return -1;
        } else {
            //compare
            final int compareVorname = o1.getVorname().compareTo(o2.getVorname());
            if (compareVorname == 0) {
                final int compareNachname = o1.getNachname().compareTo(o2.getNachname());
                if (compareNachname == 0) {
                    final int compareDatum = o1.getDatum().compareTo(o2.getDatum());
                    if (compareDatum == 0) {
                        final int compareBetrag = o1.getBetrag().compareTo(o2.getBetrag());
                        if (compareBetrag == 0) {
                            return 0;
                        } else {
                            return compareBetrag;
                        }
                    } else {
                        return compareDatum;
                    }
                } else {
                    return compareNachname;
                }
            } else {
                return compareVorname;
            }
        }
    }
}
