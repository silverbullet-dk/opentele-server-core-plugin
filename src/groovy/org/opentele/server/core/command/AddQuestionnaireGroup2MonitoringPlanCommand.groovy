package org.opentele.server.core.command

import grails.validation.Validateable
import groovy.transform.ToString
import groovy.util.logging.Log4j
import org.opentele.server.model.MonitoringPlan
import org.opentele.server.model.questionnaire.QuestionnaireGroup
import org.opentele.server.model.questionnaire.QuestionnaireGroup2QuestionnaireHeader

@Log4j
@ToString(includeNames = true)
@Validateable(nullable = true)
class AddQuestionnaireGroup2MonitoringPlanCommand {
    MonitoringPlan monitoringPlan
    QuestionnaireGroup questionnaireGroup
    List<Map> newQuestionnaires
    Map questionnaireGroup2Headers = [:]

    List<QuestionnaireGroup2HeaderCommand> addedQuestionnaireGroup2Headers() {
        questionnaireGroup2Headers.collect {
            def group2Header = QuestionnaireGroup2QuestionnaireHeader.get(it.key)
            def useStandard = it.value.useStandard
            def addQuestionnaire = it.value.addQuestionnaire ? it.value.addQuestionnaire : false
            new QuestionnaireGroup2HeaderCommand(questionnaireGroup2Header: group2Header,
                                                 useStandard: useStandard, addQuestionnaire: addQuestionnaire)
        }
    }

    List<QuestionnaireGroup> getQuestionnaireGroups() {
        QuestionnaireGroup.list()
    }
    List addedQuestionnaires = []
    List updatedQuestionnaires = []

    static constraints = {
        monitoringPlan nullable: false
        questionnaireGroup nullable: false
    }
}

@Validateable(nullable = true)
@ToString(includeNames = true)
class QuestionnaireGroup2HeaderCommand {
    QuestionnaireGroup2QuestionnaireHeader questionnaireGroup2Header
    boolean useStandard
    boolean addQuestionnaire
}
