import org.opentele.server.core.model.BootStrapUtil
import org.opentele.server.core.model.types.MeasurementTypeName

import org.opentele.server.core.model.types.MeterTypeName
import org.opentele.server.core.model.types.PermissionName
import org.opentele.server.core.RoleNameService
import org.opentele.server.model.Role

@SuppressWarnings("GroovyDocCheck")
class OpenTeleCoreBootStrap {

    def springSecurityService

    BootStrapUtil bootStrapUtil = null

    def init = { servletContext ->

        bootStrapUtil = new BootStrapUtil()

        environments {
            development {
                println "Initializing for core DEVEL"
                doBootstrapForTest()
            }
            test {
                println "Initializing for core TEST"
                doBootstrapForTest()
            }
        }
    }

    def doBootstrapForTest() {
        setupRolesAndTypes()

        Role role = bootStrapUtil.setupRoleIfNotExists(RoleNameService.roleReadAllPatientsInSystem, new Date())
        bootStrapUtil.setupPermissionsForRole(role)

    }

    def setupRolesAndTypes() {
        createPermissions()
        createRoles()
        createMeasurementTypes()
        createMeterTypes()
    }


    def createPermissions() {
        PermissionName.allValues().collect {
            bootStrapUtil.createPermissionIfNotExists(it)
        }
    }

    def createRoles() {
        Date now = new Date()
        def role

        role = bootStrapUtil.setupRoleIfNotExists(RoleNameService.roleAdministrator)
        bootStrapUtil.setupPermissionsForRole(role)
        role = bootStrapUtil.setupRoleIfNotExists(RoleNameService.rolePatient, now)
        bootStrapUtil.setupPermissionsForRole(role)
        role = bootStrapUtil.setupRoleIfNotExists(RoleNameService.roleClinician,now)
        bootStrapUtil.setupPermissionsForRole(role)
        role = bootStrapUtil.setupRoleIfNotExists(RoleNameService.roleVideoConsultant,now)
        bootStrapUtil.setupPermissionsForRole(role)
    }

    def createMeasurementTypes() {
        Date now = new Date()

        MeasurementTypeName.values().each { name ->
            bootStrapUtil.createMeasurementTypeIfNotExists(name, now)
        }
    }

    def createMeterTypes() {
        MeterTypeName.values().each { name ->
            bootStrapUtil.createMeterTypeIfNotExists(name)
        }
    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    def destroy = {
    }
}
