package org.opentele.server.model.cgm

class ContinuousBloodSugarMeasurement extends ContinuousBloodSugarEvent {

    double glucoseValueInmmolPerl
    static constraints = {
        glucoseValueInmmolPerl(nullable: true)
    }
}
