package org.jqc.common.annotate;

/**
 * Free the namespace of a variable name.
 */
public @interface FreeVars {
    String[] value() default {};
}
