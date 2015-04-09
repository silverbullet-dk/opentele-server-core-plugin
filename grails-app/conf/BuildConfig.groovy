grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.7
grails.project.source.level = 1.7

grails.server.port.http = 8082 // To prevent clashes w/provider+citizen server when testing

//grails.project.war.file = "target/${appName}-${appVersion}.war"
//grails.project.war.file = "target/${appName}.war"

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "verbose" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve

    def gebVersion = "0.10.0"

    repositories {
        grailsCentral()
        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        mavenLocal()
        //mavenRepo "http://snapshots.repository.codehaus.org"

        mavenCentral()
        grailsCentral()
        mavenRepo name: "SilverbulletExt", root: "http://ci.silverbullet.dk/artifactory/ext-release-local"
        mavenRepo "http://download.java.net/maven/2/"

        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

        // runtime 'mysql:mysql-connector-java:5.1.18'

        test "org.gebish:geb-spock:$gebVersion"
        test "org.gebish:geb-junit4:$gebVersion"

        compile 'cglib:cglib:3.1'

        compile 'commons-collections:commons-collections:3.2.1'
    }

    plugins {
        runtime ":build-test-data:2.2.2"

        build(":tomcat:7.0.54",
              ":release:3.0.1",
              ":rest-client-builder:2.0.3") {
            export = false
        }
        compile ":tomcat:7.0.54"

        runtime ":database-migration:1.4.0"

        runtime ":kih-auditlog:1.1"

        test ":hibernate4:4.3.5.5"
        //test ":hibernate:3.6.10.18"
        //runtime ":hibernate:3.6.10.18"
        runtime ":hibernate4:4.3.5.5"

        runtime ":spring-security-core:2.0-RC4"

        test ":geb:$gebVersion"
        test ":code-coverage:1.2.5"
    }
}
