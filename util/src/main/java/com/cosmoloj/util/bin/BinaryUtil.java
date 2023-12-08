package com.cosmoloj.util.bin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Utilitaires de manipulation de données binaires.
 *
 * @author Samuel Andrés
 */
public final class BinaryUtil {

    private BinaryUtil() {
    }

    /**
     * <span class="fr">Convertit en entier double précision en booléen.</span>
     * @param i <span class="fr">entier double précision</span>
     * @return <span class="fr">vrai si l'entier double précision n'est pas null</span>
     */
    public static boolean isTrue(final long i) {
        return i != 0;
    }

    /**
     * <span class="fr">Convertit en entier double précision en booléen.</span>
     * @param i <span class="fr">entier double précision</span>
     * @return <span class="fr">vrai si l'entier double précision est null</span>
     */
    public static boolean isFalse(final long i) {
        return i == 0;
    }

    /**
     * <span class="fr">Convertit en entier en booléen.</span>
     * @param i <span class="fr">entier</span>
     * @return <span class="fr">vrai si l'entier n'est pas null</span>
     */
    public static boolean isTrue(final int i) {
        return i != 0;
    }

    /**
     * <span class="fr">Convertit en entier en booléen.</span>
     * @param i <span class="fr">entier</span>
     * @return <span class="fr">vrai si l'entier est null</span>
     */
    public static boolean isFalse(final int i) {
        return i == 0;
    }

    public static int keepIntRight(final int value, final int size) {
        return value >>> (Integer.SIZE - size);
    }

    public static int keepIntLeft(final int value, final int size) {
        return value << (Integer.SIZE - size);
    }

    /**
     * <span class="fr">Indique le nombre d'unités nécessaires de la taille indiquée pour stocker la quantité passée en
     * paramètre.</span>
     * @param amount <span>la quantité</span>
     * @param unit <span>la taille de l'unité</span>
     * @return <span>le nombre d'unités nécessaires pour stocker la quantité</span>
     */
    public static int sizedUnitsForAmount(final int amount, final int unit) {
        return amount == 0 ? 0 : ((amount - 1) / unit) + 1;
    }

    /**
     * <div class="fr">Construit l'information du nombre de bytes nécessaire pour encoder un nombre de bits donné.</div>
     * @param bitNb <span class="fr">nombre de bits</span>
     * @return <span class="fr">nombre de bytes nécessaire pour contenir le nombre de bits indiqué en paramètre</span>
     */
    public static int bytesFor(final int bitNb) {
        return sizedUnitsForAmount(bitNb, Byte.SIZE);
    }

    public static int bytesFor(final int bitNb, final int offset) {
        return bytesFor(bitNb + offset);
    }

    /**
     * <span class="fr">Redimensionne le buffer en un buffer de taille supérieure.</span>
     *
     * @param b1 <span class="fr">buffer à redimensionner</span>
     * @param allocationUnit <span class="fr">unité de redimensinnement en bits</span>
     * @return <span class="fr">un nouveau buffer</span>
     */
    public static ByteBuffer adjustTo(final ByteBuffer b1, final int allocationUnit) {
        return adjustTo(b1, 1, allocationUnit);
    }

    /**
     * <span class="fr">Redimensionne le buffer en un buffer de taille supérieure.</span>
     *
     * @param b1 <span class="fr">buffer à redimensionner</span>
     * @param allocationNb <span class="fr">nombre d'unités requises</span>
     * @param allocationUnit <span class="fr">unité de redimensinnement en bits</span>
     * @return <span class="fr">un nouveau buffer</span>
     */
    public static ByteBuffer adjustTo(final ByteBuffer b1, final int allocationNb, final int allocationUnit) {

        final byte[] array1 = new byte[b1.limit()];
        b1.get(array1);

        final byte[] array2 = new byte[allocationUnit * allocationNb];
        for (int i = 0; i < allocationNb; i++) {
            System.arraycopy(array1,
                    i * array1.length / allocationNb,
                    array2, i * allocationUnit,
                    array1.length / allocationNb);
        }

        // On peut peut-être réutiliser et renvoyer b1 ?
        final ByteBuffer b2 = ByteBuffer.allocateDirect(allocationUnit * allocationNb);
        b2.order(b1.order());
        b2.clear();
        b2.put(array2);
        b2.flip();

        return b2;
    }

    public static int[] oneByteMaxSizes(final int[] multiByteSizes) {

        final int[] sizes = new int[oneByteMaxLength(multiByteSizes)];
        int j = 0;
        boolean first = true;
        int incpj = 0;
        for (int i = 0, len = sizes.length; i < len; i++) {
            if (first) {
                incpj = multiByteSizes[j];
            }
            if (incpj > Byte.SIZE) {
                first = false;
                sizes[i] = Byte.SIZE;
                incpj -= Byte.SIZE;
            } else {
                first = true;
                sizes[i] = incpj;
                j++;
            }
        }
        return sizes;
    }

    /**
     * <div class="fr"></div>
     *
     * @param multiByteSizes <span class="fr">tableau indiquant une série de tailles en bits</span>
     * @return <span class="fr">nombre de bytes nécessaires pour représenter l'information correspondant à chaque taille
     * de l'élément dans des bytes séparés</span>
     */
    public static int oneByteMaxLength(final int[] multiByteSizes) {

        int length = 0;
        for (int i = 0, len = multiByteSizes.length; i < len; i++) {
            length += bytesFor(multiByteSizes[i]);
        }
        return length;
    }

    /**
     * <div class="fr"><p>Rédécoupage de l'information d'un tableau de bytes dont on considère que la valeur
     * significative sur chacun d'entre eux s'étend sur moins de 8 bits.</p>
     * <p>
     * Cette méthode produit un nouveau tableau de bytes dont les bits sont constitués des bits significatifs du tableau
     * d'entrée. Les bits significatifs du tableau d'entrée sont supposés être les bits de poids faible et les bits dans
     * le tableau de sortie sont replis, byte par byte, en commençant par les bits de poids fort.
     * </p>
     * </div>
     *
     * @param in <span class="fr">tableau de bytes à redécouper</span>
     * @param oldSize <span class="fr">taille en bits de l'information significative à extraire des bytes du tableau d'
     * entrée et à accumuler dans le nouveau tableau</span>
     * @return
     */
    public static byte[] compand(final byte[] in, final int oldSize) {
        final int[] oldSizes = new int[in.length];
        Arrays.fill(oldSizes, oldSize);
        return compand(in, oldSizes);
    }

    /**
     * <div class="fr"><p>Rédécoupage de l'information d'un tableau de bytes dont on considère que la valeur
     * significative sur chacun d'entre eux s'étend sur moins de 8 bits.</p>
     * <p>
     * Cette méthode produit un nouveau tableau de bytes dont les bits sont constitués des bits significatifs du tableau
     * d'entrée. Les bits significatifs du tableau d'entrée sont supposés être les bits de poids faible et les bits dans
     * le tableau de sortie sont replis, byte par byte, en commençant par les bits de poids fort.
     * </p>
     * </div>
     *
     * @param in <span class="fr">tableau de bytes à redécouper</span>
     * @param oldSizes <span class="fr">tailles en bits de l'information significative à extraire des bytes du tableau
     * d' entrée et à accumuler dans le nouveau tableau</span>
     * @return
     */
    public static byte[] compand(final byte[] in, final int[] oldSizes) {

        int oldSize = 0;
        for (final int i : oldSizes) {
            oldSize += i;
        }

        final byte[] out = new byte[oldSize / Byte.SIZE + ((oldSize % Byte.SIZE == 0) ? 0 : 1)];

        // curseur de bits global dans de tableau de sortie
        int outBitCursor = 0;
        int o = 0;
        for (int i = 0, len = in.length; i < len; i++) {

            oldSize = oldSizes[i];
            final int left = Byte.SIZE - outBitCursor;

            if (left >= oldSize) {
                out[o] |= (in[i] << outBitCursor);

                if (left == oldSize) {
                    outBitCursor = 0;
                    o++;
                } else {
                    outBitCursor += oldSize;
                }
            } else {
                out[o] |= (byte) ((in[i] << (Integer.SIZE - left)) >>> (Integer.SIZE - left - outBitCursor));
                outBitCursor = oldSize - left;
                out[++o] = (byte) (in[i] >>> left);
            }
        }

        return out;
    }

    public static String getString(final ByteBuffer buffer, final int byteNb) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteNb; i++) {
            sb.append((char) buffer.get());
        }
        return sb.toString();
    }

    public static ByteBuffer allocate(final int capacity, final ByteOrder order) {
        return ByteBuffer.allocate(capacity).order(order).clear();
    }

    public static ByteBuffer read(final ReadableByteChannel channel, final int capacity, final ByteOrder order)
            throws IOException {
        final ByteBuffer buffer = allocate(capacity, order);
        channel.read(buffer);
        return buffer.flip();
    }

    /**
     * <div class="fr">Donne une représentation binaire de l'entier donné en paramètre sur un nombre de caractères
     * spécifié.</div>
     *
     * @param input
     * @param length
     * @return
     * @see Integer#toBinaryString(int)
     */
    public static String toBinaryString(final int input, final int length) {

        final String standard = Integer.toBinaryString(input);
        final StringBuilder sb = new StringBuilder();

        if (input < 0) {
            // dans ce cas il faut supprimer les "1" du complément à deux insérés dans le format "int"
            return "1" + standard.substring(standard.length() - length + 1, standard.length());
        } else {
            for (int i = 0, len = length - standard.length(); i < len; i++) {
                sb.append(0);
            }
        }
        return sb.append(standard).toString();
    }

    /**
     * <div class="fr">Donne une représentation binaire du byte donné en paramètre sur 8 caractères.</div>
     *
     * @param input
     * @return
     * @see #toBinaryString(int, int)
     */
    public static String toBinaryString(final byte input) {
        return toBinaryString(input, Byte.SIZE);
    }

    public static String toBinaryString(final int input) {
        return toBinaryString(input, Integer.SIZE);
    }

    public static String toBinaryString(final byte[] ba, final int separator) {
        final StringBuilder sb = new StringBuilder();
        for (final byte b : ba) {
            sb.append(toBinaryString(b));
            if (separator >= 0) {
                sb.appendCodePoint(separator);
            }
        }
        if (separator >= 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public static String toBinaryString(final int[] ia, final int separator) {
        final StringBuilder sb = new StringBuilder();
        for (final int b : ia) {
            sb.append(toBinaryString(b));
            if (separator >= 0) {
                sb.appendCodePoint(separator);
            }
        }
        if (separator >= 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public static String toBinaryString(final int[] bytes, final int max, final int separator) {
        final StringBuilder sb = new StringBuilder();
        for (final int b : bytes) {
            sb.append(toBinaryString(b, max));
            if (separator >= 0) {
                sb.appendCodePoint(separator);
            }
        }
        if (separator >= 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public static byte[] toByteArray(final int[] stream) {
        final byte[] bin = new byte[stream.length];
        for (int i = 0, len = bin.length; i < len; i++) {
            bin[i] = (byte) stream[i];
        }
        return bin;
    }

    public static int[] parseIntArray(final BufferedReader reader, final int radix) throws IOException {

        final List<String> content = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            content.add(line);
        }
        return parseIntArray(content.stream(), radix);
    }

    public static int[] parseUnsignedIntArray2(final String... input) {
        return parseUnsignedIntArray(Stream.of(input), 2);
    }

    public static int[] parseIntArray10(final String... input) {
        return parseIntArray(Stream.of(input), 10);
    }

    public static int[] parseUnsignedIntArray(final Stream<String> input, final int radix) {
        return input.mapToInt(i -> Integer.parseUnsignedInt(i, radix)).toArray();
    }

    public static int[] parseIntArray(final Stream<String> input, final int radix) {
        return input.mapToInt(i -> Integer.parseInt(i, radix)).toArray();
    }

    public static byte[] parseByteArray(final BufferedReader reader, final int radix) throws IOException {
        return toByteArray(parseIntArray(reader, radix));
    }

    public static byte[] parseByteArray2(final String... input) {
        return parseByteArray(Stream.of(input), 2);
    }

    public static byte[] parseByteArray10(final String... input) {
        return parseByteArray(Stream.of(input), 10);
    }

    public static byte[] parseByteArray(final Stream<String> input, final int radix) {
        return toByteArray(parseIntArray(input, radix));
    }

    public static void printImageBytes(final byte[][] image, final PrintStream printer) {
        for (final byte[] strip : image) {
            printStripBytes(strip, printer);
        }
    }

    public static void printStripBytes(final byte[] strip, final PrintStream printer) {
        for (final byte b : strip) {
            printer.println(b);
        }
    }

    public static byte[][] extractStrips(final byte[][] image, final int... stripIndices) {

        if (stripIndices.length == 0) {
            return image;
        }

        final byte[][] outStrips = new byte[stripIndices.length][];

        int destIndex = 0;
        for (final int srcIndex : stripIndices) {
            final int len = image[srcIndex].length;
            outStrips[destIndex] = new byte[len];
            System.arraycopy(image[srcIndex], 0, outStrips[destIndex++], 0, len);
        }

        return outStrips;
    }

    public String toString(final byte b) {
        return CHARS[Byte.toUnsignedInt(b)];
    }

    private static final String[] CHARS = new String[Byte.MAX_VALUE - Byte.MIN_VALUE + 1];

    static {

        // les bytes signés négatifs
        CHARS[128] = "10000000";
        CHARS[129] = "10000001";
        CHARS[130] = "10000010";
        CHARS[131] = "10000011";
        CHARS[132] = "10000100";
        CHARS[133] = "10000101";
        CHARS[134] = "10000110";
        CHARS[135] = "10000111";
        CHARS[136] = "10001000";
        CHARS[137] = "10001001";
        CHARS[138] = "10001010";
        CHARS[139] = "10001011";
        CHARS[140] = "10001100";
        CHARS[141] = "10001101";
        CHARS[142] = "10001110";
        CHARS[143] = "10001111";
        CHARS[144] = "10010000";
        CHARS[145] = "10010001";
        CHARS[146] = "10010010";
        CHARS[147] = "10010011";
        CHARS[148] = "10010100";
        CHARS[149] = "10010101";
        CHARS[150] = "10010110";
        CHARS[151] = "10010111";
        CHARS[152] = "10011000";
        CHARS[153] = "10011001";
        CHARS[154] = "10011010";
        CHARS[155] = "10011011";
        CHARS[156] = "10011100";
        CHARS[157] = "10011101";
        CHARS[158] = "10011110";
        CHARS[159] = "10011111";
        CHARS[160] = "10100000";
        CHARS[161] = "10100001";
        CHARS[162] = "10100010";
        CHARS[163] = "10100011";
        CHARS[164] = "10100100";
        CHARS[165] = "10100101";
        CHARS[166] = "10100110";
        CHARS[167] = "10100111";
        CHARS[168] = "10101000";
        CHARS[169] = "10101001";
        CHARS[170] = "10101010";
        CHARS[171] = "10101011";
        CHARS[172] = "10101100";
        CHARS[173] = "10101101";
        CHARS[174] = "10101110";
        CHARS[175] = "10101111";
        CHARS[176] = "10110000";
        CHARS[177] = "10110001";
        CHARS[178] = "10110010";
        CHARS[179] = "10110011";
        CHARS[180] = "10110100";
        CHARS[181] = "10110101";
        CHARS[182] = "10110110";
        CHARS[183] = "10110111";
        CHARS[184] = "10111000";
        CHARS[185] = "10111001";
        CHARS[186] = "10111010";
        CHARS[187] = "10111011";
        CHARS[188] = "10111100";
        CHARS[189] = "10111101";
        CHARS[190] = "10111110";
        CHARS[191] = "10111111";
        CHARS[192] = "11000000";
        CHARS[193] = "11000001";
        CHARS[194] = "11000010";
        CHARS[195] = "11000011";
        CHARS[196] = "11000100";
        CHARS[197] = "11000101";
        CHARS[198] = "11000110";
        CHARS[199] = "11000111";
        CHARS[200] = "11001000";
        CHARS[201] = "11001001";
        CHARS[202] = "11001010";
        CHARS[203] = "11001011";
        CHARS[204] = "11001100";
        CHARS[205] = "11001101";
        CHARS[206] = "11001110";
        CHARS[207] = "11001111";
        CHARS[208] = "11010000";
        CHARS[209] = "11010001";
        CHARS[210] = "11010010";
        CHARS[211] = "11010011";
        CHARS[212] = "11010100";
        CHARS[213] = "11010101";
        CHARS[214] = "11010110";
        CHARS[215] = "11010111";
        CHARS[216] = "11011000";
        CHARS[217] = "11011001";
        CHARS[218] = "11011010";
        CHARS[219] = "11011011";
        CHARS[220] = "11011100";
        CHARS[221] = "11011101";
        CHARS[222] = "11011110";
        CHARS[223] = "11011111";
        CHARS[224] = "11100000";
        CHARS[225] = "11100001";
        CHARS[226] = "11100010";
        CHARS[227] = "11100011";
        CHARS[228] = "11100100";
        CHARS[229] = "11100101";
        CHARS[230] = "11100110";
        CHARS[231] = "11100111";
        CHARS[232] = "11101000";
        CHARS[233] = "11101001";
        CHARS[234] = "11101010";
        CHARS[235] = "11101011";
        CHARS[236] = "11101100";
        CHARS[237] = "11101101";
        CHARS[238] = "11101110";
        CHARS[239] = "11101111";
        CHARS[240] = "11110000";
        CHARS[241] = "11110001";
        CHARS[242] = "11110010";
        CHARS[243] = "11110011";
        CHARS[244] = "11110100";
        CHARS[245] = "11110101";
        CHARS[246] = "11110110";
        CHARS[247] = "11110111";
        CHARS[248] = "11111000";
        CHARS[249] = "11111001";
        CHARS[250] = "11111010";
        CHARS[251] = "11111011";
        CHARS[252] = "11111100";
        CHARS[253] = "11111101";
        CHARS[254] = "11111110";
        CHARS[255] = "11111111";


        CHARS[0] = "00000000";
        CHARS[1] = "00000001";
        CHARS[2] = "00000010";
        CHARS[3] = "00000011";
        CHARS[4] = "00000100";
        CHARS[5] = "00000101";
        CHARS[6] = "00000110";
        CHARS[7] = "00000111";
        CHARS[8] = "00001000";
        CHARS[9] = "00001001";
        CHARS[10] = "00001010";
        CHARS[11] = "00001011";
        CHARS[12] = "00001100";
        CHARS[13] = "00001101";
        CHARS[14] = "00001110";
        CHARS[15] = "00001111";
        CHARS[16] = "00010000";
        CHARS[17] = "00010001";
        CHARS[18] = "00010010";
        CHARS[19] = "00010011";
        CHARS[20] = "00010100";
        CHARS[21] = "00010101";
        CHARS[22] = "00010110";
        CHARS[23] = "00010111";
        CHARS[24] = "00011000";
        CHARS[25] = "00011001";
        CHARS[26] = "00011010";
        CHARS[27] = "00011011";
        CHARS[28] = "00011100";
        CHARS[29] = "00011101";
        CHARS[30] = "00011110";
        CHARS[31] = "00011111";
        CHARS[32] = "00100000";
        CHARS[33] = "00100001";
        CHARS[34] = "00100010";
        CHARS[35] = "00100011";
        CHARS[36] = "00100100";
        CHARS[37] = "00100101";
        CHARS[38] = "00100110";
        CHARS[39] = "00100111";
        CHARS[40] = "00101000";
        CHARS[41] = "00101001";
        CHARS[42] = "00101010";
        CHARS[43] = "00101011";
        CHARS[44] = "00101100";
        CHARS[45] = "00101101";
        CHARS[46] = "00101110";
        CHARS[47] = "00101111";
        CHARS[48] = "00110000";
        CHARS[49] = "00110001";
        CHARS[50] = "00110010";
        CHARS[51] = "00110011";
        CHARS[52] = "00110100";
        CHARS[53] = "00110101";
        CHARS[54] = "00110110";
        CHARS[55] = "00110111";
        CHARS[56] = "00111000";
        CHARS[57] = "00111001";
        CHARS[58] = "00111010";
        CHARS[59] = "00111011";
        CHARS[60] = "00111100";
        CHARS[61] = "00111101";
        CHARS[62] = "00111110";
        CHARS[63] = "00111111";
        CHARS[64] = "01000000";
        CHARS[65] = "01000001";
        CHARS[66] = "01000010";
        CHARS[67] = "01000011";
        CHARS[68] = "01000100";
        CHARS[69] = "01000101";
        CHARS[70] = "01000110";
        CHARS[71] = "01000111";
        CHARS[72] = "01001000";
        CHARS[73] = "01001001";
        CHARS[74] = "01001010";
        CHARS[75] = "01001011";
        CHARS[76] = "01001100";
        CHARS[77] = "01001101";
        CHARS[78] = "01001110";
        CHARS[79] = "01001111";
        CHARS[80] = "01010000";
        CHARS[81] = "01010001";
        CHARS[82] = "01010010";
        CHARS[83] = "01010011";
        CHARS[84] = "01010100";
        CHARS[85] = "01010101";
        CHARS[86] = "01010110";
        CHARS[87] = "01010111";
        CHARS[88] = "01011000";
        CHARS[89] = "01011001";
        CHARS[90] = "01011010";
        CHARS[91] = "01011011";
        CHARS[92] = "01011100";
        CHARS[93] = "01011101";
        CHARS[94] = "01011110";
        CHARS[95] = "01011111";
        CHARS[96] = "01100000";
        CHARS[97] = "01100001";
        CHARS[98] = "01100010";
        CHARS[99] = "01100011";
        CHARS[100] = "01100100";
        CHARS[101] = "01100101";
        CHARS[102] = "01100110";
        CHARS[103] = "01100111";
        CHARS[104] = "01101000";
        CHARS[105] = "01101001";
        CHARS[106] = "01101010";
        CHARS[107] = "01101011";
        CHARS[108] = "01101100";
        CHARS[109] = "01101101";
        CHARS[110] = "01101110";
        CHARS[111] = "01101111";
        CHARS[112] = "01110000";
        CHARS[113] = "01110001";
        CHARS[114] = "01110010";
        CHARS[115] = "01110011";
        CHARS[116] = "01110100";
        CHARS[117] = "01110101";
        CHARS[118] = "01110110";
        CHARS[119] = "01110111";
        CHARS[120] = "01111000";
        CHARS[121] = "01111001";
        CHARS[122] = "01111010";
        CHARS[123] = "01111011";
        CHARS[124] = "01111100";
        CHARS[125] = "01111101";
        CHARS[126] = "01111110";
        CHARS[127] = "01111111";
    }
}
