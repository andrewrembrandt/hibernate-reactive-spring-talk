package ch.aaap.hibreactiveex;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.hibreactiveex.model.ProductDataDTO;
import ch.aaap.hibreactiveex.repository.ProductRepository;
import ch.aaap.hibreactiveex.service.ProductService;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import reactor.test.StepVerifier;


@SpringBootTest
@Import(R2dbcIntegrationTestInitialiser.class)
public class ProductServiceIntegrationTest extends HibernateReactiveIntegrationTestBase {
  @Autowired
  ProductRepository productRepo;
  @Autowired
  ProductService productService;

  @Test
  void addProduct() {
    val newProduct = new ProductDataDTO("Gipfeli", new BigDecimal("200.5"), LocalDate.now());

    StepVerifier.create(productService.createProduct("C1", newProduct)
        .then(productRepo.findAllActiveProducts().collectList()))
          .consumeNextWith(p -> assertThat(p).isNotEmpty())
        .verifyComplete();
  }
}
