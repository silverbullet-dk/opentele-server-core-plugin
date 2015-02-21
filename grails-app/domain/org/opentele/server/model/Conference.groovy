package org.opentele.server.model

import org.opentele.server.core.model.AbstractObject

class Conference extends AbstractObject {
    static hasMany = [
        measurementDrafts: ConferenceMeasurementDraft,
        measurements: Measurement
    ]

    Clinician clinician
    Patient patient
    boolean completed

    static constraints = {
    }
}
