package com.example.hexagon.albummgt.order;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.data.repository.query.ListQueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends ListQueryByExampleExecutor<OrderEntity>, ListCrudRepository<OrderEntity, String> {

  List<OrderEntity> findByTitle(String title);

  List<OrderEntity> findByUserId(String userId);
  Page<OrderEntity> findByUserId(String userId, Pageable pageReq);

  Page<OrderEntity> findByNameContaining( String name, Pageable pageReq);

  Page<OrderEntity> findByIdIn(List<String> ids,Pageable pageReq);
}
