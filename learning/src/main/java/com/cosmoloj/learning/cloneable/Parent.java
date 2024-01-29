package com.cosmoloj.learning.cloneable;

/**
 *
 * @author Samuel Andrés
 */
public class Parent implements Cloneable {

    private final String name;
    private final String surname;
    private final AttributeCloneable phone1;

    public Parent(final String name, final String surname, final AttributeCloneable phone1) {
        this.name = name;
        this.surname = surname;
        this.phone1 = phone1;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public AttributeCloneable getPhone1() {
        return phone1;
    }

    @Override
    public Parent clone() throws CloneNotSupportedException {
        final Parent clone = (Parent) super.clone();
        return clone;
    }

    @Override
    public String toString() {
        return "Parent{" + "name=" + name + ", phone1=" + phone1 + '}';
    }
}
