package org.opentele.server.model.patientquestionnaire

import org.opentele.server.core.model.patientquestionnaire.PatientQuestionnaireNodeVisitor

class PatientNoteInputNode extends PatientQuestionnaireNode {
    String text

    static constraints = {
        defaultNext(nullable:true) // Hvorfor nullable?
    }
    
    static mapping = {
        text type: 'text'
    }

    @Override
    void visit(PatientQuestionnaireNodeVisitor visitor) {
        visitor.visitNoteInputNode(this)
    }
}
