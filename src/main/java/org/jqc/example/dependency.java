package org.jqc.example;

import org.jqc.common.annotate.Before;
import org.jqc.common.annotate.Embed;
import org.jqc.common.annotate.Initiate;

public abstract class dependency {
    @Initiate
    public abstract void initiate();

    @Before
    public abstract void before();

    @Embed
    public abstract void run();
}
