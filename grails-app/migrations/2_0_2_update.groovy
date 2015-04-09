databaseChangeLog = {
    changeSet(author: "hra", id: "20000000000-1") {
        addForeignKeyConstraint(baseColumnNames: "help_image_id", baseTableName: "patient_help_info", constraintName: "FK_PT_HELP_INF_IMG", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "help_image", referencesUniqueColumn: "false")
    }
}
