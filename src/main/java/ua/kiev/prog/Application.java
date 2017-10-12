package ua.kiev.prog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner demo(final ClientService clientService) {
		return new CommandLineRunner() {
			@Override
			public void run(String... strings) throws Exception {
				ShaPasswordEncoder encoder = new ShaPasswordEncoder();


				Client manager = new Client();
				manager.setPhone("111");
				manager.setPassword("111"); //password
				manager.setRole(UserRole.MANAGER);
				clientService.create(manager);

				Client admin = new Client();
				admin.setPhone("222");
				admin.setPassword("222");//password
				admin.setRole(UserRole.ADMIN);
				clientService.create(admin);
			}
		};
	}
}
