package org.opentele.server.model.cgm

class CoulometerReadingEvent extends ContinuousBloodSugarEvent {

    double glucoseValueInmmolPerl
    static constraints = {
        glucoseValueInmmolPerl(nullable: true)
    }


}
