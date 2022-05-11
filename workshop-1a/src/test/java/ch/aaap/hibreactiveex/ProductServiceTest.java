package ch.aaap.hibreactiveex;

import ch.aaap.hibreactiveex.model.ProductDataDTO;
import ch.aaap.hibreactiveex.repository.ProductRepository;
import ch.aaap.hibreactiveex.service.ProductService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
  @Mock
  ProductRepository productRepo;

  @Test
  void addProduct() {
    val newProduct = new ProductDataDTO("Gipfeli", new BigDecimal("200.5"), LocalDate.now());
    Mockito.when(productRepo.addProduct(eq("C1"), eq(newProduct)))
        .thenReturn(Mono.just(1));

    val productService = new ProductService(productRepo);
    val createMono = productService.createProduct("C1", newProduct);
    createMono.block();

    verify(productRepo).addProduct(eq("C1"), eq(newProduct));
  }
}
