package com.example.hexagon.albummgt.order;

import com.example.hexagon.albummgt.common.response.ResponseMsg;
import com.example.hexagon.albummgt.user.driving.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/order")
@Tag(name = "Order", description = "Test with Redis Repository")
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @Operation(summary = "Get an order from Cache by its id")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Get order successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
        @ApiResponse(responseCode = "404", description = "Order not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @GetMapping("/{orderId}")
  public ResponseMsg getOrderById(@PathVariable("orderId") String orderId) {
    OrderEntity result = orderService.getOrderById(orderId);
    return ResponseMsg.success("get order from cache successfully", result);
  }

  @Operation(summary = "Get all orders with filter and pagination, but not support fuzzy search yet.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Get all orders successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @GetMapping
  public ResponseMsg getAllOrders(
      @RequestParam(value = "name",required = false) String name,
      @RequestParam(value = "userId",required = false) String userId,
      @RequestParam(value = "ids",required = false) List<String> ids,
      @RequestParam(value = "pageNum",defaultValue = "0") Integer pageNum,
      @RequestParam(value = "pageSize",defaultValue = "2") Integer pageSize,
      @RequestParam(value = "sortBy",defaultValue = "name") String sortBy) {

    PageRequest pageReq = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
    return ResponseMsg.success(
        "get all orders from cache successfully", orderService.getAllOrders(pageReq,name,userId,ids));
  }

  @Operation(summary = "Add new order with Redis Repository")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Add a new order successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @PostMapping
  public ResponseMsg createOrder(@RequestBody List<OrderEntity> request) {
    String orderId = orderService.addOrder(request);
    return ResponseMsg.success(
        "Add new order to cache successfully", UserDTO.builder().id(orderId).build());
  }

  @Operation(summary = "Delete a order from cache")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Delete a user successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @DeleteMapping("/{orderId}")
  public ResponseMsg delete(@PathVariable("orderId") String orderId) {
    orderService.delete(orderId);
    return ResponseMsg.success("delete order from cache successfully, id=" + orderId);
  }

  @DeleteMapping("/all")
  public ResponseMsg deleteAll() {
    orderService.deleteAll();
    return ResponseMsg.success("delete all order from cache successfully");
  }
}
