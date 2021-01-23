package teachers.biniProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
//@EnableScheduling
public class BiniProjectApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BiniProjectApplication.class, args);
    }

}
