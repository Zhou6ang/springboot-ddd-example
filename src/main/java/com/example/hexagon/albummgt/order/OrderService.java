package com.example.hexagon.albummgt.order;

import com.example.hexagon.albummgt.common.exception.NotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderService {

  private OrderRepository orderRepository;

  public String addOrder(List<OrderEntity> request) {
    List<OrderEntity> entities =
        request.stream()
            .peek(
                e -> {
                  e.setCreatedDate(LocalDateTime.now().toString());
                  e.setUpdatedDate(LocalDateTime.now().toString());
                })
            .collect(Collectors.toList());
    orderRepository.saveAll(entities);
    return String.join(",", entities.stream().map(e -> e.getId() + "").toArray(String[]::new));
  }

  public Page<OrderEntity> getAllOrders(
      PageRequest pageReq, String name, String userId, List<String> ids) {

    OrderEntity.OrderEntityBuilder builder = OrderEntity.builder();
    if (name != null && !name.isEmpty()) {
      builder.name(name);
    }
    if (userId != null && !userId.isEmpty()) {
      builder.userId(userId);
    }

    ExampleMatcher matcher =
        ExampleMatcher.matching()
            .withMatcher("name", m -> m.exact())
            .withMatcher("userId", ExampleMatcher.GenericPropertyMatchers.exact());
//    ExampleMatcher.GenericPropertyMatchers.contains(); startsWith(); endsWith(); //not support by redis.

    return orderRepository.findAll(Example.of(builder.build(), matcher), pageReq);
  }

  public void delete(String orderId) {
    orderRepository.deleteById(orderId);
  }

  public OrderEntity getOrderById(String userId) {
    Optional<OrderEntity> opt = orderRepository.findById(userId);
    return opt.orElseThrow(NotFoundException::new);
  }

  public void deleteAll() {
    orderRepository.deleteAll();
  }
}
