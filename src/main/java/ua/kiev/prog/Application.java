package ua.kiev.prog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import ua.kiev.prog.entities.others.Client;
import ua.kiev.prog.entities.others.UserRole;
import ua.kiev.prog.modules.EmailSender;
import ua.kiev.prog.modules.SmsSender;
import ua.kiev.prog.services.ClientService;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Application.class, args);
		SmsSender smsSender = (SmsSender) context.getBean("smsSender");
		System.out.println("BEAN: " + smsSender.getName());
		EmailSender emailSender = (EmailSender) context.getBean("emailSender");
		System.out.println("BEAN: " + emailSender.getName());
	}

	@Bean
	public CommandLineRunner demo(final ClientService clientService) {
		return new CommandLineRunner() {
			@Override
			public void run(String... strings) throws Exception {
				ShaPasswordEncoder encoder = new ShaPasswordEncoder();

				Client manager = new Client();
				manager.setPhone("111");
				manager.setPassword("111");
				manager.setRole(UserRole.MANAGER);
				manager.setDeleted(false);
				clientService.create(manager.toDTO());

				Client admin = new Client();
				admin.setPhone("222");
				admin.setPassword("222");
				admin.setRole(UserRole.ADMIN);
				admin.setDeleted(false);
				clientService.create(admin.toDTO());

				for (int i = 0; i < 20; i++) {
					Client client = new Client();
					Integer phone = i * 11111111;
					client.setPhone(phone.toString());
					client.setRole(UserRole.USER);
					client.setDeleted(false);
					clientService.create(client.toDTO());
				}
			}
		};
	}
}
