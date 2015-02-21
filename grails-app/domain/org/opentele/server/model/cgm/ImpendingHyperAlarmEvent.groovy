package org.opentele.server.model.cgm

class ImpendingHyperAlarmEvent extends ContinuousBloodSugarEvent {

    Double glucoseValueInmmolPerl
    String impendingNess

    static constraints = {
        glucoseValueInmmolPerl(nullable: true)
        impendingNess(nullable: true)
    }
}
