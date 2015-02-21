package org.opentele.server.core.util

import org.opentele.server.core.util.CaseInsensitivePasswordAuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder
import org.springframework.security.core.userdetails.UserDetails
import spock.lang.Specification

class CaseInsensitivePasswordAuthenticationProviderSpec extends Specification {
    def caseInsensitivePasswordAuthenticationProvider = new CaseInsensitivePasswordAuthenticationProvider()
    def passwordEncoder = new MessageDigestPasswordEncoder('SHA-256')

    def userDetails = Mock(UserDetails)
    def setup() {
        caseInsensitivePasswordAuthenticationProvider.passwordEncoder = passwordEncoder
    }

    def "check that password case insensitive works as expected"() {
        setup:
        userDetails.getPassword() >> passwordEncoder.encodePassword("lowercase-password", null)  // simulate from database
        def authentication = new UsernamePasswordAuthenticationToken("dummy","LOWERCASE-PASSWORD")

        when:
        caseInsensitivePasswordAuthenticationProvider.additionalAuthenticationChecks(userDetails, authentication)

        then:
        noExceptionThrown()
    }

    def "check that password case sensitive still works as expected"() {
        setup:
        userDetails.getPassword() >> passwordEncoder.encodePassword("MixedCase-password", null)  // simulate from database
        def authentication = new UsernamePasswordAuthenticationToken("dummy","MixedCase-password")

        when:
        caseInsensitivePasswordAuthenticationProvider.additionalAuthenticationChecks(userDetails, authentication)

        then:
        noExceptionThrown()
    }
}
