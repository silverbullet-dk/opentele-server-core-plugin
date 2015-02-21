package org.opentele.server.model

import org.opentele.server.core.model.AbstractObject
import org.opentele.server.core.model.Schedule

class ScheduleWindow extends AbstractObject {

    Schedule.ScheduleType scheduleType
    int windowSizeMinutes

    void setWindowSizeHours(int hours) {
        windowSizeMinutes = hours * 60
    }

    void setWindowSizeDays(int days) {
        windowSizeMinutes = days * 24 * 60
    }

    static constraints = {
        scheduleType(unique: true)
        windowSizeMinutes min: 1
    }
}
