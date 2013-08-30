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


package junit.org.rapidpm.lang.cache.generic.book;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

import org.rapidpm.lang.cache.generic.Cache;
import org.rapidpm.lang.cache.generic.CacheFinder;
import org.rapidpm.lang.cache.generic.GenericCacheThreadsave;

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
