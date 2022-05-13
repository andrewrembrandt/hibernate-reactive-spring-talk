package ch.aaap.hibreactiveex;

import java.util.Map;
import javax.persistence.Persistence;
import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OmsApplication {
  @Bean
  public Mutiny.SessionFactory sessionFactory() {
    return Persistence.createEntityManagerFactory("hibernateReactive")
        .unwrap(Mutiny.SessionFactory.class);
  }

  public static void main(String[] args) {
    SpringApplication.run(OmsApplication.class);
  }
}
