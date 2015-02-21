package org.opentele.server.core.model

import grails.buildtestdata.mixin.Build
import grails.test.mixin.TestFor
import org.opentele.server.model.cgm.ContinuousBloodSugarEvent
import org.opentele.server.model.Measurement
import org.opentele.server.model.MeasurementType
import org.opentele.server.core.model.types.MeasurementTypeName
import spock.lang.Specification

@TestFor(ContinuousBloodSugarEvent)
@Build([Measurement, MeasurementType, ContinuousBloodSugarEvent])
class ContinuousBloodSugarMeasurementSpec extends Specification {
    Measurement measurement

    def setup() {
        measurement = Measurement.build(measurementType: MeasurementType.build(name: MeasurementTypeName.CONTINUOUS_BLOOD_SUGAR_MEASUREMENT))
    }

    def 'requires a measurement'() {
        when:
        ContinuousBloodSugarEvent continuousBloodSugarEvent = new ContinuousBloodSugarEvent(time: new Date(), value: 5)

        then:
        !continuousBloodSugarEvent.validate()
    }

    def 'requires a time'() {
        when:
        ContinuousBloodSugarEvent continuousBloodSugarMeasurement = new ContinuousBloodSugarEvent(measurement: measurement, value: 5)

        then:
        !continuousBloodSugarMeasurement.validate()
    }

    def 'can be created with measurement, record ID, time, and value'() {
        when:
        ContinuousBloodSugarEvent continuousBloodSugarMeasurement = new ContinuousBloodSugarEvent(measurement: measurement, recordNumber: 2000, time: new Date(), value: 5)

        then:
        continuousBloodSugarMeasurement.validate()
    }

    def 'can be found through measurement'() {
        when:
        10.times {
            measurement.addToContinuousBloodSugarEvents(recordNumber: it, time: new Date(), value: 5)
        }

        then:
        measurement.continuousBloodSugarEvents.size() == 10
    }
}
