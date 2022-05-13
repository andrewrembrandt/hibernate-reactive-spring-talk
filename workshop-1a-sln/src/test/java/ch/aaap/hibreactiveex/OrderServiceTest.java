package ch.aaap.hibreactiveex;

import ch.aaap.hibreactiveex.model.NewOrderDTO;
import ch.aaap.hibreactiveex.repository.OrderProductRepository;
import ch.aaap.hibreactiveex.repository.OrderRepository;
import ch.aaap.hibreactiveex.service.OrderService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;

import static org.assertj.core.util.Lists.list;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
  @Mock
  OrderRepository orderRepo;

  @Mock
  OrderProductRepository orderProductRepo;

  @Test
  void addOrder() {
    val newOrder = new NewOrderDTO(list("A1213", "A1214"), "who@me.com");
    Mockito.when(orderRepo.addOrder(eq(newOrder), any(ZonedDateTime.class)))
        .thenReturn(Mono.just(1L));
    Mockito.when(orderProductRepo.addProductsForOrder(anyLong(), anyList())).thenReturn(Mono.just(2));

    val orderService = new OrderService(orderRepo, orderProductRepo);
    val createMono = orderService.createOrder(newOrder);
    createMono.block();

    verify(orderRepo).addOrder(eq(newOrder), any(ZonedDateTime.class));
    verify(orderProductRepo).addProductsForOrder(eq(1L), eq(newOrder.getProductSkus()));
  }
}
