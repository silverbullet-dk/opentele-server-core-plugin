databaseChangeLog = {

    changeSet(author: 'emh', id: 'migrate_to_bloodsugar_events') {
        dropForeignKeyConstraint(baseTableName: "continuous_blood_sugar_measurement", constraintName: "FK_CGM_MEASUREMENT")
        renameTable(oldTableName: "continuous_blood_sugar_measurement", newTableName: "continuous_blood_sugar_event")
        renameColumn(tableName: "continuous_blood_sugar_event", oldColumnName: "value", newColumnName: "glucose_value_inmmol_perl", columnDataType: '${double.type}')
        dropNotNullConstraint(tableName: "continuous_blood_sugar_event", columnName:"glucose_value_inmmol_perl", columnDataType: '${double.type}')

        addColumn(tableName: "continuous_blood_sugar_event") {
            column(name: "class", type: '${string.type}')
        }

        addColumn(tableName: "continuous_blood_sugar_event") {
            column(name: "duration_in_minutes", type: '${integer.type}')
        }

        addColumn(tableName: "continuous_blood_sugar_event") {
            column(name: "exercise_type", type: '${string.type}')
        }

        addColumn(tableName: "continuous_blood_sugar_event") {
            column(name: "exercise_intensity", type: '${string.type}')
        }

        addColumn(tableName: "continuous_blood_sugar_event") {
            column(name: "impending_ness", type: '${string.type}')
        }

        addColumn(tableName: "continuous_blood_sugar_event") {
            column(name: "insulin_type", type: '${string.type}')
        }

        addColumn(tableName: "continuous_blood_sugar_event") {
            column(name: "units", type: '${string.type}')
        }

        addColumn(tableName: "continuous_blood_sugar_event") {
            column(name: "food_type", type: '${string.type}')
        }

        addColumn(tableName: "continuous_blood_sugar_event") {
            column(name: "carbo_grams", type: '${string.type}')
        }

        addColumn(tableName: "continuous_blood_sugar_event") {
            column(name: "state_of_health", type: '${string.type}')
        }

        dropColumn(columnName: "cgm_graphs_created", tableName: "measurement")

        addForeignKeyConstraint(baseColumnNames: "measurement_id", baseTableName: "continuous_blood_sugar_event", constraintName: "FK_CGM_MEASUREMENT", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "measurement", referencesUniqueColumn: "false")

        createIndex(indexName: "record_num_idx", tableName: "continuous_blood_sugar_event") {
            column(name: "record_number")
        }
    }
}
