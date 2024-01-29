package com.cosmoloj.learning.cloneable;

/**
 *
 * @author Samuel Andrés
 */
public class AttributeNotCloneable {

    private final String phone;

    public AttributeNotCloneable(final String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public AttributeNotCloneable clone() throws CloneNotSupportedException {
        final AttributeNotCloneable clone = (AttributeNotCloneable) super.clone();
        return clone;
    }

    @Override
    public String toString() {
        return "Attribute{" + "phone=" + phone + '}';
    }
}
