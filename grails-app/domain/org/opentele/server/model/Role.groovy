package org.opentele.server.model

import org.opentele.server.core.model.AbstractObject

class Role extends AbstractObject {

	String authority

    static hasMany = [
            userRoles: UserRole,
            rolePermissions: RolePermission
    ]

    static mapping = {
		cache true
	}

	static constraints = {
		authority blank: false, unique: true,  maxSize: 128
	}
}