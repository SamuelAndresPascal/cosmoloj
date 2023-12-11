package com.cosmoloj.format.tga;

/**
 *
 * @author Samuel Andrés
 */
public final class TgaUtil {

    private TgaUtil() {
    }

    public static final byte RLE = (byte) 0x80;
    public static final byte COUNT_MASK = (byte) 0x7f;

    /**
     * <div class="fr">Le premier bit du décompte indique si on est en RLE.</div>
     * @param repetitionCount
     * @return
     */
    public static boolean isRunLengthPacket(final byte repetitionCount) {
        return (repetitionCount & TgaUtil.RLE) == TgaUtil.RLE;
    }

    /**
     * <div class="fr">Pour calculer le véritable nombre de pixels représentés par un paquet, on prend les 7 bits les
     * plus faibles et on ajoute 1 à la valeur.</div>
     * @param repetitionCount
     * @return
     */
    public static int getCount(final byte repetitionCount) {
        return (repetitionCount & TgaUtil.COUNT_MASK) + 1;
    }
}
