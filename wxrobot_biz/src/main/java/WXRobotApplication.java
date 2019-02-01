import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.wx.robot")
public class WXRobotApplication {

    public static void main(String[] args) {
        SpringApplication.run(WXRobotApplication.class, args);
    }
}
