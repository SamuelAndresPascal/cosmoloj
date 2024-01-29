package com.cosmoloj.learning.cloneable;

/**
 *
 * @author Samuel Andrés
 */
public class Child extends Parent {

    private final int age;
    private final AttributeNotCloneable phone2;

    public Child(final String name, final String surname, final AttributeCloneable phone1, final int age,
            final AttributeNotCloneable phone2) {
        super(name, surname, phone1);
        this.age = age;
        this.phone2 = phone2;
    }

    public int getAge() {
        return age;
    }

    public AttributeNotCloneable getPhone2() {
        return phone2;
    }

    @Override
    public Child clone() throws CloneNotSupportedException {
        final Child clone = (Child) super.clone();
        return clone;
    }

    @Override
    public String toString() {
        return "Child{" + "age=" + age + ", phone2=" + phone2 + '}';
    }
}
