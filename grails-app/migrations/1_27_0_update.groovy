databaseChangeLog = {

    changeSet(author: 'km', id: 'add_ctg') {
        createTable(tableName: 'real_timectg') {
            column(autoIncrement: 'true', name: 'id', type: '${id.type}') {
                constraints(nullable: 'false', primaryKey: 'true', primaryKeyName: 'real_timectg_PK')
            }

            column(name: "version", type: '${id.type}') {
                constraints(nullable: "false")
            }

            column(name: "xml", type: '${text.type}') {
                constraints(nullable: "false")
            }
            column(name: "soap_action", type: '${string.type}') {
                constraints(nullable: "false")
            }

            column(name: "created_by", type: '${string.type}')
            column(name: "created_date", type: '${datetime.type}')
            column(name: "modified_by", type: '${string.type}')
            column(name: "modified_date", type: '${datetime.type}')
        }
    }
}
