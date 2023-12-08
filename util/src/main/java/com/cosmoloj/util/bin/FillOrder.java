package com.cosmoloj.util.bin;

import java.nio.ByteOrder;
import java.util.Arrays;

/**
 *
 * @author Samuel Andrés
 */
public enum FillOrder {

    /**
     * <div class="fr">Le curseur compte 0 sur le bit de poids le plus faible.</div>
     */
    FROM_LOW {
        @Override
        public int join(final int[] toJoin, final int length, final int bitCursor, final int left,
                final ByteOrder order) {
            int result = 0;

            if (ByteOrder.LITTLE_ENDIAN.equals(order)) {
                for (int i = 1; i < length; i++) {
                    result |= (toJoin[i] << (i - 1) * Byte.SIZE);
                }
                result <<= left;
                return result | toJoin[0];
            }

            throw new UnsupportedOperationException("byte order not supported");
        }

        /**
         * <pre>
         * 00000000|00000000|00000000|ABCDEFGH
         *                                 ^==cursor
         * DEFGH000|00000000|00000000|00000000 (( Integer.SIZE - cursor - size
         * 00000000|00000000|00000000|000000DE )) cursor
         * </pre>
         */
        @Override
        public int complete(final byte in, final int bitCursor, final int left, final int size, final ByteOrder order) {
            return extract(in, bitCursor, size, order);
        }

        /**
         * <pre>
         * 00000000|00000000|00000000|ABCDEFGH
         *                                   ^cursor
         * FGH00000|00000000|00000000|00000000 (( Integer.SIZE - cursor - size
         * 00000000|00000000|00000000|000000FG )) Integer.SIZE - size
         * </pre>
         */
        @Override
        public int extract(final byte in, final int bitCursor, final int size, final ByteOrder order) {
            return BinaryUtil.keepIntRight(BinaryUtil.keepIntLeft(Byte.toUnsignedInt(in), bitCursor + size), size);
        }
    },

    /**
     * <div class="fr">Le curseur compte 0 sur le bit de poids le plus fort.</div>
     */
    FROM_HIGH {
        @Override
        public int join(final int[] toJoin, final int length, final int bitCursor, final int left,
                final ByteOrder order) {
            int result = 0;

            if (ByteOrder.LITTLE_ENDIAN.equals(order)) {
                for (int i = 0; i < length - 1; i++) {
                    result |= (toJoin[i] << (length - 1 - 1 - i) * Byte.SIZE);
                }
                result <<= bitCursor == 0 ? Byte.SIZE : bitCursor;
                return result | toJoin[length - 1];
            } else {
                return FROM_LOW.join(toJoin, length, bitCursor, left, ByteOrder.LITTLE_ENDIAN);
            }
        }

        /**
         * <pre>
         * 00000000|00000000|00000000|ABCDEFGH
         *                            =====^cursor
         * FGH00000|00000000|00000000|00000000 (( Integer.SIZE - left (left = Byte.SIZE - bitCursor)
         * 00000000|00000000|00000000|000000FG )) Integer.SIZE - size
         * </pre>
         */
        @Override
        public int complete(final byte in, final int bitCursor, final int left, final int size, final ByteOrder order) {
            return BinaryUtil.keepIntRight(BinaryUtil.keepIntLeft(Byte.toUnsignedInt(in), left), size);
        }

        /**
         * <pre>
         * 00000000|00000000|00000000|ABCDEFGH
         *                            =^cursor
         * BCDEFGH0|00000000|00000000|00000000 (( Integer.SIZE - Byte.SIZE + bitCursor
         * 00000000|00000000|00000000|000000BC )) Integer.SIZE - size
         * </pre>
         */
        @Override
        public int extract(final byte in, final int bitCursor, final int size, final ByteOrder order) {
            return complete(in, bitCursor, Byte.SIZE - bitCursor, size, order);
        }
    };

    /**
     *
     * @param toJoin <span class="fr">les bytes à joindre</span>
     * @param length <span class="fr">le nombre de bytes à joindre (inférieur à la longueur du tableau)</span>
     * @param bitCursor
     * @param left
     * @param order
     * @return
     */
    public abstract int join(int[] toJoin, int length, int bitCursor, int left, ByteOrder order);

    /**
     * <div class="fr">Extraction de bits d'un byte. Les bits sont extraits et repositionnés. Les bits au-delà du nombre
     * de bits à extraire sont mis à 0.</div>
     *
     * @param in <span class="fr">byte dont les bits sont extraits</span>
     * @param bitCursor <span class="fr">position à partir de laquelle les bits sont extraits</span>
     * @param left <span class="fr">nombre de bits disponibles restant (typiquement le nombre de bits dans le byte
     * diminué de la position à partir de laquelle les bits sont extraits)</span>
     * @param size <span class="fr">nombre de bits à extraire</span>
     * @param order
     * @return
     */
    public abstract int complete(byte in, int bitCursor, int left, int size, ByteOrder order);

    /**
     * <div class="fr">Extraction de bits d'un byte. Les bits sont extraits et repositionnés.</div>
     *
     * @param in <span class="fr">byte dont les bits sont extraits</span>
     * @param bitCursor <span class="fr">position à partir de laquelle les bits sont extraits</span>
     * @param size <span class="fr">nombre de bits à extraire</span>
     * @param order
     * @return
     */
    public abstract int extract(byte in, int bitCursor, int size, ByteOrder order);

    /**
     * <div class="fr"><p>Rédécoupage de l'information d'un tableau de bytes en unités de moins de 8 bits.</p>
     * <p>
     * Cette méthode produit un nouveau tableau de bytes dont chacun est composé d'un nombre de bits significatifs
     * inférieur à 8, extraits du tableau de bytes d'origine. Lorsque l'ordre est big endian, on lit d'abord les bits de
     * poids faible et on lit ensuite les bits de poids fort. Les bits complémentaires du byte résultat sont des 0 de
     * poids fort. Inversement, lorsque l'ordre est little endian, on lit d'abord les bits de poids fort et on lit
     * ensuite les bits de poids faible. Les bits complémentaires du byte résultat sont des 0 de poids faible.</p>
     * </div>
     *
     * @param in <span class="fr">tableau de bytes à redécouper</span>
     * @param newSize <span class="fr">taille en bits de l'information à extraire et à répartir dans les nouveaux bytes
     * </span>
     * @param bOrder
     * @return
     */
    public byte[] expand(final byte[] in, final int newSize, final ByteOrder bOrder) {

        if (newSize % Byte.SIZE == 0) {
            return Arrays.copyOf(in, in.length);
        }

        final int[] newSizes = new int[in.length * Byte.SIZE / newSize
                + (((in.length * Byte.SIZE) % newSize == 0) ? 0 : 1)];

        Arrays.fill(newSizes, newSize);

        return expand(in, 0, in.length, newSizes, bOrder);
    }

    /**
     * <div class="fr"><p>Rédécoupage de l'information d'un tableau de bytes en unités de moins de 8 bits.</p>
     * <p>
     * Cette méthode produit un nouveau tableau de bytes dont chacun est composé d'un nombre de bits significatifs
     * inférieur à 8, extraits du tableau de bytes d'origine. Lorsque l'ordre est big endian, on lit d'abord les bits de
     * poids faible et on lit ensuite les bits de poids fort. Les bits complémentaires du byte résultat sont des 0 de
     * poids fort. Inversement, lorsque l'ordre est little endian, on lit d'abord les bits de poids fort et on lit
     * ensuite les bits de poids faible. Les bits complémentaires du byte résultat sont des 0 de poids faible.</p>
     * </div>
     *
     * @param in <span class="fr">tableau de bytes à redécouper</span>
     * @param from
     * @param to
     * @param newSizes <span class="fr">tailles en bits de l'information à extraire et à répartir dans les nouveaux
     * bytes</span>
     * @param bOrder
     * @return
     */
    public byte[] expand(final byte[] in, final int from, final int to, final int[] newSizes, final ByteOrder bOrder) {
        final ByteArrayConsumer consumer = new ByteArrayConsumer();

        // tailles d'un byte maximum
        final int[] sizes = BinaryUtil.oneByteMaxSizes(newSizes);

        consumer.size(sizes.length);

        final ExpandCore core = new ExpandCore(in, from, to, this, bOrder);
        for (int o = 0, len = sizes.length; o < len; o++) {
            consumer.accept(core.get(sizes[o]));
        }
        return consumer.get();
    }
}
