package com.cosmoloj.format.shp;

import com.cosmoloj.util.bin.BinaryUtil;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SeekableByteChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Samuel Andrés
 */
public class ShpHeaderReader {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final SeekableByteChannel channel;

    public ShpHeaderReader(final SeekableByteChannel channel) {
        this.channel = channel;
    }

    public ShpHeader read() throws IOException {

        LOG.debug("header reading");
        channel.position(0);

        ByteBuffer buffer;

        // 1- lecture du code
        //==============================================================================================================

        buffer = BinaryUtil.read(channel, Integer.BYTES * 7, ByteOrder.BIG_ENDIAN);

        if (buffer.getInt() != 9994) {
            throw new IllegalStateException("file code must be 9994");
        }


        // 2- viennent ensuite 5 entiers inutilisés (valeur 0)
        //==============================================================================================================

        for (int i = 0; i < 5; i++) {
            if (buffer.getInt() != 0) {
                throw new IllegalStateException("bytes from 5 to 20 must not be used");
            }
        }


        // 3- taille du fichier (en nombre de mots de 16 bits)
        //==============================================================================================================

        final int length = buffer.getInt();

        if (length * 2 != channel.size()) {
            LOG.debug("read file size {} (16-bits) does not match channel's one {} (8-bits)", length, channel.size());
        }


        // 4- version
        //==============================================================================================================

        buffer = BinaryUtil.read(channel, Integer.BYTES * 2, ByteOrder.LITTLE_ENDIAN);

        if (buffer.getInt() != 1000) {
            throw new IllegalStateException("file version must be 1000");
        }


        // 5- type de forme
        //==============================================================================================================

        final int shapeType = buffer.getInt();


        // 6- BBox
        //==============================================================================================================

        buffer = BinaryUtil.read(channel, Double.BYTES * 8, ByteOrder.LITTLE_ENDIAN);

        final double xMin = buffer.getDouble();
        final double yMin = buffer.getDouble();
        final double xMax = buffer.getDouble();
        final double yMax = buffer.getDouble();
        final double zMin = buffer.getDouble();
        final double zMax = buffer.getDouble();
        final double mMin = buffer.getDouble();
        final double mMax = buffer.getDouble();

        // Structuration de l'en-tête
        //==============================================================================================================

        return new ShpHeader(length, shapeType, xMin, yMin, xMax, yMax, zMin, zMax, mMin, mMax);
    }
}
