package org.opentele.server.core.util;

/*
 * Copyright 2004-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import grails.converters.JSON;
import groovy.lang.GroovyObject;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.codehaus.groovy.grails.web.converters.exceptions.ConverterException;
import org.codehaus.groovy.grails.web.converters.marshaller.ObjectMarshaller;
import org.codehaus.groovy.grails.web.json.JSONWriter;
import org.springframework.beans.BeanUtils;

/**
 * @author Siegfried Puchbauer
 * @since 1.1
 *
 * HRA: Adapted class to not include class attribute
 *
 */
public class CustomGroovyBeanJSONMarshaller implements ObjectMarshaller<JSON> {

    public boolean supports(Object object) {
        return object instanceof GroovyObject;
    }

    public void marshalObject(Object o, JSON json) throws ConverterException {
        JSONWriter writer = json.getWriter();
        try {
            writer.object();
            for (PropertyDescriptor property : BeanUtils.getPropertyDescriptors(o.getClass())) {
                String name = property.getName();
                Method readMethod = property.getReadMethod();
                if (readMethod != null && !(name.equals("metaClass")) && !(name.equals("class"))) {
                    Object value = readMethod.invoke(o, (Object[]) null);
                    writer.key(name);
                    json.convertAnother(value);
                }
            }
            for (Field field : o.getClass().getDeclaredFields()) {
                int modifiers = field.getModifiers();
                if (Modifier.isPublic(modifiers) && !(Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers))) {
                    writer.key(field.getName());
                    json.convertAnother(field.get(o));
                }
            }
            writer.endObject();
        }
        catch (ConverterException ce) {
            throw ce;
        }
        catch (Exception e) {
            throw new ConverterException("Error converting Bean with class " + o.getClass().getName(), e);
        }
    }
}
