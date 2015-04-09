package org.opentele.server.core.model

import grails.buildtestdata.mixin.Build
import grails.test.mixin.TestFor
import org.opentele.server.core.model.types.Sex
import org.opentele.server.model.Measurement
import org.opentele.server.model.MeasurementType
import org.opentele.server.model.PassiveInterval
import org.opentele.server.model.Patient
import org.opentele.server.model.patientquestionnaire.CompletedQuestionnaire
import org.opentele.server.core.model.types.PatientState
import spock.lang.Specification
import spock.lang.Unroll

@TestFor(Patient)
@Build([Patient,Measurement, MeasurementType, CompletedQuestionnaire])
class PatientSpec extends Specification {
    Patient patient
    def setup() {
        patient = Patient.build()
    }

    def "test that getLatestQuestionnaireUploadDate works as expected"() {
        setup:
        [2,4,1].each { day ->
            def completedQuestionnaire = CompletedQuestionnaire.build(uploadDate: new Date())
            completedQuestionnaire.uploadDate[Calendar.DATE] = day
            patient.addToCompletedQuestionnaires(completedQuestionnaire)
        }
        patient.save()

        expect:
        patient.latestQuestionnaireUploadDate[Calendar.DATE] == 4
    }

    def "test that getLatestQuestionnaireUploadDate works as expected when there is no completed questionnaires"() {
        expect:
        !patient.latestQuestionnaireUploadDate
    }

    def 'takes passive interval into account when calculating extended state'() {
        when:
        def startDate = new GregorianCalendar(2014, Calendar.JANUARY, 1).getTime()
        def endDate = new Date(System.currentTimeMillis() + 1000 * 1000)
        patient.passiveIntervals = [new PassiveInterval(intervalStartDate: startDate, intervalEndDate: endDate)]

        then:
        patient.stateWithPassiveIntervals == PatientState.PAUSED
    }

    def 'gives normal patient state if patient is not passive'() {
        expect:
        patient.state == patient.stateWithPassiveIntervals
    }

    def 'does not accept PAUSED as a valid state'() {
        when:
        patient.state = PatientState.PAUSED

        then:
        !patient.validate()
    }

    @Unroll
    def "test gestation age is computed correctly"() {
        when:
        patient.dueDate = dueDate

        then:
        expected == patient.getGestationalAge(now)

        where:
        now                                                                 | dueDate                                                       | expected
        new GregorianCalendar(2014, Calendar.FEBRUARY, 4        ).getTime() | new GregorianCalendar(2014, Calendar.FEBRUARY ,  5).getTime() | "39+6"
        new GregorianCalendar(2014, Calendar.FEBRUARY, 4        ).getTime() | new GregorianCalendar(2014, Calendar.APRIL    ,  6).getTime() | "31+2"
        new GregorianCalendar(2014, Calendar.FEBRUARY, 4        ).getTime() | new GregorianCalendar(2014, Calendar.SEPTEMBER,  8).getTime() | "9+1"
        new GregorianCalendar(2014, Calendar.FEBRUARY, 4        ).getTime() | new GregorianCalendar(2014, Calendar.FEBRUARY ,  4).getTime() | "40+0"
        new GregorianCalendar(2014, Calendar.FEBRUARY, 4, 15, 15).getTime() | new GregorianCalendar(2014, Calendar.FEBRUARY ,  4).getTime() | "40+0"
        new GregorianCalendar(2014, Calendar.FEBRUARY, 4, 23,  1).getTime() | new GregorianCalendar(2014, Calendar.FEBRUARY ,  4).getTime() | "40+0"
        new GregorianCalendar(2014, Calendar.FEBRUARY, 4        ).getTime() | new GregorianCalendar(2014, Calendar.JANUARY  , 14).getTime() | "43+0"
        new GregorianCalendar(2014, Calendar.FEBRUARY, 4        ).getTime() | new GregorianCalendar(2014, Calendar.JANUARY  , 15).getTime() | "42+6"
        new GregorianCalendar(2014, Calendar.FEBRUARY, 4        ).getTime() | new GregorianCalendar(2014, Calendar.JANUARY  , 13).getTime() | ""
        new GregorianCalendar(2014, Calendar.FEBRUARY, 4        ).getTime() | new GregorianCalendar(2014, Calendar.NOVEMBER , 10).getTime() | ""
        new GregorianCalendar(2014, Calendar.FEBRUARY, 4        ).getTime() | new GregorianCalendar(2015, Calendar.NOVEMBER , 11).getTime() | ""
    }

    def "calls identification validation mixin if set" () {
        given:
        def mixinUsed = false
        TestIdentificationMixin.callback = { mixinUsed = true }
        Patient.mixin(TestIdentificationMixin)
        Patient toValidate = createValidPatient()

        when:
        toValidate.validate()

        then:
        mixinUsed == true
    }

    private Patient createValidPatient() {
        def toValidate = new Patient(firstName: 'bla', lastName: 'morebla', address: 'bla', postalCode: '8888', city: 'bla', sex: Sex.MALE, cpr: '1234567890')
        toValidate
    }
}

class TestIdentificationMixin {

    def static callback

    def validateIdentification(val, obj, errors) {
        if (callback) {
            callback()
        }
        callback = null
    }
}
