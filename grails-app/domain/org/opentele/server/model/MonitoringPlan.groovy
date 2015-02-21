package org.opentele.server.model

import org.opentele.server.core.model.AbstractObject

class MonitoringPlan extends AbstractObject {
    Date startDate
    Patient patient
    
    static hasMany = [questionnaireSchedules:QuestionnaireSchedule]
	
    static constraints = {
        patient(nullable: false)
        startDate(nullable: false)
    }	
}
