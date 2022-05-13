package ch.aaap.hibreactiveex.model;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {
  ProductDTO toProductDto(Product product);

  ProductDataDTO toProductDataDto(Product product);

  Product toProduct(ProductDTO dto);

  Product toProduct(ProductDataDTO dto);
}
