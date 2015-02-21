package org.opentele.server.model

import org.opentele.server.core.model.AbstractObject

class Consultation extends AbstractObject {

    static hasMany = [
            measurements: Measurement
    ]

    Clinician clinician
    Patient patient
    Date consultationDate

    static constraints = {
        clinician(nullable:false)
        patient(nullable:false)
        consultationDate(nullable:false)
    }
}