/*
 * Copyright (c) 2012.
 * This is part of the project "RapidPM"
 * from Sven Ruppert for RapidPM, please contact chef@sven-ruppert.de
 */


package junit.org.rapidpm.lang.cache.generic.person;

import org.rapidpm.lang.cache.generic.Cacheable;

import java.util.Date;
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
