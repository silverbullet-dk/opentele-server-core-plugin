package org.opentele.server.model

import org.opentele.server.core.model.AbstractObject

class HelpImage extends AbstractObject{
    String filename

    static constraints = {
        filename(blank:false,nullable:false)
    }
}
