package org.opentele.server.core.model.questionnaire
import grails.test.mixin.Mock
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import org.opentele.server.core.model.Schedule
import org.opentele.server.core.model.types.Weekday
import org.opentele.server.model.questionnaire.Questionnaire
import org.opentele.server.model.questionnaire.QuestionnaireHeader
import org.opentele.server.model.questionnaire.StandardSchedule

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

@TestMixin(GrailsUnitTestMixin)
@Mock([Questionnaire])
class StandardScheduleTest {
    def questionnaire

    void setUp() {
        questionnaire = new Questionnaire(revision: 1, creationDate: new Date(), questionnaireHeader: new QuestionnaireHeader())
        questionnaire.standardSchedule = new StandardSchedule()
    }

    void testDefaultValuesForStandardSchedule() {
        def standardSchedule = new StandardSchedule()
        assertEquals(Schedule.ScheduleType.UNSCHEDULED, standardSchedule.type)
    }

    void testQuestionnaireValidWithoutStandardSchedule()
    {
        assertTrue questionnaire.validate()
    }

    void testAnyStandardScheduleIsValid()
    {
        questionnaire.standardSchedule = new StandardSchedule()
        assertTrue questionnaire.validate()
    }

    void testSaveAndGet() {
        def questionnaire = new Questionnaire(revision: 1, creationDate: new Date(), questionnaireHeader: new QuestionnaireHeader())
        def standardSchedule = new StandardSchedule(
                type: Schedule.ScheduleType.WEEKDAYS, timesOfDay: Schedule.TimeOfDay.toTimesOfDay('12:11'), weekdays: Weekday.FRIDAY
        )
        questionnaire.standardSchedule = standardSchedule
        questionnaire.save(failOnError: true)
        questionnaire = Questionnaire.get(questionnaire.id)

        assertEquals(Schedule.ScheduleType.WEEKDAYS, questionnaire.standardSchedule.type)
    }
}
