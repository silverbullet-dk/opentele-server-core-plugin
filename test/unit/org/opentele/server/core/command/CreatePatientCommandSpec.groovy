package org.opentele.server.core.command

import grails.buildtestdata.mixin.Build
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.web.ControllerUnitTestMixin
import org.opentele.server.core.model.types.Sex
import org.opentele.server.model.NumericThreshold
import org.opentele.server.model.Patient
import org.opentele.server.model.PatientGroup
import org.opentele.server.model.StandardThresholdSet
import org.opentele.server.model.Threshold
import spock.lang.Specification

@TestMixin(ControllerUnitTestMixin)
@Mock([Patient, PatientGroup])
@Build([PatientGroup, StandardThresholdSet, NumericThreshold])
class CreatePatientCommandSpec extends Specification {

    def setup() {
        mockCommandObject(CreatePatientCommand)
    }

    def "calls identification validation mixin if set" () {
        given:
        def mixinUsed = false
        TestIdentificationMixin.callback = { mixinUsed = true }
        CreatePatientCommand.mixin(TestIdentificationMixin)
        def toValidate = createCommand()

        when:
        toValidate.validate()

        then:
        mixinUsed == true
    }

    def "updated thresholds will not be overwritten if patient groups has not unchanged"() {
        given:
        def command = createCommand()
        def groupIds = createPatientGroupsWithThresholds()

        command.setPatientGroups(groupIds)
        command.thresholds.clear()

        when:
        command.setPatientGroups(groupIds)

        then:
        command.thresholds.size() == 0
    }

    def "updated thresholds will be overwritten if patient groups has changed"() {
        given:
        def command = createCommand()
        def groupIds = createPatientGroupsWithThresholds()

        command.setPatientGroups(groupIds)
        def expectedThresholds = command.thresholds.size()
        command.thresholds.clear()

        when:
        command.setPatientGroups(groupIds.drop(1))

        then:
        command.thresholds.size() == expectedThresholds
    }

    private String[] createPatientGroupsWithThresholds() {

        def thresholdSet = StandardThresholdSet.build()
        thresholdSet.thresholds = [[NumericThreshold.build()]] as HashSet

        def pg1 = PatientGroup.build()
        pg1.standardThresholdSet = thresholdSet
        def pg2 = PatientGroup.build()
        pg2.standardThresholdSet = thresholdSet

        pg1.save(failOnError: true)
        pg2.save(failOnError: true)

        def groupIds = [pg1.id.toString(), pg2.id.toString()] as String[]
        return groupIds
    }

    def createCommand() {
        return new CreatePatientCommand(firstName: 'bla', lastName: 'morebla', address: 'bla', postalCode: '8888', city: 'bla', sex: Sex.MALE, cpr: '1234567890')
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
