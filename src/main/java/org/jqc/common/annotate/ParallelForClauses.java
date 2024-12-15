package org.jqc.common.annotate;

import java.lang.annotation.Documented;

@Documented
public @interface ParallelForClauses {
    ParallelFor[] value();
}
