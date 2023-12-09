package com.cosmoloj.format.shp;

import com.cosmoloj.util.bin.BinaryUtil;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.WritableByteChannel;

/**
 *
 * @author Samuel Andrés
 */
public class ShpHeaderWriter {

    private final WritableByteChannel channel;

    public ShpHeaderWriter(final WritableByteChannel channel) {
        this.channel = channel;
    }

    /**
     * <div class="fr">Écriture de l'en-tête.</div>
     *
     * @param header
     * @throws IOException
     */
    public void write(final ShpHeader header) throws IOException {

        ByteBuffer buffer;

        // 1- code
        //==============================================================================================================

        buffer = BinaryUtil.allocate(Integer.BYTES * 7, ByteOrder.BIG_ENDIAN)
                .putInt(9994);


        // 2- viennent ensuite 5 entiers inutilisés (valeur 0)
        //==============================================================================================================

        for (int i = 0; i < 5; i++) {
            buffer.putInt(0);
        }


        // 3- taille du fichier (en nombre de mots de 16 bits)
        //==============================================================================================================

        buffer.putInt(header.length());
        buffer.flip();
        channel.write(buffer);


        // 4- version
        //==============================================================================================================

        buffer = BinaryUtil.allocate(Integer.BYTES * 2, ByteOrder.LITTLE_ENDIAN)
                .putInt(1000);


        // 5- type de forme
        //==============================================================================================================

        buffer.putInt(header.shapeType());
        buffer.flip();
        channel.write(buffer);


        // 6- BBox
        //==============================================================================================================

        buffer = BinaryUtil.allocate(Double.BYTES * 8, ByteOrder.LITTLE_ENDIAN)
                .putDouble(header.xMin())
                .putDouble(header.yMin())
                .putDouble(header.xMax())
                .putDouble(header.yMax())
                .putDouble(header.zMin())
                .putDouble(header.zMax())
                .putDouble(header.mMin())
                .putDouble(header.mMax())
                .flip();

        channel.write(buffer);
    }
}
