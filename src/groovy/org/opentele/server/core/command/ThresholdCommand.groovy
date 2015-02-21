package org.opentele.server.core.command

import grails.validation.Validateable
import org.opentele.server.model.Threshold

@Validateable(nullable = true)
abstract class ThresholdCommand {
    Long version
    Threshold threshold

    abstract List<String> getBindableProperties()

    static constraints = {
        threshold nullable: false
        version nullable: true, validator: { value, object ->
            value == null || value == object.threshold.version
        }
    }
}
