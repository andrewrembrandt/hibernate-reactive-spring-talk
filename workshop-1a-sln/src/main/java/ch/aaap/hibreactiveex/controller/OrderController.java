package ch.aaap.hibreactiveex.controller;

import ch.aaap.hibreactiveex.model.NewOrderDTO;
import ch.aaap.hibreactiveex.model.NoProductsException;
import ch.aaap.hibreactiveex.model.OrderDTO;
import ch.aaap.hibreactiveex.model.SkuNotFoundException;
import ch.aaap.hibreactiveex.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Orders Basic API")
@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successful operation")})
public class OrderController {

  private final OrderService orderService;

  @GetMapping
  @Operation(
      summary = "Orders within a date/time range",
      description = "Returns all between the from and to date/times (inclusive)")
  Flux<OrderDTO> getOrdersBetween(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime from,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime to) {
    return orderService.getOrdersBetween(from, to);
  }

  @PostMapping
  @Operation(
      summary = "Create/add a new order",
      description =
          "Creates a new order with the referenced product SKUs - SKUs can be repeated for quantities > 1")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "400",
            description = "Bad request - supplied data is invalid",
            content = @Content())
      })
  Mono<Void> create(@RequestBody NewOrderDTO dto) {
    return orderService.createOrder(dto);
  }

  @ExceptionHandler
  ResponseEntity<String> handle(SkuNotFoundException skuException) {
    return ResponseEntity.badRequest()
        .body("Order for creation contains invalid SKUs: " + skuException.getSku());
  }

  @ExceptionHandler
  ResponseEntity<String> handle(NoProductsException noProductsException) {
    return ResponseEntity.badRequest()
        .body(
            "Order for creation must contain at least one SKU: " + noProductsException.getOrder());
  }
}
