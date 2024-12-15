package org.jqc.common.annotate;

/**
 * Used to connect variables in a target and buffer or
 * variable namespace.
 * Shared variables will be initiated (by buffer value) before
 * the initiate() function when the shared variable names are
 * declared by @SharedVariableMap(...), and similarly in before()
 * before each run() execution.
 * Variable sharing is by reference (pointer).
 * That is, any modifications by current program modify buffer value.
 * {@code usedVars}: usedVars by this program.
 * {@code sharedVars}: sharedVars name in buffer.
 */
public @interface SharedVariableMap {
    String[] usedVars() default {};
    String[] sharedVars() default {};
}
