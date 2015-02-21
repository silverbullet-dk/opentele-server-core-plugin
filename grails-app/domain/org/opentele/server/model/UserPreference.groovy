package org.opentele.server.model

import org.opentele.server.core.model.AbstractObject

import groovy.transform.ToString

@ToString(includeNames = true)
class UserPreference extends AbstractObject {

    static belongsTo = [clinician: Clinician]

    String preference
    String value

    static constraints = {
        preference nullable: false
    }
}
