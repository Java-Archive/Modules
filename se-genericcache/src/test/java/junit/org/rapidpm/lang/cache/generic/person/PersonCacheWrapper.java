/*
 * Copyright (c) 2012.
 * This is part of the project "RapidPM"
 * from Sven Ruppert for RapidPM, please contact chef@sven-ruppert.de
 */


package junit.org.rapidpm.lang.cache.generic.person;

import org.rapidpm.lang.cache.generic.Cache;
import org.rapidpm.lang.cache.generic.CacheFinder;
import org.rapidpm.lang.cache.generic.GenericCacheThreadsave;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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
