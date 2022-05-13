package ch.aaap.hibreactiveex.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
  String sku;
  private String name;
  private BigDecimal price;
  private LocalDate creationDate;
}
