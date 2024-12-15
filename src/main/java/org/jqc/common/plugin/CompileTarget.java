package org.jqc.common.plugin;

import org.jqc.common.annotate.After;
import org.jqc.common.annotate.Before;
import org.jqc.common.annotate.Embed;
import org.jqc.common.annotate.Initiate;

public abstract class CompileTarget {
    @Initiate
    public abstract void initiate();

    @Before
    public abstract void before();

    @Embed
    public abstract void run();

    @After
    public abstract void after();

    public abstract String[] sharedVariables();
}
