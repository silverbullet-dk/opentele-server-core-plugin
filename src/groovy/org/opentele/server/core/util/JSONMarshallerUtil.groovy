package org.opentele.server.core.util

import grails.converters.JSON
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat

public class JSONMarshallerUtil {

    static void registerCustomJSONMarshallers(def grailsApplication) {
        JSON.registerObjectMarshaller(new CustomDomainClassJSONMarshaller(false, grailsApplication), 2)
        JSON.registerObjectMarshaller(new CustomGroovyBeanJSONMarshaller(), 1)
        JSON.registerObjectMarshaller(Date) {
            it == null ? null : ISODateTimeFormat.dateTime().print(new DateTime(it))
        }
    }

}

