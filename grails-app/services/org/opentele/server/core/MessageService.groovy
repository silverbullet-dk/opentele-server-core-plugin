package org.opentele.server.core

import org.opentele.server.core.exception.MessageException
import org.opentele.server.model.Department
import org.opentele.server.model.Message
import org.opentele.server.model.Patient
import org.springframework.transaction.annotation.Transactional

class MessageService {

    def springSecurityService
    def patientOverviewMaintenanceService

    // + client
    @Transactional
    def saveMessage (Message msg) {
        Date now = new Date()
        if (!msg.sendDate) {
            msg.sendDate = now
        }

        if (!msg.validate()) {
            log.warn "Not able to validate message"
            throw new MessageException ("Could not validate message" + msg.errors)
        }

        msg.save(flush: true)

        if (msg.hasErrors()) {
            log.error "Could not save message: " + msg.errors
            throw new MessageException ("Could not save message" + msg.errors)
        }

        patientOverviewMaintenanceService.updateOverviewFor(msg.patient)
        return msg
    }

    // + client
    @Transactional
    def saveMessage (def params) {
        def msg = new Message(params)
        return saveMessage(msg)
    }

    // + client
    @Transactional
    def saveMessage(Department department, Patient patient, String title, String message) {
        def msg = new Message(department: department, patient: patient, title: title, text: message, sendDate: new Date(), isRead:false)
        return saveMessage(msg)
    }

    // + client
    @Transactional
    boolean setRead(Message message) {
        message.isRead = true
        message.readDate = new Date()
        saveMessage(message)
    }

    @Transactional
    boolean setUnRead(Message message) {
        message.isRead = false
        message.readDate = null
        saveMessage(message)
    }
}
