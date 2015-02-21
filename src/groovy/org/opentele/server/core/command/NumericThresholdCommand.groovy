package org.opentele.server.core.command
import grails.validation.Validateable
import org.grails.databinding.BindUsing
import org.opentele.server.core.util.FloatBindingHelper

@Validateable(nullable = true)
@BindUsing(FloatBindingHelper)
class NumericThresholdCommand extends ThresholdCommand {

    Float alertHigh
    Float warningHigh
    Float warningLow
    Float alertLow

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
