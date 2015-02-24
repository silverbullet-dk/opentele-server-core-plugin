package org.opentele.server.model

import org.opentele.server.core.model.AbstractObject

abstract class Threshold extends AbstractObject {
    // Let each subclass have its own table, just as if we didn't have this superclass
    static mapping = { tablePerHierarchy false }

    MeasurementType type

    String toString() {
        type?.name
    }

    public abstract Threshold duplicate()
}
