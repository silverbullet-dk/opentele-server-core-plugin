class OpenteleServerCorePluginGrailsPlugin {

    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.1 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    def title = "Opentele Server Core Plugin"
    def author = "OpenTele"
    def authorEmail = ""
    def description = '''\
Core OpenTele plugin containing domain classes and common services used in both the provider and the citizen targeted 
OpenTele servers.
'''

    // URL to the plugin's documentation
    def documentation = "4s-online.dk"

    // Extra (optional) plugin metadata

    def license = "APACHE"

    def organization = [ name: "4S", url: "4s-online.dk" ]

    def developers = [ [ name: "Silverbullet A/S", email: "opentele@silverbullet.dk" ]]


    // Online location of the plugin's browseable source code.
//    def scm = [ url: "http://svn.codehaus.org/grails-plugins/" ]

}
