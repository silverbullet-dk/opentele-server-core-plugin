package org.opentele.server.model

import org.opentele.server.core.model.AbstractObject

class Meter extends AbstractObject {

    static belongsTo = [patient : Patient, meterType:MeterType, monitorKit: MonitorKit]
    
    boolean active
     
    String meterId
    String model 
    
    String toString() {
        model
    }
    
	static constraints = {
        active(nullable:false)
        meterId(nullable:false)
        model(nullable:false)
        meterType(nullable:false)
        patient(nullable:true)
        monitorKit(nullable:true)
    }
}
