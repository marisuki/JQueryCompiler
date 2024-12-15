package org.jqc.common.annotate;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *  {@code Initiate} expresses BeforeAll semantics that need to execute
 *  before using all operators.
 *  In initiate() function, only {@code PrivateVar} variables are initiated.
 *  Differently, {@code Before} states that
 *  the initiation is required whenever applying this operator. For
 *  example, a query plan iterates a set of one-tuple-at-a-time operators
 *  on a data block, {@code Initiate} applies to the start of execution,
 *  while {@code Before} to the start of every operator. Moreover,
 *  for parallel processing, there is a barrier between the blocks of
 *  {@code Before} or {@code Initiate} and {@code Embed} run().
 */
@Embed
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface Initiate {
}
