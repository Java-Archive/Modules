/*
 * Copyright [2014] [www.rapidpm.org / Sven Ruppert (sven.ruppert@rapidpm.org)]
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

package org.arangodb.objectmapper.http;

import org.apache.http.conn.*;

import java.lang.ref.WeakReference;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/** 
 * Original file from "Java API for CouchDB http://www.ektorp.org"
 * 
 * @author henrik lundgren
 * 
 */

public class IdleConnectionMonitor {

	private final static long DEFAULT_IDLE_CHECK_INTERVAL = 30;
	
	private final static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1, new ThreadFactory() {
		
		private final AtomicInteger threadCount = new AtomicInteger(0);
		
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			t.setDaemon(true);
			t.setName(String.format("arangodb-idle-connection-monitor-thread-%s", threadCount.incrementAndGet()));
			return t;
		}
	});
	
    public static void monitor(ClientConnectionManager cm) {
        CleanupTask cleanupTask = new CleanupTask(cm);
        ScheduledFuture<?> cleanupFuture = executorService.scheduleWithFixedDelay(cleanupTask, DEFAULT_IDLE_CHECK_INTERVAL, 
                                                                                DEFAULT_IDLE_CHECK_INTERVAL, TimeUnit.SECONDS);
        cleanupTask.setFuture(cleanupFuture);
    }
	
	private static class CleanupTask implements Runnable {

        private final WeakReference<ClientConnectionManager> cm;
        private ScheduledFuture<?> thisFuture;

        CleanupTask(ClientConnectionManager cm) {
            this.cm = new WeakReference<ClientConnectionManager>(cm);
        }

        public void setFuture(ScheduledFuture<?> future) {
            thisFuture = future;
        }

        public void run() {
            if (cm.get() != null) {
                cm.get().closeExpiredConnections();
            } else if (thisFuture != null) {
                thisFuture.cancel(false);
            }
        }
		
	}
	
}
