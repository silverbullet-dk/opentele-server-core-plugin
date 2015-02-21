package org.opentele.server.core

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormatter
import org.joda.time.format.ISODateTimeFormat
import org.opentele.server.core.util.CustomDomainClassJSONMarshaller
import org.opentele.server.core.util.CustomGroovyBeanJSONMarshaller
import grails.converters.JSON


// HRA TODO: Fjernes, eller opsplittes til mere passende placeringer

class RoleNameService {
    // User role names
    public static String roleAdministrator = "Administrator"
    public static String rolePatient = "Patient"
    public static String roleClinician = "Kliniker"
    public static String roleVideoConsultant = "Videokonsultør"
    public static String roleReadAllPatientsInSystem = "Adgang til alle patienter"
}
