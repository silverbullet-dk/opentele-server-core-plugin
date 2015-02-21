package org.opentele.server.model

import org.opentele.server.core.model.AbstractObject

class Message extends AbstractObject {

    Department department
    Patient patient
	Message inReplyTo
	
    String title
    String text

    Boolean sentByPatient = false

    Boolean isRead = false

    Date sendDate
    Date readDate

    void setTitle(String value) {
        title = value == null ? "" : value
    }

	static mapping = {
		sort sendDate: "desc"
	}
	
	String toString() {
 		"From: " + department +"\nTo: " + patient + "\nTitle: " + title
 	}
	
    static constraints = {
        department(nullable: false)
        patient(nullable: false)
        title(nullable: false, blank: true)
        text(nullable: false, maxSize: 2000)
        isRead(nullable: false)
        sentByPatient(nullable: false)
        sendDate(nullable: true) // Hvis vi skal haandtere kladder
        readDate(nullable: true)
		inReplyTo(nullable:true)
    }
}
