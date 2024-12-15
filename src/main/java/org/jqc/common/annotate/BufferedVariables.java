package org.jqc.common.annotate;

public @interface BufferedVariables {
    String[] value() default {};
}
