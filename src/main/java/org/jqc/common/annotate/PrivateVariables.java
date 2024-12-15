package org.jqc.common.annotate;

public @interface PrivateVariables {
    String[] value() default {};
}
