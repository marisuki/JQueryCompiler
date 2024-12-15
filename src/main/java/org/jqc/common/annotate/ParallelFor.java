package org.jqc.common.annotate;

import java.lang.annotation.Repeatable;

/**
 * specify the parallel region
 * specify whether the variables in the parallel section are private or shared
 * specify how/if the threads are synchronized
 * specify how to parallelize loops
 * specify how the works is divided between threads (scheduling)
 *
 */
@Repeatable(ParallelForClauses.class)
public @interface ParallelFor {
    int positionOfForLoop();
    int numberOfThreads() default 1;
    String[] SharedVars() default {};
    String[] PrivateVars() default {};
    String[] AtomicVars() default {};
}
