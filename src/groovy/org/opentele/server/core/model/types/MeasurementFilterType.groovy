package org.opentele.server.core.model.types

public enum MeasurementFilterType {
    WEEK(true),
    MONTH(true),
    QUARTER(true),
    YEAR(true),
    CUSTOM(true),
    ALL(false);

    boolean isLimited

    private MeasurementFilterType(boolean isLimited) {
        this.isLimited = isLimited
    }

    static safeValueOf(String type) {
        try {
            MeasurementFilterType.valueOf(MeasurementFilterType.class, type)
        } catch (Exception e) {
            null
        }
    }

    static valueOf(String type) {
        if (type != null) {
            MeasurementFilterType.valueOf(MeasurementFilterType.class, type)
        } else {
            ALL;
        }
    }
}