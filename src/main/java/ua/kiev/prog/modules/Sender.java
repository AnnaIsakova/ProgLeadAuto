package ua.kiev.prog.modules;

public abstract class Sender {

    String name;
    public abstract Object execute(Object... objects);

    public String getName() {
        return name;
    }
}
