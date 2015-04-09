package org.opentele.server.model

import org.apache.commons.codec.binary.Base64
import org.opentele.server.core.model.AbstractObject

class RealTimeCtg extends AbstractObject {

    String xml
    String soapAction
    Patient patient

    static fetchMode = [patient: 'eager']

    static mapping = {
        xml type: 'text'
    }

    static constraints = {
        xml nullable: false
        soapAction nullable: false
        patient nullable: false
    }

    String getXMLAsString() {
        new String(Base64.decodeBase64(xml))
    }

}
