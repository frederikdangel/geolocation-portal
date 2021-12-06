package com.frederik.springboot.cruddemo.service;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.util.Arrays;
import java.util.Objects;

public class FindNull {
    
    public static <T> void  fillNotNullFields(T reference, T source) {
        try {
            Arrays.asList(Introspector.getBeanInfo(reference.getClass(), Object.class)
                    .getPropertyDescriptors())
                    .stream()
                    .filter(pd -> Objects.nonNull(pd.getReadMethod()))
                    .forEach(pd -> {
                        try {
                            Object value = pd.getReadMethod().invoke(source);
                            if (value != null)
                                pd.getWriteMethod().invoke(reference, value);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
    }

}
