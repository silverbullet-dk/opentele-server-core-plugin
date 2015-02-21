package org.opentele.server.model.cgm

class GenericEvent extends ContinuousBloodSugarEvent {

    String indicatedEvent

    static constraints = {
        indicatedEvent(nullable: true)
    }
}
