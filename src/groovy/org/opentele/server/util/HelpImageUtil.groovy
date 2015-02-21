package org.opentele.server.util

import org.codehaus.groovy.grails.web.context.ServletContextHolder
import org.opentele.server.model.HelpImage
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.opentele.server.model.HelpImage

import javax.servlet.ServletContext

class HelpImageUtil {

    public static ServletContext servletContext
    private static final Logger log = LoggerFactory.getLogger(HelpImageUtil.class)

    public static String getAndEnsureUploadDir() {
        def grailsApplication = new HelpImage().domainClass.grailsApplication
        def uploadDir = new File(grailsApplication.config.help.image.uploadPath)
        if (!uploadDir.exists())
            uploadDir.mkdir()
        return grailsApplication.config.help.image.uploadPath + "/"
    }

    public static HelpImage ensureHelpImageExists(String filename) {
        def helpImageInstance = HelpImage.findByFilename(filename);
        if (!helpImageInstance) {
            helpImageInstance = new HelpImage()
            helpImageInstance.filename = filename
        }

        def destFile = new File(getAndEnsureUploadDir() + filename)
        if (!destFile.exists() || getOverwriteExistingFiles()) {
            try {
                def imageFile = new File(getProvidedImagesDir() + "/" + filename)
                if (imageFile.exists()) {
                    imageFile.withInputStream { is ->
                        destFile << is
                    }
                }
            } catch (e) {
                log.warn("Exception when uploading image file: ${e.message}", e)
            }
        }

        helpImageInstance.save()

        return helpImageInstance
    }

    private static Boolean getOverwriteExistingFiles() {
        def grailsApplication = new HelpImage().domainClass.grailsApplication
        return grailsApplication.config.help.image.overwriteExistingFiles
    }

    private static String getProvidedImagesDir() {
        def grailsApplication = new HelpImage().domainClass.grailsApplication
        String resourcePath = grailsApplication.config.help.image.providedImagesPath
        if (!servletContext)
            servletContext = ServletContextHolder.servletContext
        return servletContext.getRealPath(resourcePath)
    }
}
