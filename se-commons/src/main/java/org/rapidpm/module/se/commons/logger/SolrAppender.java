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

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.common.SolrInputDocument;

/**
 * User: Sven Ruppert Date: 18.09.13 Time: 10:03
 */
public class SolrAppender extends AppenderSkeleton implements Serializable {

    private static final int QUEUE_SIZE = 100;
    private static final int THREAD_COUNT = 10;

    private ConcurrentUpdateSolrServer solrServer;
    private String localhostname;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HHmmss");
    private Random random = new Random(System.nanoTime());

    //    private String solrurl = "http://localhost:8983/solr/logger";
    private String solrurl = null;

    private void init() {

        solrServer = new ConcurrentUpdateSolrServer(solrurl, QUEUE_SIZE, THREAD_COUNT); //REFAC mandantenfähig machen
        solrServer.setParser(new XMLResponseParser());

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override public void run() {
                try {
                    if (liste.isEmpty()) {

                    } else {
                        solrServer.add(liste);
                        softCommit();
                        solrServer.commit();
                    }
                    solrServer.shutdownNow();
                } catch (SolrServerException | IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * Create new instance.
     */
    public SolrAppender() {
//        init();
    }

    /**
     * Create new instance. Provided for compatibility with log4j 1.3.
     *
     * @param isActive true if appender is ready for use upon construction. Not used in log4j 1.2.x.
     *
     * @since 1.2.15
     */
    public SolrAppender(boolean isActive) {
        super(isActive);
//        init();
    }

    /**
     * Subclasses of <code>AppenderSkeleton</code> should implement this method to perform actual logging. See also {@link #doAppend AppenderSkeleton.doAppend} method.
     *
     * @since 0.9.0
     */
    @Override protected void append(LoggingEvent event) {
        if (solrServer == null) {
            init();
        } else {

        }

        subAppend(event);   //TODO change per CDI
    }

    /**
     * Release any resources allocated within the appender such as file handles, network connections, etc.
     *
     * <p>It is a programming error to append to a closed appender.
     *
     * @since 0.8.4
     */
    @Override public void close() {
        try {
            solrServer.add(liste);
            softCommit();
            solrServer.commit();
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
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
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }


    private int batchCounter = 0;
    private List<SolrInputDocument> liste = new ArrayList<>();

    protected void subAppend(LoggingEvent event) {
        int index = event.getLoggerName().lastIndexOf('.');
        String loggerName;

        if (index > -1) {
            loggerName = event.getLoggerName().substring(index + 1);
        } else {
            loggerName = event.getLoggerName();
        }

        final SolrInputDocument d = new SolrInputDocument();
        d.addField("loggername", loggerName);
        d.addField("hostname", localhostname);
        d.addField("id", System.nanoTime() + "-" + random.nextInt() + "-" + random.nextInt());

        d.addField("threadname", event.getThreadName());
        d.addField("timestamp", event.getTimeStamp());
        d.addField("date", sdf.format(new Date(event.getTimeStamp())));
        d.addField("level", event.getLevel());
        d.addField("message", event.getRenderedMessage());

        final LocationInfo locationInformation = event.getLocationInformation();
        final String className = locationInformation.getClassName();
        d.addField("className", className);
        final String fileName = locationInformation.getFileName();
        d.addField("fileName", fileName);
        final String lineNumber = locationInformation.getLineNumber();
        d.addField("lineNumber", lineNumber);
        final String methodName = locationInformation.getMethodName();
        d.addField("methodName", methodName);

        liste.add(d);

        try {
//            solrServer.commit();
            if (batchCounter < QUEUE_SIZE) {
                batchCounter = batchCounter + 1;
            } else {
                batchCounter = 0;
                solrServer.add(liste);
                softCommit();
                liste.clear();
            }
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }


    }


    private void softCommit() throws IOException, SolrServerException {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setParam("soft-commit", "true");
        updateRequest.setParam("content-type", "application/xml");
        updateRequest.setAction(UpdateRequest.ACTION.COMMIT, false, false);
        updateRequest.process(solrServer);
    }

    public String getSolrurl() {
        return solrurl;
    }

    public void setSolrurl(String solrurl) {
        this.solrurl = solrurl;
    }
}
