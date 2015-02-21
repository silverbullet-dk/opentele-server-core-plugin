package org.opentele.server.core.util

import org.springframework.beans.PropertyEditorRegistrar
import org.springframework.beans.PropertyEditorRegistry
    
class CustomPropertyEditorRegistrar implements PropertyEditorRegistrar{
    
    public void registerCustomEditors(PropertyEditorRegistry registry) {
        registry.registerCustomEditor(float.class, new CustomFloatEditor(true));
        registry.registerCustomEditor(Float.class, new CustomFloatEditor(true));
    }
}
