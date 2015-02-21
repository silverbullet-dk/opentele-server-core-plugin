package org.opentele.server.core.command

import grails.validation.Validateable
import org.opentele.server.model.PatientGroup
import org.opentele.server.core.model.types.PatientState


@Validateable(nullable = true)
class PatientSearchCommand {
    String ssn
    String phone
    String firstName
    String lastName
    String username
    PatientState status = PatientState.ACTIVE
    PatientGroup patientGroup

    static List<PatientGroup> getAllPatientGroups() {
        PatientGroup.list([sort: 'name'])
    }

    String getSsn() {
        this.ssn?.replace('-','')
    }
}
