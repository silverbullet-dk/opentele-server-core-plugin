package org.opentele.server.core.test

import grails.test.spock.IntegrationSpec
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder

class AbstractIntegrationSpec extends IntegrationSpec {
    def caseInsensitivePasswordAuthenticationProvider

    protected authenticate = {String login, String password ->
        def auth = new UsernamePasswordAuthenticationToken(login, password)
        def authtoken = caseInsensitivePasswordAuthenticationProvider.authenticate(auth)

        SecurityContextHolder.getContext().setAuthentication(authtoken)
    }
}
