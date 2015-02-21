package org.opentele.server.model.cgm

class ImpendingHypoAlarmEvent extends ContinuousBloodSugarEvent {

    Double glucoseValueInmmolPerl
    String impendingNess

    static constraints = {
        glucoseValueInmmolPerl(nullable: true)
        impendingNess(nullable: true)
    }
}
