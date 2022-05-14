package ch.aaap.hibreactiveex;

import ch.aaap.hibreactiveex.model.Product;
import ch.aaap.hibreactiveex.model.ProductDataDTO;
import ch.aaap.hibreactiveex.model.ProductMapper;
import ch.aaap.hibreactiveex.model.ProductMapperImpl;
import ch.aaap.hibreactiveex.repository.ProductRepository;
import ch.aaap.hibreactiveex.service.ProductService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@Import({ProductService.class, ProductMapperImpl.class})
public class ProductServiceTest {
  @MockBean
  ProductRepository productRepo;
  @Autowired
  ProductService productService;
  @Autowired
  ProductMapper productMapper;

  @Test
  void addProduct() {
    val newProduct = new Product(1L, "gip1", "Gipfeli", new BigDecimal("200.5"), LocalDate.now(), false);
    Mockito.when(productRepo.addProduct(eq("C1"), eq(newProduct)))
        .thenReturn(Mono.empty());

    val createMono = productService.createProduct("C1", productMapper.toProductDataDto(newProduct));
    createMono.block();

    verify(productRepo).addProduct(eq("C1"), eq(newProduct));
  }
}
