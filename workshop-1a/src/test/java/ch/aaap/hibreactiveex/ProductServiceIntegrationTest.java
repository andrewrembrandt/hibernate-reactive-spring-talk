package ch.aaap.hibreactiveex;

import static org.assertj.core.api.Assertions.assertThat;

import ch.aaap.hibreactiveex.model.ProductDataDTO;
import ch.aaap.hibreactiveex.repository.ProductRepository;
import ch.aaap.hibreactiveex.service.ProductService;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;


@SpringBootTest
@Import(R2dbcIntegrationTestInitialiser.class)
public class ProductServiceIntegrationTest {
  @Autowired
  ProductRepository productRepo;

  @Test
  void addProduct() {
    val newProduct = new ProductDataDTO("Gipfeli", new BigDecimal("200.5"), LocalDate.now());

    val productService = new ProductService(productRepo);

    StepVerifier.create(productService.createProduct("C1", newProduct)
        .then(productRepo.findAllActiveProductDTO().collectList()))
          .consumeNextWith(p -> assertThat(p).isNotEmpty())
        .verifyComplete();
  }
}
