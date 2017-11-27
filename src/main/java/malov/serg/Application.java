package malov.serg;

import malov.serg.Model.CustomUser;
import malov.serg.Model.Order;
import malov.serg.Model.UserRole;
import malov.serg.Service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(final UserService userService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {
               /* userService.addUser(new CustomUser("admin", "$2a$10$iCxmJ6AX2rlo34XqXVu6LenOpWR2wXdE8xQ9vq.eU63dWJHveyaBu", UserRole.ADMIN));
                */}
        };
    }


}