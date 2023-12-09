package com.cosmoloj.format.dbf.v3;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 *
 * @author Samuel Andrés
 */
public final class DbfNumericIntegerColumnHeader extends DbfColumnHeader<Integer> {

    public DbfNumericIntegerColumnHeader(final String name, final int memory, final byte size) {
        super(name, DbfType.NUMERIC, memory, size, (byte) 0);
    }

    @Override
    public Integer fromBytes(final byte[] bytes) {
        return Integer.parseInt(new String(bytes, StandardCharsets.ISO_8859_1).trim());
    }

    @Override
    public byte[] toBytes(final Integer data) {
        final byte[] convert = data.toString().getBytes(StandardCharsets.ISO_8859_1);
        if (convert.length == getSize()) {
            return convert;
        } else if (convert.length < getSize()) {
            /*
            Si la forme binaire de l'entier est plus courte que la place, il faut écrire la valeur à la fin et remplir
            d'espaces avant
            */
            // on crée un tableau de la bonne taille
            final byte[] temp = new byte[getSize()];

            // on remplit d'espaces avant la copie
            Arrays.fill(temp, (byte) 32);

            // on copie le tableau lu à la fin du nouveau tableau
            System.arraycopy(convert, 0, temp, temp.length - convert.length, convert.length);
            return temp;
        } else {
            throw new IllegalStateException();
        }
    }
}
