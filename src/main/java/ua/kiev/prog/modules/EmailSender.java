package ua.kiev.prog.modules;

public class EmailSender extends Sender {

    private static final EmailSender INSTANCE = new EmailSender();

    private EmailSender(){
        this.name = "EmailSender";
    }

    public static EmailSender getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public Object execute(Object... objects) {
        return null;
    }
}
