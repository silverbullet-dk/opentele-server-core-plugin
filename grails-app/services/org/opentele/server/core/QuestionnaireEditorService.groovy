package org.opentele.server.core

import org.opentele.server.core.command.QuestionnaireEditorCommand
import org.opentele.server.model.Clinician
import org.opentele.server.model.questionnaire.Questionnaire
import org.opentele.server.model.questionnaire.QuestionnaireHeader
import org.springframework.transaction.annotation.Transactional

class QuestionnaireEditorService {
    def questionnaireNodeService

    void createOrUpdateQuestionnaire(QuestionnaireEditorCommand command, Clinician creator) {
        QuestionnaireHeader questionnaireHeader = getOrCreateQuestionnaireDraft(command.questionnaireHeader, creator)

        def questionnaire = questionnaireHeader.draftQuestionnaire
        if(questionnaire.nodes) {
            questionnaireNodeService.deleteQuestionnaireNodes(questionnaireHeader.draftQuestionnaire)
        }

        new EditorQuestionnaireBuilder().buildQuestionnaire(command, questionnaire)
        questionnaire.save(failOnError: true)
    }

    @Transactional
    QuestionnaireHeader getOrCreateQuestionnaireDraft(QuestionnaireHeader questionnaireHeader, Clinician creator) {
        if(questionnaireHeader.draftQuestionnaire) {
            questionnaireHeader
        } else {
            def nextRevision = calculateNextRevision(questionnaireHeader)
            def questionnaire = new Questionnaire(questionnaireHeader: questionnaireHeader, creator: creator, revision: nextRevision, creationDate: new Date())
            questionnaire.save(failOnError: true)
            questionnaireHeader.draftQuestionnaire = questionnaire
            questionnaireHeader.addToQuestionnaires(questionnaire)
            questionnaireHeader.save(failOnError: true)
        }
    }

    private String calculateNextRevision(QuestionnaireHeader questionnaireHeader) {
        def latestQuestionnaire = questionnaireHeader.questionnaires.max { it.creationDate }
        def nextRevision = latestQuestionnaire?.revision?.toBigDecimal() ?: 0
        nextRevision = Math.floor(nextRevision) + 1
        nextRevision
    }
}



