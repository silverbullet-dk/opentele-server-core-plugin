package org.opentele.server.core

import org.springframework.context.MessageSource
import org.springframework.web.servlet.i18n.SessionLocaleResolver

class I18nService {

    boolean transactional = false

    SessionLocaleResolver localeResolver
    MessageSource messageSource

    /**
     *
     * @param msgKey
     * @param defaultMessage default message to use if none is defined in the message source
     * @param objs objects for use in the message
     * @return
     */
    @SuppressWarnings("GroovyAccessibility")
    def msg(String msgKey, String defaultMessage = null, List objs = null) {

        def msg = messageSource.getMessage(msgKey,objs?.toArray(),defaultMessage,localeResolver.defaultLocale)

        if (msg == null || msg == defaultMessage) {
            log.warn("No i18n messages specified for msgKey: ${msgKey}")
            msg = defaultMessage
        }

        return msg
    }

    /**
     * Methode to look like g.message
     * @param args
     * @return
     */
    @SuppressWarnings("GroovyAssignabilityCheck")
    def message(Map args) {
        assert args.code
        return msg(args.code, args.default, args.attrs)
    }
}
