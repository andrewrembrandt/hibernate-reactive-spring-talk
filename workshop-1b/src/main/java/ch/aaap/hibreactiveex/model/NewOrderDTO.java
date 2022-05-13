package ch.aaap.hibreactiveex.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class NewOrderDTO {
  private List<String> productSkus;
  private String buyerEmail;
}
