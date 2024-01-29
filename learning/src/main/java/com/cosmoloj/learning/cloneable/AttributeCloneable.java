package com.cosmoloj.learning.cloneable;

/**
 *
 * @author Samuel Andrés
 */
public class AttributeCloneable implements Cloneable {

    private final String phone;

    public AttributeCloneable(final String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public AttributeCloneable clone() throws CloneNotSupportedException {
        final AttributeCloneable clone = (AttributeCloneable) super.clone();
        return clone;
    }

    @Override
    public String toString() {
        return "Attribute{" + "phone=" + phone + '}';
    }
}
