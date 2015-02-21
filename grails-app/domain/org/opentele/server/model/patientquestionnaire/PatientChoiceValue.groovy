package org.opentele.server.model.patientquestionnaire

import org.opentele.server.core.model.AbstractObject
import org.opentele.server.core.model.types.Severity

class PatientChoiceValue extends AbstractObject {
	
    PatientInputNode patientInputNode 
    
    String label
    String value
    int ordering
    static constraints = {
        label(nullable:false)
        value(nullable:false)
        ordering(nullable:false)
    }
}
