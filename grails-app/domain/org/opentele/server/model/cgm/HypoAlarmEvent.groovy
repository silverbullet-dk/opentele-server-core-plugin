package org.opentele.server.model.cgm

class HypoAlarmEvent extends ContinuousBloodSugarEvent {

    Double glucoseValueInmmolPerl

    static constraints = {
        glucoseValueInmmolPerl(nullable: true)
    }
}
