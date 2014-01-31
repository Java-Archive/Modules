
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

public class PersonCacheWrapperThreadTest extends TestCase {
    private List<Person> persons;
    private PersonCacheWrapper personCache = new PersonCacheWrapper();
    private static final int READING_THREAD_COUNT = 40;
    private static final int WRITING_THREAD_COUNT = 5;
    private static final int LOOPS = 1000;
    private static final int OUTPUT = 1;
    private static final int READ_DELAY = 32;
    private static final int WRITE_DELAY = 1;


    @Override
    protected void setUp() throws Exception {
        persons = createPersonList();
        personCache.put2Cache(persons);
    }

    public void testFillCache() {
        assertEquals(3, personCache.getAllPersons().size());
    }

    boolean writersFinished = false;
    boolean readersFinished = true;

    public void testGetAndPutWithThreads() {
        for (int i = 0; i < WRITING_THREAD_COUNT; i++) {
            new Thread("write-thread " + i) {
                @Override
                public void run() {
                    for (int loop = 0; loop < LOOPS; loop++) {
                        try {
                            final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                            final Person person = new Person("1name" + loop, "2name" + loop, dateFormat.parse("04.04.1974"),
                                    loop);
                            personCache.put2Cache(person);
//                            System.out.println(this.getName() + " : " + person);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            sleep(WRITE_DELAY);
                        } catch (Exception e) {
                            System.out.println("abort " + getName());
                        }
                    }
                    if (getName().equals("write-thread " + (WRITING_THREAD_COUNT - 1))) {
                        writersFinished = true;
                    }
                }
            }.start();
        }
        // create writing threads
        for (int i = 0; i < READING_THREAD_COUNT; i++) {
            new Thread("read-thread " + i) {
                @Override
                public void run() {
                    for (int loop = 0; loop < LOOPS; loop++) {
                        final Collection<Person> foundPersons = personCache.getPersons4FirstName("1name" + loop);
                        for (final Person person : foundPersons) {
//                            System.out.println(this.getName() + " : " + person);
                        }
                        try {
                            sleep(READ_DELAY);
                        } catch (Exception e) {
                            System.out.println("abort " + getName());
                        }
                    }
                }
            }.start();
            if (getName().equals("read-thread " + (READING_THREAD_COUNT - 1))) {
                readersFinished = true;
            }
        }
        while (!writersFinished || !readersFinished) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }
    }

    public void testGetPersons4FirstName() {
        Collection<Person> persons4FirstName = personCache.getPersons4FirstName("Ingo");
        assertTrue(persons4FirstName.contains(persons.get(0)));
        assertEquals(1, persons4FirstName.size());

        persons4FirstName = personCache.getPersons4Birth(null);
        assertTrue(persons4FirstName.contains(persons.get(2)));
        assertEquals(1, persons4FirstName.size());
    }

    public void testGetPersons4LastName() {
        final Collection<Person> persons4LastName = personCache.getPersons4LastName("Gerberding");
        assertTrue(persons4LastName.contains(persons.get(1)));
        assertEquals(1, persons4LastName.size());
    }

    public void testGetPersons4Birth() throws ParseException {
        final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        final Date birth = dateFormat.parse("04.04.1974");
        final Collection<Person> persons4Birth = personCache.getPersons4Birth(birth);
        assertTrue(persons4Birth.contains(persons.get(0)));
        assertEquals(1, persons4Birth.size());
    }

    public void testGetPersons4EmployeeNo() {
        final Collection<Person> persons4EmployeeNo = personCache.getPersons4EmployeeNo(6);
        assertTrue(persons4EmployeeNo.contains(persons.get(1)));
        assertEquals(1, persons4EmployeeNo.size());
    }

    public void testRemoveFromCache() throws IllegalAccessException, InvocationTargetException {
        final List<Person> personListClone = new ArrayList<Person>();
        personListClone.addAll(persons);

        Collection<Person> persons4FirstName = personCache.getPersons4FirstName("Ingo");

        personCache.removePerson(persons4FirstName);

        final Collection<Person> allPersons = personCache.getAllPersons();
        assertEquals(2, allPersons.size());
        persons4FirstName = personCache.getPersons4FirstName("Ingo");
        assertEquals(0, persons4FirstName.size());

        assertTrue(allPersons.contains(personListClone.get(1)));
    }


    private static List<Person> createPersonList() throws ParseException {
        final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        final List<Person> personList = new ArrayList<Person>();
        // person 0
        Person person = new Person("Ingo", "Goldbeck", dateFormat.parse("04.04.1974"), 25);
        personList.add(person);
        // person 1
        person = new Person("Frank", "Gerberding", dateFormat.parse("03.03.1969"), 6);
        personList.add(person);
        // person 2
        person = new Person(null, null, null, 0);
        personList.add(person);
        return personList;
    }
}
