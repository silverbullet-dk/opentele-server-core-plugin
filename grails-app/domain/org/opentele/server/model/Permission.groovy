package org.opentele.server.model

import org.opentele.server.core.model.AbstractObject
import org.opentele.server.core.model.types.PermissionName

class Permission extends AbstractObject {

    String permission

    static hasMany = [
            rolePermissions: RolePermission
    ]

    static constraints = {
    }

    String toString() {
        return "Permission - " + permission
    }

    // Strips the leading "ROLE_" string in the permission
    String getActualName() {
        getPermission().trim().substring(5)
    }

    static def allAssignable() {
        Permission.findAllByPermissionNotEqual(PermissionName.NONE)
    }
}
