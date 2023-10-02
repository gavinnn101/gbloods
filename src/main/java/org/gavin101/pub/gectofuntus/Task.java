package org.gavin101.pub.gectofuntus;

public abstract class Task {

    protected String name;

    public Task() {
        super();
        name = "Un-named";
    }

    public abstract boolean activate();
    public abstract void execute();

}

