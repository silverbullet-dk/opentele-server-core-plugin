package org.opentele.server.model.cgm

import org.opentele.server.core.model.AbstractObject
import org.opentele.server.model.Measurement

class ContinuousBloodSugarEvent extends AbstractObject {
    Measurement measurement
    long recordNumber
    Date time

    static constraints = {
        measurement(nullable: false)
        recordNumber(nullable: false)
        time(nullable: false)
    }
}



