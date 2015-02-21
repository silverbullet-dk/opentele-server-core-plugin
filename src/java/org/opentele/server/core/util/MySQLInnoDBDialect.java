package org.opentele.server.core.util;

import java.sql.Types;

public class MySQLInnoDBDialect extends org.hibernate.dialect.MySQL5InnoDBDialect {
 
   /**
    * Initializes a new instance of the {@link MySQLInnoDBDialect} class.
    */
    public MySQLInnoDBDialect() {
        registerColumnType(Types.VARBINARY, 255, "blob");
    }
}