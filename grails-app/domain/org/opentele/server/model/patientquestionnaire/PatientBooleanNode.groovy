package org.opentele.server.model.patientquestionnaire

import org.opentele.server.core.model.patientquestionnaire.PatientQuestionnaireNodeVisitor

// transient.. Not stored as result!
class PatientBooleanNode extends PatientQuestionnaireNode {

    String variableName
    Boolean value
    
    static constraints = {
        defaultNext(nullable:true) // Hvorfor nullable?
        variableName(nullable:false)
        value(nullable:false)
    }

    @Override
    void visit(PatientQuestionnaireNodeVisitor visitor) {
        visitor.visitBooleanNode(this)
    }
}
