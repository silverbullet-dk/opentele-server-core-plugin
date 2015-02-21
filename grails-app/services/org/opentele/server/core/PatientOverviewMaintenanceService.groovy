package org.opentele.server.core

import  org.opentele.server.core.model.types.PatientState
import org.opentele.server.core.model.types.Severity
import org.opentele.server.model.*
import org.opentele.server.model.patientquestionnaire.CompletedQuestionnaire
import org.opentele.server.model.patientquestionnaire.PatientQuestionnaire

class PatientOverviewMaintenanceService {
    //def questionnaireService

    // Bootstrap etc
    void createOverviewFor(Patient patient) {
        PatientOverview patientOverview = new PatientOverview(patientOverviewProperties(patient)).save(failOnError: true)
        patient.addToPatientOverviews(patientOverview)
    }

    void updateOverviewFor(Patient patient) {
        PatientOverview existingOverview = patient.patientOverview
        existingOverview.setProperties(patientOverviewProperties(patient))
        existingOverview.save(failOnError: true)
    }

    // Checkjob
    boolean overviewDetailsAreWrongFor(Patient patient) {
        Map correctProperties = patientOverviewProperties(patient)
        Map actualProperties = patient.patientOverview.getProperties().findAll { it.key in correctProperties.keySet() }
        actualProperties != correctProperties
    }

    //Client + server (used from patientOverviewMaintenanceService)
    private def worstSeverityOfUnacknowledgedQuestionnaires(Patient patient, questionnaires) {
        Severity severity = Severity.NONE
        questionnaires.each { questionnaire ->
            if (questionnaire.severity && questionnaire.severity > severity) {
                severity = questionnaire.severity
            }
        }

        if (severity < Severity.BLUE && !patient.blueAlarmQuestionnaireIDs.empty) {
            return Severity.BLUE
        }

        severity
    }

    private def patientOverviewProperties(Patient patient) {
        String name = patient.name
        String cpr = patient.cpr

        List<CompletedQuestionnaire> unacknowledgedQuestionnaires = findUnacknowledgedQuestionnaires(patient)
        int numberOfUnacknowledgedQuestionnaires = unacknowledgedQuestionnaires.size()
        Severity worstSeverity = worstSeverityOfUnacknowledgedQuestionnaires(patient, unacknowledgedQuestionnaires)

        CompletedQuestionnaire questionnaireOfWorstSeverity = findQuestionnaireOfWorstSeverity(worstSeverity, unacknowledgedQuestionnaires)
        String blueAlarmText = collectBlueAlarmText(patient)
        String greenQuestionnaireIds = collectGreenQuestionnaireIds(unacknowledgedQuestionnaires)

        List<Message> unreadMessages = Message.findAllByPatientAndIsRead(patient, false, [sort: 'sendDate', order: 'asc'])
        List<Message> unreadMessagesToPatient = unreadMessages.findAll { !it.sentByPatient }
        Date dateOfOldestUnreadMessageToPatient = unreadMessagesToPatient.empty ? null : unreadMessagesToPatient.first().sendDate
        List<Message> unreadMessagesFromPatient = unreadMessages.findAll { it.sentByPatient }
        Date dateOfOldestUnreadMessageFromPatient = unreadMessagesFromPatient.empty ? null : unreadMessagesFromPatient.first().sendDate


        boolean unreadMessagesToPatientTriggersAlarm = patient.noAlarmIfUnreadMessagesToPatient ? !unreadMessagesFromPatient.empty : !unreadMessages.empty

        boolean important = (patient.state == PatientState.ACTIVE) && (
        numberOfUnacknowledgedQuestionnaires > 0 ||
                worstSeverity > Severity.NONE ||
                unreadMessagesToPatientTriggersAlarm)

        [
                patient: patient,
                name: name,
                cpr: cpr,
                numberOfUnacknowledgedQuestionnaires: numberOfUnacknowledgedQuestionnaires,
                questionnaireSeverity: worstSeverity,
                questionnaireSeverityOrdinal: worstSeverity?.ordinal(),
                mostSevereQuestionnaireName: questionnaireOfWorstSeverity?.questionnaireHeader?.name,
                mostSevereQuestionnaireDate: questionnaireOfWorstSeverity?.uploadDate,
                blueAlarmText: blueAlarmText,
                greenQuestionnaireIds: greenQuestionnaireIds,

                numberOfUnreadMessagesToPatient: unreadMessagesToPatient.size(),
                dateOfOldestUnreadMessageToPatient: dateOfOldestUnreadMessageToPatient,
                numberOfUnreadMessagesFromPatient: unreadMessagesFromPatient.size(),
                dateOfOldestUnreadMessageFromPatient: dateOfOldestUnreadMessageFromPatient,

                important: important
        ]
    }

    private List<CompletedQuestionnaire> findUnacknowledgedQuestionnaires(Patient patient) {
        CompletedQuestionnaire.findAllByPatientAndAcknowledgedDateIsNull(patient, [sort: 'uploadDate', order: 'desc'])
    }

    private CompletedQuestionnaire findQuestionnaireOfWorstSeverity(Severity worstSeverity, List<CompletedQuestionnaire> questionnaires) {
        questionnaires.find { it.severity == worstSeverity }
    }

    private String collectBlueAlarmText(Patient patient) {
        if (patient.blueAlarmQuestionnaireIDs == null || patient.blueAlarmQuestionnaireIDs.empty) {
            return null
        }
        patient.blueAlarmQuestionnaireIDs.collect { PatientQuestionnaire.get(it).name }.join('\n')
    }

    private String collectGreenQuestionnaireIds(List<CompletedQuestionnaire> completedQuestionnaires) {
        List<CompletedQuestionnaire> greenQuestionnaires = completedQuestionnaires.findAll { it.severity == Severity.GREEN }
        greenQuestionnaires.empty ? null : greenQuestionnaires.collect { it.id }.join(',')
    }
}
