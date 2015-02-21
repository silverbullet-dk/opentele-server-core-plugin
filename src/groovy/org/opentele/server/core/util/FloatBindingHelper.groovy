package org.opentele.server.core.util

import org.grails.databinding.BindingHelper
import org.grails.databinding.DataBindingSource

class FloatBindingHelper implements BindingHelper {

    @Override
    Object getPropertyValue(Object obj, String propertyName, DataBindingSource source) {
        return source[propertyName].equals("") ? null : source[propertyName]
    }
}
