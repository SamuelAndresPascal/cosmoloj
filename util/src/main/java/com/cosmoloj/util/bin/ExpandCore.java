package com.cosmoloj.util.bin;

import java.nio.ByteOrder;

/**
 *
 * @author Samuel Andrés
 */
public class ExpandCore {

    private final byte[] in;
    private final int to;
    private final FillOrder fOrder;
    private final ByteOrder bOrder;
    /*
    Un int tient au mieux sur le nombre de bytes d'un Integer s'il est exactement ajusté et au pire sur un byte de plus
    s'il y a un décalage + 1.
    */
    private final int[] toJoin = new int[Integer.BYTES + 1];
    private int byteCursor;
    private int bitCursor;

    public ExpandCore(final byte[] in, final int from, final int to,
            final int bitCursor, final FillOrder fOrder, final ByteOrder bOrder) {
        this.in = in;
        this.byteCursor = from;
        this.to = to;
        this.bitCursor = bitCursor;
        this.fOrder = fOrder;
        this.bOrder = bOrder;
    }

    public ExpandCore(final byte[] in, final int from, final int to, final FillOrder fOrder, final ByteOrder bOrder) {
        this(in, from, to, 0, fOrder, bOrder);
    }

    public ExpandCore(final byte[] in, final FillOrder fOrder, final ByteOrder bOrder) {
        this(in, 0, in.length, fOrder, bOrder);
    }

    public int getByteCursor() {
        return byteCursor;
    }

    public int getBitCursor() {
        return bitCursor;
    }

    public int get(final int size) {
        final int left = Byte.SIZE - bitCursor;
        if (left < size) {
            /*
            Il faut vérifier qu'on dispose de suffisamment de bytes en réserve.
            Cela est donné en ajoutant le curseur du byte courant et le nombre de bytes nécessaires à la lecture. Il
            faut encore retirer 1 (le byte courant qui est déjà compté dans le curseur de byte).
            */
            final int bytes = BinaryUtil.bytesFor(size, bitCursor);
            if (byteCursor + bytes - 1 < to) {
                // dans ce cas, on a suffisamment de bytes suivant dans le tableau d'entrée pour servir de complément
                toJoin[0] = fOrder.extract(in[byteCursor], bitCursor, left, bOrder);
                byteCursor++;

                ////
                for (int i = 1; i < bytes - 1; i++) {
                    toJoin[i] = Byte.toUnsignedInt(in[byteCursor]);
                    byteCursor++;
                }
                ////

                // calcul du curseur de bits sur le dernier byte
                bitCursor = (size - left) % Byte.SIZE;
                if (bitCursor == 0) {
                    toJoin[bytes - 1] = Byte.toUnsignedInt(in[byteCursor]);
                    byteCursor++;
                } else {
                    toJoin[bytes - 1] = fOrder.extract(in[byteCursor], 0, bitCursor, bOrder);
                }

                return fOrder.join(toJoin, bytes, bitCursor, left, bOrder);
            } else {
                // dans ce cas, on est arrivé au bout du tableau d'entrée et il faut compléter avec des zéros
                return fOrder.complete(in[byteCursor], bitCursor, left, size, bOrder);
            }
        } else {
            // dans ce cas, on a suffisamment de bits dans le byte courant d'entrée
            final int result = fOrder.extract(in[byteCursor], bitCursor, size, bOrder);
            if (left == size) {
                bitCursor = 0;
                byteCursor++;
            } else {
                bitCursor += size;
            }
            return result;
        }
    }
}
