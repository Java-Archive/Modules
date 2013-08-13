/*
 * Copyright (c) 2012.
 * This is part of the project "RapidPM"
 * from Sven Ruppert for RapidPM, please contact chef@sven-ruppert.de
 */

package junit.org.rapidpm.lang.cache.generic.document;

import org.rapidpm.lang.cache.generic.Cache;
import org.rapidpm.lang.cache.generic.CacheFinder;
import org.rapidpm.lang.cache.generic.GenericCacheThreadsave;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;


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
