package ch.aaap.hibreactiveex.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class SkuNotFoundException extends RuntimeException {
  private String sku;
}
