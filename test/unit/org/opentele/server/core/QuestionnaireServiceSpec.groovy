package org.opentele.server.core

import grails.buildtestdata.mixin.Build
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.opentele.builders.MonitoringPlanBuilder
import org.opentele.builders.PatientBuilder
import org.opentele.builders.QuestionnaireScheduleBuilder

import org.opentele.server.core.model.Schedule

import org.opentele.server.core.model.types.Unit

import org.opentele.server.core.model.types.Weekday
import org.opentele.server.model.MonitoringPlan
import org.opentele.server.model.Patient
import org.opentele.server.model.QuestionnaireSchedule
import org.opentele.server.model.ScheduleWindow
import org.opentele.server.model.patientquestionnaire.CompletedQuestionnaire
import org.opentele.server.model.patientquestionnaire.PatientBooleanNode
import org.opentele.server.model.patientquestionnaire.PatientQuestionnaire
import org.opentele.server.model.questionnaire.*
import spock.lang.Specification
import spock.lang.Unroll

import static org.opentele.server.core.model.Schedule.ScheduleType.*

@TestFor(QuestionnaireService)
@Build([StandardSchedule, QuestionnaireGroup, QuestionnaireGroup2QuestionnaireHeader, QuestionnaireHeader, Questionnaire, QuestionnaireNode, QuestionnaireSchedule, PatientBooleanNode, BooleanNode, ScheduleWindow, PatientQuestionnaire, MonitoringPlan, CompletedQuestionnaire])
@Mock([CompletedQuestionnaire, Questionnaire2MeterType])
class QuestionnaireServiceSpec extends Specification {
    Patient patient
    Date monitoringPlanStartDate
    MonitoringPlan monitoringPlan

    def setup() {
        patient = new PatientBuilder().build()
        monitoringPlanStartDate = Date.parse("yyyy/M/d", "2013/6/5")
        monitoringPlan = new MonitoringPlanBuilder().forPatient(patient).forStartDate(monitoringPlanStartDate).build()
        //Set windowsize that match old static windowsizes
        ScheduleWindow.build(scheduleType: WEEKDAYS, windowSizeMinutes: 30)
        ScheduleWindow.build(scheduleType: MONTHLY, windowSizeMinutes: 30)
        ScheduleWindow.build(scheduleType: EVERY_NTH_DAY, windowSizeMinutes: 30)

        service.questionnaireNodeService = Mock(QuestionnaireNodeService)
    }

    def "test list will not fail if inactive schema on monitoringplan"() {
        setup:
        patient.monitoringPlan = new MonitoringPlan(patient: patient).build()

        def timesOfDay = [new Schedule.TimeOfDay(hour: 16, minute: 0)]
        QuestionnaireSchedule questionnaireSchedule = new QuestionnaireScheduleBuilder().forMonitoringPlan(monitoringPlan).forScheduleType(WEEKDAYS).forWeekdays(Weekday.values().collect()).forTimesOfDay(timesOfDay).build()
        // Remove active questionnaire
        questionnaireSchedule.getQuestionnaireHeader().setActiveQuestionnaire(null)

        when:
        def list = service.list(patient.cpr)

        then:
        list.questionnaires.empty
    }


    def "test list will find an active questionnaire if there is one"() {
        setup:
        patient.monitoringPlan = new MonitoringPlan(patient: patient).build()

        def timesOfDay = [new Schedule.TimeOfDay(hour: 16, minute: 0)]
        new QuestionnaireScheduleBuilder().forMonitoringPlan(monitoringPlan).forScheduleType(WEEKDAYS).forWeekdays(Weekday.values().collect()).forTimesOfDay(timesOfDay).build()

        when:
        def list = service.list(patient.cpr)

        then:
        list.questionnaires.size() == 1
    }

    @SuppressWarnings("GroovyAccessibility")
    @Unroll
    def "test MeasurementDescription.orderedUnits() formats units correctly"() {
        setup:
        def measurementDescription = new MeasurementDescription(units: testUnitSet)

        expect:
        expected == measurementDescription.orderedUnits()

        where:
        testUnitSet                             | expected
        null                                    | []
        [Unit.BPM, Unit.PERCENTAGE] as Set      | [Unit.PERCENTAGE, Unit.BPM]
        [Unit.PERCENTAGE, Unit.BPM] as Set      | [Unit.PERCENTAGE, Unit.BPM]
        [Unit.MMHG, Unit.BPM] as Set            | [Unit.MMHG, Unit.BPM]
        [Unit.BPM, Unit.MMHG] as Set            | [Unit.MMHG, Unit.BPM]
        [Unit.KILO, Unit.MMHG] as Set           | [Unit.KILO, Unit.MMHG]
        [Unit.MMHG] as Set                      | [Unit.MMHG]
        [Unit.BPM, Unit.BPM, Unit.BPM] as Set   | [Unit.BPM]
        [Unit.BPM, Unit.KILO, Unit.MMHG] as Set | [Unit.BPM, Unit.KILO, Unit.MMHG]
    }

    Calendar subtractOneMinute(Calendar c) {
        def result = c.clone()
        result.add(Calendar.MINUTE, -1)
        result
    }
}
