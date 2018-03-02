package com.java8.constructor;

/**
 * @author landyl
 * @create 1:52 PM 03/01/2018
 * Let's see how the :: keyword works for constructors.
 * First we define an example bean with different constructors:
 */
class Person {
    String firstName;
    String lastName;

    Person() {}

    Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}