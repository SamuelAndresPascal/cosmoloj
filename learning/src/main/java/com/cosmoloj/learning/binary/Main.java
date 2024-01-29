package com.cosmoloj.learning.binary;

import java.nio.ByteBuffer;

/**
 *
 * @author Samuel Andrés
 */
public final class Main {

    private Main() {
    }

    public static void main(final String[] args) {
        final int byteArrayToInt = ByteBuffer.wrap(
                new byte[]{(byte) 0xff, (byte) 0x0f, (byte) 0xf0, (byte) 0x00}).getInt();
        System.out.println(Integer.toHexString(byteArrayToInt));
        System.out.println(Integer.toHexString(byteArrayToInt & 0xff));
        System.out.println(Integer.toHexString((byteArrayToInt >> 24) & 0x000000ff));
        System.out.println(Integer.toHexString((byteArrayToInt >> 16) & 0x000000ff));
        System.out.println(Integer.toHexString((byteArrayToInt >> 8) & 0x000000ff));
        System.out.println(Integer.toHexString(byteArrayToInt & 0x000000ff));
    }
}
