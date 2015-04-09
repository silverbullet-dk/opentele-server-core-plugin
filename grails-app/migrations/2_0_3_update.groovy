databaseChangeLog = {

    changeSet(author: "Henrik (kih-1497)", id: "kih-1497-1") {
        modifyDataType(tableName: "audit_log_entry", columnName: "patient_cpr", newDataType: '${string128.type}')
    }

    changeSet(author: "Henrik (kih-1497)", id: "kih-1497-2") {
        modifyDataType(tableName: "patient", columnName: "cpr", newDataType: '${string128.type}')
    }

    changeSet(author: "Henrik (kih-1497)", id: "kih-1497-3") {
        modifyDataType(tableName: "monitor_kit", columnName: "name", newDataType: '${string128.type}')
    }

    changeSet(author: "Henrik (kih-1497)", id: "kih-1497-4") {
        modifyDataType(tableName: "patient_group", columnName: "name", newDataType: '${string128.type}')
    }

    changeSet(author: "RA (kih-1714)", id: "kih-1714-1") {
        delete(tableName: "real_timectg")
        renameTable(oldTableName: "real_timectg", newTableName: "real_time_ctg")

        addColumn(tableName: "real_time_ctg") {
            column(name: "patient_id", type: '${id.type}') {
                constraints(nullable: "false")
            }
        }

        addForeignKeyConstraint(baseColumnNames: "patient_id", baseTableName: "real_time_ctg",
                constraintName: "real_time_ctg_patient_FK",
                referencedColumnNames: "id", referencedTableName: "patient", referencesUniqueColumn: "false")

        createIndex(indexName: "patient_id_created_date_idx", tableName: "real_time_ctg") {
            column(name: "patient_id")
            column(name: "created_date")
        }
    }
}