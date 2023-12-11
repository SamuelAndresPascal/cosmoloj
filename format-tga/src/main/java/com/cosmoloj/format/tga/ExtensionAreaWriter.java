package com.cosmoloj.format.tga;

import java.io.Closeable;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Samuel Andrés
 */
public class ExtensionAreaWriter implements Closeable {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final SeekableByteChannel channel;
    private final ImageSpecification imageSpecification;

    public ExtensionAreaWriter(final SeekableByteChannel channel, final ImageSpecification imageSpecification)  {
        this.channel = channel;
        this.imageSpecification = imageSpecification;
    }

    public void write(final ExtensionArea extension) throws IOException {

        ByteBuffer buffer = ByteBuffer.allocate(Short.BYTES + ExtensionArea.AUTHOR_NAME_LENGTH
        + ExtensionArea.AUTHOR_COMMENTS_LENGTH + Short.BYTES * 6 + ExtensionArea.JOB_NAME_LENGTH + Short.BYTES * 3
        + ExtensionArea.SOFTWARE_ID_LENGTH + Byte.BYTES * 3 + Integer.BYTES + Short.BYTES * 2 + Short.BYTES * 2
        + Integer.BYTES + Integer.BYTES + Integer.BYTES + Byte.BYTES);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        buffer.putShort(extension.extensionSize());
        buffer.put(extension.authorName().getBytes(StandardCharsets.US_ASCII));
        buffer.put(extension.authorComments().getBytes(StandardCharsets.US_ASCII));

        final short[] dateTimeStamp = extension.dateTimeStamp();
        for (int i = 0; i < dateTimeStamp.length; i++) {
            buffer.putShort(dateTimeStamp[i]);
        }

        buffer.put(extension.jobName().getBytes(StandardCharsets.US_ASCII));

        final short[] jobTime = extension.jobTime();
        for (int i = 0; i < jobTime.length; i++) {
            buffer.putShort(jobTime[i]);
        }

        buffer.put(extension.softwareId().getBytes(StandardCharsets.US_ASCII));

        buffer.put(extension.softwareVersion());

        buffer.putInt(extension.keyColor());

        final short[] pixelAspectRatio = extension.pixelAspectRatio();
        for (int i = 0; i < pixelAspectRatio.length; i++) {
            buffer.putShort(pixelAspectRatio[i]);
        }

        final short[] gammaValue = extension.gammaValue();
        for (int i = 0; i < gammaValue.length; i++) {
            buffer.putShort(gammaValue[i]);
        }

        buffer.putInt(extension.colorCorrectionOffset());

        buffer.putInt(extension.postageStampOffset());

        buffer.putInt(extension.scanLineOffset());

        buffer.put(extension.attributesType());

        buffer.flip();
        channel.write(buffer);

        if (extension.scanLineOffset() != 0) {
            channel.position(extension.scanLineOffset());

            buffer = ByteBuffer.allocate(Integer.BYTES * imageSpecification.imageHeight());
            buffer.order(ByteOrder.LITTLE_ENDIAN);

            final int[] scanLineTable = extension.scanLineTable();
            for (int i = 0; i < scanLineTable.length; i++) {
                buffer.putInt(scanLineTable[i]);
            }
            buffer.flip();
            channel.write(buffer);
        }

        if (extension.postageStampOffset() != 0) {
            channel.position(extension.postageStampOffset());

            final byte[] postageStampImage = extension.postageStampImage();
            final byte postageStampWidth = postageStampImage[0];
            final byte postageStampHeight = postageStampImage[1];

            buffer = ByteBuffer.allocate(Byte.BYTES * 2 + postageStampWidth * postageStampHeight);
            buffer.order(ByteOrder.LITTLE_ENDIAN);

            buffer.put(postageStampImage);

            buffer.flip();
            channel.write(buffer);
        }


        if (extension.colorCorrectionOffset() != 0) {
            channel.position(extension.colorCorrectionOffset());

            buffer = ByteBuffer.allocate(256 * 4 * Short.BYTES);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            final short[][] colorCorrectionTable = extension.colorCorrectionTable();
            for (final short[] colorCorrectionTable1 : colorCorrectionTable) {
                for (final short colorCorrectionTable2 : colorCorrectionTable1) {
                    buffer.putShort(colorCorrectionTable2);
                }
            }
            buffer.flip();
            channel.write(buffer);
        }
    }

    @Override
    public void close() throws IOException {
        this.channel.close();
    }
}
