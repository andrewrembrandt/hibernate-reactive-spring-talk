package ch.aaap.hibreactiveex.service;

import ch.aaap.hibreactiveex.model.ProductDTO;
import ch.aaap.hibreactiveex.model.ProductDataDTO;
import ch.aaap.hibreactiveex.model.ProductMapper;
import ch.aaap.hibreactiveex.model.SkuAlreadyExistsException;
import ch.aaap.hibreactiveex.model.SkuNotFoundException;
import ch.aaap.hibreactiveex.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class ProductService {
  private final ProductRepository repo;
  private final ProductMapper mapper;

  public Flux<ProductDTO> getAllActiveProducts() {
    return repo.findAllActiveProducts().map(mapper::toProductDto);
  }

  public Mono<Void> deleteProduct(String sku) {
    return repo.softDeleteBySku(sku).flatMap(ensureSingleUpdate(sku, false));
  }

  public Mono<Void> createProduct(String sku, ProductDataDTO dto) {
    return repo.addProduct(sku, mapper.toProduct(dto));
  }

  public Mono<Void> updateProduct(String sku, ProductDataDTO dto) {
    return repo.updateBySku(sku, dto).flatMap(ensureSingleUpdate(sku, false));
  }

  private Function<Integer, Mono<? extends Void>> ensureSingleUpdate(String sku, boolean create) {
    return numUpdated -> {
      if (numUpdated == 1) return Mono.empty();
      else {
        if (create) return Mono.error(new SkuAlreadyExistsException(sku));
        else return Mono.error(new SkuNotFoundException(sku));
      }
    };
  }
}
