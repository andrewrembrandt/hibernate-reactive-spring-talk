package ch.aaap.hibreactiveex.repository;

import ch.aaap.hibreactiveex.model.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.convert.MappingR2dbcConverter;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public class OrderProductRepository {
  // TODO 1: Refactor these repositories to uni Hibernate Reactive & Mutiny

  @Autowired private DatabaseClient client;
  @Autowired private MappingR2dbcConverter mappingConverter;

  public Flux<ProductDTO> getProductsForOrder(long orderId) {
    return client
        .sql(
            "select p.sku, p.name, p.price, p.creation_date from product p "
                + "inner join order_product op on op.product_id = p.id where op.order_id = :order_id")
        .bind("order_id", orderId)
        .map( (row, meta) -> mappingConverter.read(ProductDTO.class, row, meta))
        .all();
  }

  public Mono<Integer> addProductsForOrder(long orderId, List<String> skus) {
    return Flux.fromIterable(skus)
        .flatMap(
            sku -> {
              return client
                  .sql(
                      "insert into order_product(order_id, product_id) "
                          + "select :order_id, p.id from product p where p.sku = :sku")
                  .bind("order_id", orderId)
                  .bind("sku", sku)
                  .fetch()
                  .rowsUpdated();
            })
        .collectList()
        .map(numUpdatedList -> numUpdatedList.stream().mapToInt(i -> i).sum());
  }
}
