package org.opentele.server.core

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.userdetails.GrailsUserDetailsService
import org.opentele.server.core.todo.model.OpenteleGrailsUserDetails
import org.opentele.server.model.Permission
import org.opentele.server.model.RolePermission
import org.opentele.server.model.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException
import org.springframework.security.core.authority.GrantedAuthorityImpl
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException

/**
 * Implement UserDetails service to handle roles and permissions
 */
class UserDetailsService implements GrailsUserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsService.class.name)
    static final List NO_ROLES = [new GrantedAuthorityImpl(SpringSecurityUtils.NO_ROLE)]

    @Override
    UserDetails loadUserByUsername(String username, boolean loadRoles) throws UsernameNotFoundException, DataAccessException {
        return loadUserByUsername(username)
    }

    @Override
    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException, DataAccessException {

        User user = User.findByUsername(s)
        if (!user) {
            throw new UsernameNotFoundException('User not found', s)
        }

        def permissions = Permission.executeQuery(
                "select distinct p.permission from Permission as p " +
                "inner join p.rolePermissions as rp " +
                "inner join rp.role as r " +
                "inner join r.userRoles as ur " +
                "inner join ur.user as u " +
                "where u.id = ? ", user.id)

        def authorities = []
        permissions.each {
            authorities << new GrantedAuthorityImpl(it)
        }

        return new OpenteleGrailsUserDetails (user.username, user.password, user.enabled,
                !user.accountExpired, !user.passwordExpired,
                !user.accountLocked, authorities ?: NO_ROLES, user.id)
    }
}
