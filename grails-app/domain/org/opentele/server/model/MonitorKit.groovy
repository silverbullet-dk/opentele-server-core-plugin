package org.opentele.server.model

import org.opentele.server.core.model.AbstractObject

class MonitorKit extends AbstractObject {

    static hasMany = [meters: Meter]
    static belongsTo = [patient: Patient, department: Department]
    
    String name

	static constraints = {
        name(nullable:false, blank: false)
		patient (nullable:true)
        name unique: 'department'
    }
	
	String toString() {
		name
	}
}
