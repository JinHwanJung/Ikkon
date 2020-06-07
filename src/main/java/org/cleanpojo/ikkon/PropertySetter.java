package org.cleanpojo.ikkon;

import static org.cleanpojo.ikkon.ArgumentResolver.resolveArgument;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

interface PropertySetter {

    static void setProperties(Object source, Object target)
            throws IllegalAccessException, InvocationTargetException {

        for (Method method : target.getClass().getMethods()) {
            if (isSetter(method)) {
                Method setter = method;
                setProperty(source, target, setter);
            }
        }
    }

    private static boolean isSetter(Method method) {
        return method.getName().startsWith("set")
            && method.getReturnType().equals(void.class)
            && method.getParameterCount() == 1;
    }

    private static void setProperty(Object source, Object target, Method setter)
            throws IllegalAccessException, InvocationTargetException {

        var property = PropertyDescriptor.fromSetter(setter);
        Getter getter = GetterSelector.instance.select(property, source.getClass());
        if (getter != null) {
            setter.invoke(target, resolveArgument(property.getType(), getter, source));
        }
    }
}
