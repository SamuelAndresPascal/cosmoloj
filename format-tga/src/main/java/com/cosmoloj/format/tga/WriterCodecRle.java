package com.cosmoloj.format.tga;

import com.cosmoloj.util.io.CodecWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.WritableByteChannel;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 *
 * @author Samuel Andrés
 */
public class WriterCodecRle implements CodecWriter {

    private enum PacketType {
        RLE, UNCOMPRESSED;
    }

    private final WritableByteChannel channel;
    private final ByteOrder byteOrder;
    private final int pixelAllocation;
    private final UnaryOperator<byte[]> pixelToData;
    private final int imageWidth;
    private final int imageHeight;

    public WriterCodecRle(final WritableByteChannel channel, final ByteOrder byteOrder,
            final int pixelAllocation, final UnaryOperator<byte[]> pixelToData, final int imageWidth,
            final int imageHeight) {
        this.channel = channel;
        this.byteOrder = byteOrder;
        this.pixelAllocation = pixelAllocation;
        this.pixelToData = pixelToData;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    @Override
    public void write(final Function<int[], byte[]> pixelSupplier) throws IOException {

        byte[] lastDataValueInLine;
        byte count = 0;
        PacketType packetType;

        final int[] coordinate = new int[2];

        for (int i = 0; i < imageHeight; i++) {
            coordinate[0] = i;

            lastDataValueInLine = null;
            int firstUncompressedPixelInLine = 0;
            packetType = null;
            count = 0;

            for (int j = 0; j < imageWidth; j++) {
                coordinate[1] = j;

                if (count - 1 == TgaUtil.COUNT_MASK) {
                    // il faut absolument écrire car on ne peut plus compter
                    if (PacketType.RLE.equals(packetType)) {
                        writeRlePacket(count, lastDataValueInLine);
                    } else if (PacketType.UNCOMPRESSED.equals(packetType)) {

                        writeRawPacket(count, firstUncompressedPixelInLine, j - 1, i, pixelSupplier);
                    } else {
                        /*
                        Si on a compté de nombreux pixels, on devrait avoir déterminé s'il s'agit de pixels répétés ou
                        non.
                        */
                        throw new IllegalStateException();
                    }

                    // réinitialisation de l'état : le pixel précédent doit être oublié car il a été écrit
                    packetType = null;
                    lastDataValueInLine = null;
                    count = 0;
                }

                // si on a des bits d'attributs, il faut redécouper les bytes lus…
                final byte[] data = pixelToData.apply(pixelSupplier.apply(coordinate));

                if (lastDataValueInLine == null) {
                    // ici, il ne faut rien écrire car on ne sait pas encore si des pixels vont se répéter
                    lastDataValueInLine = data;
                    count++;
                } else {
                    if (Arrays.equals(lastDataValueInLine, data)) {
                        if (packetType == null) {
                            // on initialise le mode compressé et on incrémente le décompte
                            packetType = PacketType.RLE;
                            count++;
                        } else if (PacketType.RLE.equals(packetType)) {
                            // on est déjà en mode compressé, on se contente d'incrémenter
                            count++;
                        } else if (PacketType.UNCOMPRESSED.equals(packetType)) {
                            /*
                            On était dans une suite de pixels qui ne se répétaient pas et on tombe sur un pixel qui se
                            répète.
                            Idéalement il faudrait un seuil à partir duquel écrire en compressé…
                            Mais pour l'instant on compresse dès deux pixels répétés.
                            Le pixel présent et le précédent se répètent, il faut donc :
                            - écrire les pixels lus jusqu'au n-2 (ceux qui ne se répètent pas)
                            - passer en mode compressé RLE
                            - mettre à jour le décompte à 2 pixels
                            */
                            writeRawPacket(count, firstUncompressedPixelInLine, j - 2, i, pixelSupplier);

                            /*
                            Réinitialisation de l'état : on sait déjà que la valeur courante répète la valeur
                            précédente.
                            */
                            packetType = PacketType.RLE;
                            lastDataValueInLine = data;
                            count = 2;
                        }
                    } else {

                        if (packetType == null) {
                            packetType = PacketType.UNCOMPRESSED;
                            lastDataValueInLine = data;
                            count++;
                            // il faut mémoriser le pixel précédent (on mémorise son index sur la ligne)
                            //(le premier pixel non compressé était donc le précédent)
                            firstUncompressedPixelInLine = j - 1;
                        } else if (PacketType.UNCOMPRESSED.equals(packetType)) {
                            lastDataValueInLine = data;
                            count++;
                            // on est déjà en mode non compressé : pas nécessaire de mémoriser le pixel
                        } else if (PacketType.RLE.equals(packetType)) {
                            /*
                            On était en mode compressé et un pixel cesse de répéter le précédent, il faut donc :
                            - écrire les pixels compressés jusqu'au n-1 (ceux qui se répètent)
                            - passer en mode indéfini (on ne sait pas si le pixel suivant va répéter le pixel courant)
                            - mettre à jour le décompte à 1 pixel
                            */
                            writeRlePacket(count, lastDataValueInLine);

                            // réinitialisation de l'état : on ne sait pas si la valeur courante va se répéter
                            packetType = null;
                            lastDataValueInLine = data;
                            count = 1;
                        }
                    }
                }
            }

            if (PacketType.RLE.equals(packetType)) {
                writeRlePacket(count, lastDataValueInLine);
            } else {
                writeRawPacket(count, firstUncompressedPixelInLine, imageWidth, i, pixelSupplier);
            }
        }
    }

    /**
     * <div class="fr">Écriture d'un paquet de pixels en mode de décompte répété.</div>
     *
     * @param count <span class="fr">nombre de pixels</span>
     * @param pixelValue <span class="fr">valeur du data</span>
     * @throws IOException
     */
    private void writeRlePacket(final byte count, final byte[] data) throws IOException {

        final ByteBuffer buffer = ByteBuffer.allocate(Byte.BYTES + pixelAllocation);
        buffer.order(byteOrder);
        buffer.clear();

        // écriture du décompte
        buffer.put((byte) (TgaUtil.RLE | (count - 1)));

        // écriture de la valeur du pixel répété
        buffer.put(data);
        buffer.flip();
        channel.write(buffer);
    }

    /**
     * <div class="fr">Écriture d'un paquet de pixels en mode brut (non répété).</div>
     *
     * @param count <span class="fr">nombre de pixels</span>
     * @param startIndex <span class="fr">index (inclusif) du premier pixel</span>
     * @param endIndex <span class="fr">index (exclusif) du dernier pixel</span>
     * @param line <span class="fr">ligne de données</span>
     * @param pixelToData <span class="fr">fournisseur de la valeur du pixel</span>
     * @throws IOException
     */
    private void writeRawPacket(final byte count, final int startIndex, final int endIndex, final int line,
            final Function<int[], byte[]> pixelSupplier) throws IOException {

        final ByteBuffer buffer = ByteBuffer.allocate(Byte.BYTES + pixelAllocation * (endIndex - startIndex));
        buffer.order(byteOrder);
        buffer.clear();

        // écriture du décompte
        buffer.put((byte) (count - 1));

        final int[] coordinate = new int[2];
        coordinate[0] = line;

        // écriture des pixels non répétés
        for (int k = startIndex; k < endIndex; k++) {
            coordinate[1] = k;
            buffer.put(pixelToData.apply(pixelSupplier.apply(coordinate)));
        }

        buffer.flip();
        channel.write(buffer);
    }
}
