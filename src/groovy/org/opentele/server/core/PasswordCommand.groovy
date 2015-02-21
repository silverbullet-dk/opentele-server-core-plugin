package org.opentele.server.core
import org.codehaus.groovy.grails.validation.Validateable
import org.opentele.server.model.User
import org.opentele.server.core.util.PasswordUtil

@Validateable
class PasswordCommand {

    User user
    String currentPassword
    String password
    String passwordRepeat

    static constraints = {
        user(nullable: true)
        currentPassword(nullable: false, blank: false)
        password(nullable: false, blank: false, validator: PasswordUtil.passwordValidator)
        passwordRepeat(validator: { value, obj -> value == obj.password })
    }
}
