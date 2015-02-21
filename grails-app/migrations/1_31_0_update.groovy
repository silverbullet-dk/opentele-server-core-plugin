databaseChangeLog = {
    changeSet(author: 'emh', id: 'add_generic_events_to_bloodsugar_events') {
        addColumn(tableName: "continuous_blood_sugar_event") {
            column(name: "indicated_event", type: '${string.type}')
        }
    }

    changeSet(author: "toby (generated)", id: "1417695269597-1") {
        createTable(tableName: "help_image") {
            column(autoIncrement: "true", name: "id", type: '${id.type}') {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "help_image_PK")
            }

            column(name: "filename", type: '${string512.type}') {
                constraints(nullable: "false")
            }

            column(name: "version", type: '${id.type}') { constraints(nullable: "false") }
            column(name: "created_by", type: '${string.type}')
            column(name: "created_date", type: '${datetime.type}')
            column(name: "modified_by", type: '${string.type}')
            column(name: "modified_date", type: '${datetime.type}')
        }
    }

    changeSet(author: "toby (generated)", id: "1417695269597-2") {
        createTable(tableName: "help_info") {
            column(autoIncrement: "true", name: "id", type: '${id.type}') {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "help_info_PK")
            }

            column(name: "help_image_id", type: '${id.type}')

            column(name: "text", type: '${text.type}')

            column(name: "version", type: '${id.type}') { constraints(nullable: "false") }
            column(name: "created_by", type: '${string.type}')
            column(name: "created_date", type: '${datetime.type}')
            column(name: "modified_by", type: '${string.type}')
            column(name: "modified_date", type: '${datetime.type}')
        }
    }

    changeSet(author: "toby (generated)", id: "1417695269597-3") {
        createTable(tableName: "patient_help_info") {
            column(autoIncrement: "true", name: "id", type: '${id.type}') {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "patient_help_PK")
            }

            column(name: "help_image_id", type: '${id.type}')

            column(name: "text", type: '${text.type}')

            column(name: "version", type: '${id.type}') { constraints(nullable: "false") }
            column(name: "created_by", type: '${string.type}')
            column(name: "created_date", type: '${datetime.type}')
            column(name: "modified_by", type: '${string.type}')
            column(name: "modified_date", type: '${datetime.type}')
        }
    }

    changeSet(author: "toby (generated)", id: "1417528665697-4") {
        addColumn(tableName: "patient_questionnaire_node") {
            column(name: "help_info_id", type: '${id.type}')
        }
    }

    changeSet(author: "toby (generated)", id: "1417695269597-5") {
        addColumn(tableName: "questionnaire_node") {
            column(name: "help_info_id", type: '${id.type}')
        }
    }

    changeSet(author: "toby (generated)", id: "1417695269597-6") {
        addForeignKeyConstraint(baseColumnNames: "help_image_id", baseTableName: "help_info", constraintName: "FK_HELP_INF_IMG", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "help_image", referencesUniqueColumn: "false")
    }

    changeSet(author: "toby (generated)", id: "1417695269597-7") {
        addForeignKeyConstraint(baseColumnNames: "help_info_id", baseTableName: "patient_questionnaire_node", constraintName: "FK_PQN_HELP_INFO", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "patient_help_info", referencesUniqueColumn: "false")
    }

    changeSet(author: "toby (generated)", id: "1417695269597-8") {
        addForeignKeyConstraint(baseColumnNames: "help_info_id", baseTableName: "questionnaire_node", constraintName: "FK_QNODE_HELP_INFO", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "help_info", referencesUniqueColumn: "false")
    }
}
