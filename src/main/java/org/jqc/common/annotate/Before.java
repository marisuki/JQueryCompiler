package org.jqc.common.annotate;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 *  {@code @Before} is used to assign local/shared variables.
 *  All references to external objects should be declared here.
 */
@Embed
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface Before {

}
