package org.opentele.server.core.command

import grails.validation.Validateable
import groovy.transform.ToString
import org.opentele.server.model.Clinician
import org.opentele.server.model.PatientGroup
import org.opentele.server.model.Role
import org.opentele.server.core.util.PasswordUtil


@Validateable(nullable=true) //Provides hasErrors and validate()
@ToString(includeNames = true)
class ClinicianCommand {
    Long id
    Long version
    String firstName
    String lastName
    String phone
    String mobilePhone
    String email
    String videoUser
    String videoPassword

    String username
    String cleartextPassword
    String password

    List<Role> possibleRoles = Role.findAllByAuthorityNotEqual("Patient")
    List<PatientGroup> possiblePatientGroups = PatientGroup.list(order: 'name')

    List<Long> roleIds = []
    List<Long> groupIds = []

    String getPassword() {
        if (password) {
            password
        }
        cleartextPassword
    }

    static constraints = {
        importFrom(Clinician)
        username nullable: false, unique: true, blank: false, maxSize:  128
        cleartextPassword nullable: true, blank: true, validator: PasswordUtil.passwordValidator
        id(nullable: true)
        version(nullable: true)
        roleIds(nullable: false, minSize: 1)
        groupIds(nullable: false, minSize: 1)
        phone(nullable: true)
        mobilePhone(nullable: true)
        email(nullable: true)
        videoUser(nullable: true)
        videoPassword(nullable: true)
    }

}
