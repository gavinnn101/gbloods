package org.gavin101.priv.gsnapegrass;

public abstract class Task {

    protected String name;

    public Task() {
        super();
        name = "Un-named";
    }

    public abstract boolean activate();
    public abstract void execute();

}
