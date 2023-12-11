package com.cosmoloj.format.tga;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author Samuel Andrés
 */
public class ExtensionAreaReader implements Closeable {

    private final SeekableByteChannel channel;
    private final ImageSpecification imageSpecification;

    public ExtensionAreaReader(final SeekableByteChannel channel, final ImageSpecification imageSpecification) {
        this.channel = channel;
        this.imageSpecification = imageSpecification;
    }

    public ExtensionArea read() throws IOException {

        final ByteBuffer buffer = ByteBuffer.allocate(Short.BYTES + ExtensionArea.AUTHOR_NAME_LENGTH
        + ExtensionArea.AUTHOR_COMMENTS_LENGTH + Short.BYTES * 6 + ExtensionArea.JOB_NAME_LENGTH + Short.BYTES * 3
        + ExtensionArea.SOFTWARE_ID_LENGTH + Byte.BYTES * 3 + Integer.BYTES + Short.BYTES * 2 + Short.BYTES * 2
        + Integer.BYTES + Integer.BYTES + Integer.BYTES + Byte.BYTES);

        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.clear();
        channel.read(buffer);
        buffer.flip();

        final short extensionSize = buffer.getShort();

        final byte[] authorNameBytes = new byte[ExtensionArea.AUTHOR_NAME_LENGTH];
        buffer.get(authorNameBytes);
        final String authorName = new String(authorNameBytes, StandardCharsets.US_ASCII);

        final byte[] authorCommentsBytes = new byte[ExtensionArea.AUTHOR_COMMENTS_LENGTH];
        buffer.get(authorCommentsBytes);
        final String authorComments = new String(authorCommentsBytes, StandardCharsets.US_ASCII);

        final short[] dateTimeStamp = new short[6];
        for (int i = 0; i < dateTimeStamp.length; i++) {
            dateTimeStamp[i] = buffer.getShort();
        }

        final byte[] jobNameBytes = new byte[ExtensionArea.JOB_NAME_LENGTH];
        buffer.get(jobNameBytes);
        final String jobName = new String(jobNameBytes, StandardCharsets.US_ASCII);

        final short[] jobTime = new short[3];
        for (int i = 0; i < jobTime.length; i++) {
            jobTime[i] = buffer.getShort();
        }

        final byte[] softwareIdBytes = new byte[ExtensionArea.SOFTWARE_ID_LENGTH];
        buffer.get(softwareIdBytes);
        final String softwareId = new String(softwareIdBytes, StandardCharsets.US_ASCII);

        final byte[] softwareVersion = new byte[3];
        buffer.get(softwareVersion);

        final int keyColor = buffer.getInt();

        final short[] pixelAspectRatio = new short[2];
        for (int i = 0; i < pixelAspectRatio.length; i++) {
            pixelAspectRatio[i] = buffer.getShort();
        }

        final short[] gammaValue = new short[2];
        for (int i = 0; i < gammaValue.length; i++) {
            gammaValue[i] = buffer.getShort();
        }

        final int colorCorrectionOffset = buffer.getInt();

        final int postageStampOffset = buffer.getInt();

        final int scanLineOffset = buffer.getInt();

        final byte attributesType = buffer.get();

        final int[] scanLineTable = scanLineTable(scanLineOffset);

        final byte[] postageStampImage = postageStampImage(postageStampOffset);

        final short[][] colorCorrectionTable = colorCorrectionTable(colorCorrectionOffset);

        return new ExtensionArea(extensionSize, authorName, authorComments, dateTimeStamp, jobName, jobTime,
                softwareId, softwareVersion, keyColor, pixelAspectRatio, gammaValue, colorCorrectionOffset,
                postageStampOffset, scanLineOffset, attributesType, scanLineTable, postageStampImage,
                colorCorrectionTable);
    }

    private int[] scanLineTable(final int scanLineOffset) throws IOException {
        if (scanLineOffset != 0) {
            channel.position((long) scanLineOffset);

            final ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES * imageSpecification.imageHeight());
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            buffer.clear();
            channel.read(buffer);
            buffer.flip();

            final int[] scanLineTable = new int[imageSpecification.imageHeight()];
            for (int i = 0; i < scanLineTable.length; i++) {
                scanLineTable[i] = buffer.getInt();
            }
            return scanLineTable;
        }
        return null;
    }

    private byte[] postageStampImage(final int postageStampOffset) throws IOException {
        if (postageStampOffset != 0) {

            channel.position((long) postageStampOffset);

            ByteBuffer buffer = ByteBuffer.allocate(Byte.BYTES * 2);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            buffer.clear();
            channel.read(buffer);
            buffer.flip();

            final byte postageStampWidth = buffer.get();
            final byte postageStampHeight = buffer.get();

            buffer = ByteBuffer.allocate(postageStampWidth * postageStampHeight);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            buffer.clear();
            channel.read(buffer);
            buffer.flip();

            final byte[] postageStampImage = new byte[postageStampWidth * postageStampHeight + 2];
            postageStampImage[0] = postageStampWidth;
            postageStampImage[1] = postageStampHeight;
            buffer.get(postageStampImage, 2, postageStampImage.length - 2);
            return postageStampImage;
        }
        return null;
    }

    private short[][] colorCorrectionTable(final int colorCorrectionOffset) throws IOException {
        if (colorCorrectionOffset != 0) {
            channel.position((long) colorCorrectionOffset);

            ByteBuffer buffer = ByteBuffer.allocate(256 * 4 * Short.BYTES);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            buffer.clear();
            channel.read(buffer);
            buffer.flip();

            final short[][] colorCorrectionTable = new short[256][4];
            for (final short[] colorCorrectionTable1 : colorCorrectionTable) {
                for (int j = 0; j < colorCorrectionTable1.length; j++) {
                    colorCorrectionTable1[j] = buffer.getShort();
                }
            }
            return colorCorrectionTable;
        }
        return null;
    }

    @Override
    public void close() throws IOException {
        this.channel.close();
    }
}
