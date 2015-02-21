package org.opentele.server.model.questionnaire

import org.opentele.server.core.model.AbstractObject

class QuestionnaireGroup extends AbstractObject {
    String name

    @Override
    String toString() {
        name
    }

    static hasMany = [questionnaireGroup2Header: QuestionnaireGroup2QuestionnaireHeader]
    static constraints = {
        name(blank: false, unique: true)
    }
    static mapping = {
        questionnaireGroup2Header cascade: "all-delete-orphan"
    }
}
