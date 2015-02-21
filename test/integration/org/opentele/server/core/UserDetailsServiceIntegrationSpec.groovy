package org.opentele.server.core

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.test.spock.IntegrationSpec
import org.opentele.server.model.Permission
import org.opentele.server.model.Role
import org.opentele.server.model.RolePermission
import org.opentele.server.model.User
import org.opentele.server.model.UserRole
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException


class UserDetailsServiceIntegrationSpec extends IntegrationSpec {
    UserDetailsService service

    User user

    def setup() {
        service = new UserDetailsService()
        user = User.findByUsername('testuser') ?: new User(username: 'testuser', password: 'abcd1234', enabled: true).save(failOnError: true, flush: true)
    }

    def "Test that User with single permission only gets a single permission from service"() {
        setup:
        def role = new Role(authority: 'test').save(failOnError: true, flush: true)
        def permission = new Permission(permission: 'permission').save(failOnError: true, flush: true)
        new RolePermission(role: role, permission: permission).save(failOnError: true, flush: true)
        new UserRole(user: user, role: role).save(failOnError: true, flush: true)

        when:
        UserDetails res = service.loadUserByUsername('testuser')

        then:
        res.authorities.size() == 1
    }

    def "User with two roles with the same permission gives only one permission"() {
        setup:
        def role1 = new Role(authority: 'test1').save(failOnError: true, flush: true)
        def permission = new Permission(permission: 'permission').save(failOnError: true, flush: true)
        new RolePermission(role: role1, permission: permission).save(failOnError: true, flush: true)
        new UserRole(user: user, role: role1).save(failOnError: true, flush: true)

        def role2 = new Role(authority: 'test2').save(failOnError: true, flush: true)
        new RolePermission(role: role2, permission: permission).save(failOnError: true, flush: true)
        new UserRole(user: user, role: role2).save(failOnError: true, flush: true)

        when:
        UserDetails res = service.loadUserByUsername('testuser')

        then:
        res.authorities.size() == 1
    }

    def "User with two roles different permissions gives two permissions"() {
        setup:
        def role1 = new Role(authority: 'test1').save(failOnError: true, flush: true)
        def permission1 = new Permission(permission: 'permission1').save(failOnError: true, flush: true)
        new RolePermission(role: role1, permission: permission1).save(failOnError: true, flush: true)
        new UserRole(user: user, role: role1).save(failOnError: true, flush: true)

        def role2 = new Role(authority: 'test2').save(failOnError: true, flush: true)
        def permission2 = new Permission(permission: 'permission2').save(failOnError: true, flush: true)
        new RolePermission(role: role2, permission: permission2).save(failOnError: true, flush: true)
        new UserRole(user: user, role: role2).save(failOnError: true, flush: true)

        when:
        UserDetails res = service.loadUserByUsername('testuser')

        then:
        res.authorities.size() == 2
        res.authorities[0].getAuthority() != res.authorities[1].getAuthority()
    }

    def "If no user found an exception is thrown"() {
        setup:

        when:
        service.loadUserByUsername('gdhasgdhsa')

        then:
        thrown UsernameNotFoundException
    }

    def "User without permissions gives NO_ROLES"() {
        setup:

        when:
        UserDetails res = service.loadUserByUsername('testuser')

        then:
        res.authorities.size() == 1
        res.authorities[0].getAuthority() == SpringSecurityUtils.NO_ROLE
    }
}