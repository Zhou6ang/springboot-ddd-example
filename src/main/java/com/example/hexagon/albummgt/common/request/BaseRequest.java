package com.example.hexagon.albummgt.common.request;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
public class BaseRequest {
  private int pageSize;
  private int pageNumber;
  private String sortBy;
  private String sortDirection;
}
