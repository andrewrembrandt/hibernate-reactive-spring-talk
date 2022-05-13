package ch.aaap.hibreactiveex;


import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import java.util.function.Consumer;
import lombok.val;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;

@ContextConfiguration(initializers = HibernateReactiveIntegrationTestBase.Initializer.class)
public class HibernateReactiveIntegrationTestBase {

  public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
      Consumer<CreateContainerCmd> fixPorts = cc -> cc.withPortBindings(new PortBinding(Ports.Binding.bindPort(25432), new ExposedPort(5432)));

      val postgres = new PostgreSQLContainer("postgres:12.3")
          .withUsername("user")
          .withPassword("password")
          .withCreateContainerCmdModifier(fixPorts);
      postgres.start();

    }
  }

}
