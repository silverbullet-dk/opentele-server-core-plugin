package org.opentele.server.core.model

import grails.buildtestdata.mixin.Build
import grails.test.mixin.*
import grails.validation.ValidationException
import org.opentele.server.model.Clinician
import org.opentele.server.model.Conference
import org.opentele.server.model.Patient
import spock.lang.Specification

@TestFor(Conference)
@Build([Patient, Clinician])
class ConferenceSpec extends Specification {
    def 'requires patient'() {
        when:
        new Conference(clinician: Clinician.build()).save(failOnError: true)

        then:
        thrown(ValidationException)
    }

    def 'requires clinician'() {
        when:
        new Conference(patient: Patient.build()).save(failOnError: true)

        then:
        thrown(ValidationException)
    }

    def 'can be created for patient and clinician'() {
        when:
        new Conference(patient: Patient.build(), clinician: Clinician.build()).save(failOnError: true)

        then:
        true // a.k.a. "it does not crash"
    }
}
