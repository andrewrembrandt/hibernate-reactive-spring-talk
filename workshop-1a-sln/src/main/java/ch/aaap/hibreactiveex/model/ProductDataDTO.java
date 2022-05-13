package ch.aaap.hibreactiveex.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDataDTO {
  private String name;
  private BigDecimal price;
  private LocalDate creationDate;
}
