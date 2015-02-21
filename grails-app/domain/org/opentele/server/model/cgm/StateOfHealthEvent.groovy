package org.opentele.server.model.cgm

import org.opentele.server.model.types.cgm.HealthState

class StateOfHealthEvent extends ContinuousBloodSugarEvent{
    HealthState stateOfHealth

    static constraints = {
        stateOfHealth(nullable: true)
    }
}
