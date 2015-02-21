// configuration for plugin testing - will not be included in the plugin zip

environments {
    development {

        grails.plugin.databasemigration.dropOnStart = true
        grails.plugin.databasemigration.updateOnStart = true
        grails.plugin.databasemigration.updateOnStartFileNames = ['changelog.groovy']
        grails.plugin.databasemigration.autoMigrateScripts = ['RunApp', 'TestApp']
    }
    test {

        grails.plugin.databasemigration.dropOnStart = true
        grails.plugin.databasemigration.updateOnStart = true
        grails.plugin.databasemigration.updateOnStartFileNames = ['changelog.groovy']
        grails.plugin.databasemigration.autoMigrateScripts = ['RunApp', 'TestApp']
        // Must be set due to bug in migration plugin: https://jira.grails.org/browse/GPDATABASEMIGRATION-160
        grails.plugin.databasemigration.forceAutoMigrate = true
    }
}

log4j = {

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
           'org.codehaus.groovy.grails.web.pages', //  GSP
           'org.codehaus.groovy.grails.web.sitemesh', //  layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping', // URL mapping
           'org.codehaus.groovy.grails.commons', // core / classloading
           'org.codehaus.groovy.grails.plugins', // plugins
           'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'

    warn   'org.mortbay.log'
}
