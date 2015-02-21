package org.opentele.server.model.cgm

import org.opentele.server.model.types.cgm.FoodType

class MealEvent extends ContinuousBloodSugarEvent {
    FoodType foodType
    String carboGrams

    static constraints = {
        foodType(nullable: true)
        carboGrams(nullable: true)
    }
}
