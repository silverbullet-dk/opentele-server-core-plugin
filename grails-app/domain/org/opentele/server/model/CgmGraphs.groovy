package org.opentele.server.model

import org.opentele.server.core.model.AbstractObject

class CgmGraphs extends AbstractObject {
    Patient patient
    byte[] normalGraph
    byte[] averageGraph
}
