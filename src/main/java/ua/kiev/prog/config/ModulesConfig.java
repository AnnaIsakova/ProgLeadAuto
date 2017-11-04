package ua.kiev.prog.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.kiev.prog.modules.EmailSender;
import ua.kiev.prog.modules.SmsSender;

@Configuration
public class ModulesConfig {

    @Bean("smsSender")
    public SmsSender smsSender(){
        return new SmsSender();
    }

    @Bean("emailSender")
    public EmailSender emailSender(){
        return new EmailSender();
    }
}
