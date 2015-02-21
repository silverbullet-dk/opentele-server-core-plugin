package org.opentele.server.model.cgm

import org.opentele.server.model.types.cgm.InsulinType

class InsulinEvent extends ContinuousBloodSugarEvent {

    InsulinType insulinType
    String units

    static constraints = {
        insulinType(nullable: true)
        units(nullable: true)
    }
}
