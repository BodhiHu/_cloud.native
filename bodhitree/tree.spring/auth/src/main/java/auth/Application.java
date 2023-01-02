package bodhitree.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;

@RestController
@SpringBootApplication
@EnableAsync
public class Application {

    @RequestMapping(value = "/auth/register")
    public String register() {
        return "register success";
    }

    @RequestMapping(value = "/auth/login")
    public String login() {
        return "login success";
    }

    @RequestMapping(value = "/auth/logout")
    public String logout() {
        return "logout success";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("BodhiTree.Auth-");
        executor.initialize();
        return executor;
    }

}

