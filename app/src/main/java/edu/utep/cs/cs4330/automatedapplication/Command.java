package edu.utep.cs.cs4330.automatedapplication;


public abstract class Command {

    String options;

    public Command(String options) {
        this.options = options;
    }

    public abstract void start();
}
