package org.opentele.server.model.patientquestionnaire

import org.opentele.server.core.model.AbstractObject
import org.opentele.server.model.HelpImage

class PatientHelpInfo extends AbstractObject {
    String text
    HelpImage helpImage

    static belongsTo = [questionnaireNode: PatientQuestionnaireNode]

    static mapping = {
        text type: 'text'
    }

    static constraints = {
        text(nullable: true, blank:true)
        helpImage(nullable: true)
    }
}
