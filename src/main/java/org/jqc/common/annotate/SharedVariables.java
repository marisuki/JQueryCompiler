package org.jqc.common.annotate;

public @interface SharedVariables {
    String[] value() default {};
}
