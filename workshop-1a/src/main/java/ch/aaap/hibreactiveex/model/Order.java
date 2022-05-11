package ch.aaap.hibreactiveex.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class Order {
  @Id
  private Long id;
  private String buyerEmail;
  private ZonedDateTime placedTime;
}
