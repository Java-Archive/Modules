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

import java.util.List;

import org.rapidpm.lang.cache.generic.Cacheable;

@Cacheable(primaryKeyAttributeName = "isbn")
public class Book {
    private final String title;  // the da vinci code
    private final String author; // dan brown
    private final String isbn;   // 123-11-11
    private final String cw;     // 2006KW1
    private final String date;   // 20060104
    private final String month;  // 200601
    private final List<String> keyWords; //[nature, water, fish]

    public Book(final String title, final String author, final String isbn, final String cw, final String date, final String month, final List<String> keyWords) {
        super();
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.cw = cw;
        this.date = date;
        this.month = month;
        this.keyWords = keyWords;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getMonth() {
        return month;
    }

    public String getCw() {
        return cw;
    }

    public String getDate() {
        return date;
    }

    public List<String> getKeyWords() {
        return keyWords;
    }

    @Override
    public String toString() {
        return "book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", month='" + month + '\'' +
                ", cw='" + cw + '\'' +
                ", date='" + date + '\'' +
                ", keyWords=" + keyWords +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Book book = (Book) o;

        if (isbn != null ? !isbn.equals(book.isbn) : book.isbn != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return (isbn != null ? isbn.hashCode() : 0);
    }
}
