package ch.aaap.hibreactiveex;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
public class AsyncUpdateTest extends HibernateReactiveIntegrationTestBase {

  @Test
  void asyncTest() {
    // TODO 2: Implement two concurrent tasks:
    // a. Adding 10-100 products concurrently (with 1-10 second pauses);
    // b. While every 2 seconds dumping/printing the total value of all products at that point in time
  }
}
