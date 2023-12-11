package com.cosmoloj.util.java2d.image;

/**
 *
 * @author Samuel Andrés
 */
public abstract class DefaultBufferedImageParameters implements BufferedImageParameters {

    private final int dataBufferType;

    protected DefaultBufferedImageParameters(final int dataBufferType) {
        this.dataBufferType = dataBufferType;
    }

    @Override
    public int dataBufferType() {
        return dataBufferType;
    }
}
