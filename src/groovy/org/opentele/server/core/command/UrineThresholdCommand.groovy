package org.opentele.server.core.command

import grails.validation.Validateable
import org.opentele.server.core.model.types.ProteinValue


@Validateable(nullable = true)
class UrineThresholdCommand extends ThresholdCommand {

    ProteinValue alertHigh
   	ProteinValue warningHigh
   	ProteinValue warningLow
   	ProteinValue alertLow

    List<String> getBindableProperties() {
        ["alertHigh","warningHigh","warningLow","alertLow"]
    }

    static constraints = {
        alertHigh nullable: true, validator: { value, object ->
            value == null || value > [object.warningHigh, object.warningLow, object.alertLow].max()
        }
        warningHigh nullable: true, validator: { value, object ->
            value == null || value > [object.warningLow, object.alertLow].max()
        }
        warningLow nullable: true, validator: { value, object ->
            value == null || value > object.alertLow
        }
        alertLow nullable: true
    }

}
