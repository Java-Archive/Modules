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

import java.util.Date;

import org.rapidpm.lang.cache.generic.Cacheable;

@Cacheable(primaryKeyAttributeName = "lastName")
public class Person {
    private final String firstName;
    private final String lastName;
    private final Date birth;
    private final int employeeNo;

    public Person(final String firstName, final String lastName, final Date birth, final int employeeNo) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.birth = birth;
        this.employeeNo = employeeNo;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getBirth() {
        return birth;
    }

    public int getEmployeeNo() {
        return employeeNo;
    }


    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birth=" + birth +
                ", employeeNo=" + employeeNo +
                '}';
    }
}
