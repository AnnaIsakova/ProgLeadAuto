package ua.kiev.prog.modules;


import org.springframework.stereotype.Component;

@Component("smsSender")
public class SmsSender extends Sender {

    private static final SmsSender INSTANCE = new SmsSender();

    private SmsSender(){
        this.name = "SmsSender";
    }

    public static SmsSender getInstance(){
        return INSTANCE;
    }

    @Override
    public Object execute(Object... objects) {
        return null;
    }
}