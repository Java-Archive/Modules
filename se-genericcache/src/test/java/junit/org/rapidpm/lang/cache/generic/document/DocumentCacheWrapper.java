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

package junit.org.rapidpm.lang.cache.generic.document;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.rapidpm.lang.cache.generic.Cache;
import org.rapidpm.lang.cache.generic.CacheFinder;
import org.rapidpm.lang.cache.generic.GenericCacheThreadsave;


public class DocumentCacheWrapper {
    private static final Logger logger = Logger.getLogger(DocumentCacheWrapper.class);

    private final Cache<Document> cache = new GenericCacheThreadsave<>(Document.class, true);

    private final CacheFinder<Document, Long> idFinder = cache.createCacheFinder("docId");

    public void put2Cache(final Document document) throws IllegalAccessException, InvocationTargetException {
        cache.fillCache(document);
    }

    public void removeDocument(final Document document) throws IllegalAccessException, InvocationTargetException {
        cache.removeFromCache(document);
    }


    public Collection<Document> getDocument(final long docId) {
        final Collection<Document> collection = idFinder.findForKey(docId);
        return collection;
    }


}
