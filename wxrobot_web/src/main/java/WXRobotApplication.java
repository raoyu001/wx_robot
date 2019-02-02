import com.wx.robot.biz.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication(scanBasePackages = "com.wx.robot")
public class WXRobotApplication {

    public static void main(String[] args) {
        SpringApplication.run(WXRobotApplication.class, args);
    }

    @Bean
    CommandLineRunner run(LoginService loginService){
        Thread.setDefaultUncaughtExceptionHandler((t,e) -> {
            log.error("程序崩溃："+e.getMessage());
            log.info("ready restart");
            //SpringApplication.run(WXRobotApplication.class);
        });
        return args -> loginService.login();
    }
}
