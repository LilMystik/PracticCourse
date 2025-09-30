package source;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = "source")
@EnableCaching
public class MyApplicationJava {
  public static void main(String[] args) {
    SpringApplication.run(MyApplicationJava.class, args);
  }
}
