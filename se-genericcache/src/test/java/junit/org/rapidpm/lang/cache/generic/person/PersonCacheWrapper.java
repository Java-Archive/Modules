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


package junit.org.rapidpm.lang.cache.generic.person;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.rapidpm.lang.cache.generic.Cache;
import org.rapidpm.lang.cache.generic.CacheFinder;
import org.rapidpm.lang.cache.generic.GenericCacheThreadsave;

public class PersonCacheWrapper {
    private final Cache<Person> personCache = new GenericCacheThreadsave<>(Person.class, true);
    private final CacheFinder<Person, String> firstNameFinder = personCache.createCacheFinder("firstName");
    private final CacheFinder<Person, String> lastNameFinder = personCache.createCacheFinder("lastName");
    private final CacheFinder<Person, Date> birthFinder = personCache.createCacheFinder("birth");
    private final CacheFinder<Person, Integer> employeeNoFinder = personCache.createCacheFinder("employeeNo");

    public void put2Cache(final List<Person> personList) throws IllegalAccessException, InvocationTargetException {
        for (final Person person : personList) {
            put2Cache(person);
        }
    }

    public void put2Cache(final Person person) throws IllegalAccessException, InvocationTargetException {
        personCache.fillCache(person);
    }

    public Collection<Person> getPersons4FirstName(final String name) {
        return firstNameFinder.findForKey(name);
    }

    public Collection<Person> getPersons4LastName(final String name) {
        return lastNameFinder.findForKey(name);
    }

    public Collection<Person> getPersons4Birth(final Date birth) {
        return birthFinder.findForKey(birth);
    }

    public Collection<Person> getPersons4EmployeeNo(final int employeeNo) {
        return employeeNoFinder.findForKey(employeeNo);
    }

    public void removePerson(final Person person) throws IllegalAccessException, InvocationTargetException {
        personCache.removeFromCache(person);
    }

    public void removePerson(final Collection<Person> persons) throws IllegalAccessException, InvocationTargetException {
        personCache.removeFromCache(persons);
    }

    public Collection<Person> getAllPersons() {
        return personCache.getAllFromCache();
    }
}
