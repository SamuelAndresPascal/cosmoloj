package com.cosmoloj.time;

import java.io.Serializable;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.Chronology;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.List;

/**
 *
 * @author Samuel Andrés
 */
public final class GregorianPeriod implements ChronoPeriod, Serializable {

    @Override
    public long get(final TemporalUnit unit) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<TemporalUnit> getUnits() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Chronology getChronology() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ChronoPeriod plus(final TemporalAmount amountToAdd) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ChronoPeriod minus(final TemporalAmount amountToSubtract) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ChronoPeriod multipliedBy(final int scalar) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ChronoPeriod normalized() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Temporal addTo(final Temporal temporal) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Temporal subtractFrom(final Temporal temporal) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
