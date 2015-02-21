package org.opentele.server.model.cgm

import org.opentele.server.model.types.cgm.ExerciseIntensity
import org.opentele.server.model.types.cgm.ExerciseType

class ExerciseEvent extends ContinuousBloodSugarEvent {

    ExerciseType exerciseType
    ExerciseIntensity exerciseIntensity
    Integer durationInMinutes

    static constraints = {
        exerciseType(nullable: true)
        exerciseIntensity(nullable: true)
        durationInMinutes(nullable: true)
    }


}
