/*
 * Copyright (c) 2012.
 * This is part of the project "RapidPM"
 * from Sven Ruppert for RapidPM, please contact chef@sven-ruppert.de
 */


package junit.org.rapidpm.lang.cache.generic.book;

import org.rapidpm.lang.cache.generic.Cache;
import org.rapidpm.lang.cache.generic.CacheFinder;
import org.rapidpm.lang.cache.generic.GenericCacheThreadsave;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

public class BookCacheWrapper {
    private final Cache<Book> bookCache = new GenericCacheThreadsave<>(Book.class, true);
    private final CacheFinder<Book, String> monthFinder = bookCache.createCacheFinder("month");
    private final CacheFinder<Book, String> cwFinder = bookCache.createCacheFinder("cw");
    private final CacheFinder<Book, String> dateFinder = bookCache.createCacheFinder("date");
    private final CacheFinder<Book, String> keyWordFinder = bookCache.createCacheFinder("keyWords");

    public void put2Cache(final List<Book> bookList) throws IllegalAccessException, InvocationTargetException {
        for (final Book book : bookList) {
            put2Cache(book);
        }
    }

    public void put2Cache(final Book book) throws IllegalAccessException, InvocationTargetException {
        bookCache.fillCache(book);
    }

    public Collection<Book> getBooks4Month(final String month) {
        return monthFinder.findForKey(month);
    }

    public Collection<Book> getBooks4Cw(final String cw) {
        return cwFinder.findForKey(cw);
    }

    public Collection<Book> getBooks4Date(final String date) {
        return dateFinder.findForKey(date);
    }

    public Collection<Book> getBooks4KeyWords(final Collection<String> keyWords) {
        return keyWordFinder.findForKeys(keyWords);
    }

    public void removeBook(final Book book) throws IllegalAccessException, InvocationTargetException {
        bookCache.removeFromCache(book);
    }

    public void removeBook(final Collection<Book> books) throws IllegalAccessException, InvocationTargetException {
        bookCache.removeFromCache(books);
    }

    public Collection<Book> getAllBooks() {
        return bookCache.getAllFromCache();
    }
}
