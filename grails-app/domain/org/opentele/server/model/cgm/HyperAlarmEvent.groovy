package org.opentele.server.model.cgm

class HyperAlarmEvent extends ContinuousBloodSugarEvent {
    Double glucoseValueInmmolPerl
    static constraints = {
        glucoseValueInmmolPerl(nullable: true)
    }


}
