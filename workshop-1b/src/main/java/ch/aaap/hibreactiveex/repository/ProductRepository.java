package ch.aaap.hibreactiveex.repository;

import ch.aaap.hibreactiveex.model.Product;
import ch.aaap.hibreactiveex.model.ProductDTO;
import ch.aaap.hibreactiveex.model.ProductDataDTO;
import ch.aaap.hibreactiveex.model.ProductMapper;
import io.smallrye.mutiny.Uni;
import lombok.val;
import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static io.smallrye.mutiny.converters.uni.UniReactorConverters.toMono;
import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;
import static org.springframework.data.relational.core.query.Update.update;

@Repository
public class ProductRepository {
  @Autowired
  Mutiny.SessionFactory sessionFactory;

  @Autowired
  ProductMapper productMapper;

  public Flux<Product> findAllActiveProducts() {
    return sessionFactory.withSession(
        session ->
          session
              .createQuery("from product p where p.deleted = false", Product.class)
              .getResultList()).convert().with(toMono()).flatMapMany(Flux::fromIterable);
  }

  public Mono<Void> addProduct(String sku, Product product) {
    product.setSku(sku);
    return sessionFactory.withSession(session -> session.persist(product)).convert().with(toMono());
  }

  public Mono<Integer> updateBySku(String sku, ProductDataDTO dto) {
    val product = productMapper.toProduct(dto);
    product.setSku(sku);
    return sessionFactory.withTransaction(session ->
        session.createQuery(
                "from product where sku=:sku", Product.class)
            .setParameter(":sku", sku)
            .getSingleResult()
            .flatMap(p -> {
              product.setId(p.getId());
              return session.merge(product).chain(() -> Uni.createFrom().item(1));
            })).convert().with(toMono());
  }

  public Mono<Integer> softDeleteBySku(String sku) {
    return sessionFactory.withSession(
        session ->
            session
                .createQuery("update product p set p.deleted=true where p.sku=:sku and p.deleted=false", Product.class)
                .executeUpdate()).convert().with(toMono());
  }
}
