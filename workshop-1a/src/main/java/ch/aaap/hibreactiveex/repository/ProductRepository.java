package ch.aaap.hibreactiveex.repository;

import ch.aaap.hibreactiveex.model.ProductDTO;
import ch.aaap.hibreactiveex.model.ProductDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;
import static org.springframework.data.relational.core.query.Update.update;

@Repository
public class ProductRepository {
  // TODO 1: Refactor these repositories to uni Hibernate Reactive & Mutiny
  @Autowired private DatabaseClient client;
  @Autowired private R2dbcEntityTemplate template;

  public Flux<ProductDTO> findAllActiveProductDTO() {
    return template
        .select(ProductDTO.class)
        .from("product")
        .matching(query(where("deleted").isFalse()))
        .all();
  }

  public Mono<Integer> addProduct(String sku, ProductDataDTO dto) {
    return client
        .sql(
            "insert into product(sku, name, price, creation_date, deleted) "
                + "values(:sku, :name, :price, :creation_date, false) on conflict do nothing")
        .bind("sku", sku)
        .bind("name", dto.getName())
        .bind("price", dto.getPrice())
        .bind("creation_date", dto.getCreationDate())
        .fetch()
        .rowsUpdated();
  }

  public Mono<Integer> updateBySku(String sku, ProductDataDTO dto) {
    return template
        .update(ProductDTO.class)
        .inTable("product")
            .matching(query(where("sku").is(sku)))
        .apply(
            update("name", dto.getName())
                .set("price", dto.getPrice())
                .set("creation_date", dto.getCreationDate()));
  }

  public Mono<Integer> softDeleteBySku(String sku) {
    return template
        .update(ProductDTO.class)
        .inTable("product")
        .matching(query(where("sku").is(sku).and("deleted").is(false)))
        .apply(update("deleted", true));
  }
}
