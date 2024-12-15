package org.jqc.example;

import org.jqc.common.annotate.*;
import org.jqc.common.plugin.CompileTarget;

public class annotatedInterface extends CompileTarget {
    @SharedVar int count;
    @SharedVar int var1;
    @PrivateVar int var2;

    @Initiate
    @SharedVariableMap(usedVars = {"count"}, sharedVars = {"counter_#2"})
    @Override
    public void initiate() {
        count = 10;
        var2 = 80;
    }

    @Before
    @SharedVariableMap(usedVars = {"count"}, sharedVars = {"counter_#2"})
    @Override
    public void before() {
        count += 60;
        count += var2;
    }

    @Embed
    @SharedVariableMap(usedVars = {"var1", "count"}, sharedVars = {"counter_#1", "counter_#2"})
    @BufferedVariables({"count"})
    @Override
    public void run() {
        count += var1;
    }

    @Override
    public void after() {

    }

    @Override
    public String[] sharedVariables() {
        constants constants = new constants() {
            @Override
            public String[] sharedVarsNamespace() {
                String[] sharedVars = new String[2];
                sharedVars[0] = "int counter_#1;";
                sharedVars[1] = "int counter_#2;";
                return sharedVars;
            }
        };
        return constants.sharedVarsNamespace();
    }
}
