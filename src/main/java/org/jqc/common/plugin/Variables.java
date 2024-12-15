package org.jqc.common.plugin;

import org.jqc.common.annotate.Initiate;

public abstract class Variables {
    @Initiate
    public abstract String[] generateSharedVars();
}
