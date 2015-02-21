package org.opentele.server.model.questionnaire

import org.opentele.server.core.model.AbstractObject
import org.opentele.server.model.HelpImage

class HelpInfo  extends AbstractObject {
    String text
    HelpImage helpImage

    static belongsTo = [questionnaireNode: QuestionnaireNode]

    static mapping = {
        text type: 'text'
    }

    static constraints = {
        text(nullable: true, blank:true)
        helpImage(nullable: true)
    }
}
