package org.opentele.server.core.model.patientquestionnaire

import org.opentele.server.model.patientquestionnaire.PatientQuestionnaireNode

public interface PatientQuestionnaireNodeVisitor {
    void visitEndNode(PatientQuestionnaireNode node)
    void visitBooleanNode(PatientQuestionnaireNode node)
    void visitChoiceNode(PatientQuestionnaireNode node)
    void visitInputNode(PatientQuestionnaireNode node)
    void visitDelayNode(PatientQuestionnaireNode node)
    void visitNoteInputNode(PatientQuestionnaireNode node)
    void visitMeasurementNode(PatientQuestionnaireNode node)
    void visitTextNode(PatientQuestionnaireNode node)
}