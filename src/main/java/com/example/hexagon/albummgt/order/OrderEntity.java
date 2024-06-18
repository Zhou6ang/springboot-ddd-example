package com.example.hexagon.albummgt.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.TemporalType;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("order")
@Builder
@Data
@AllArgsConstructor
public class OrderEntity {

  @Hidden
  @Id
  private String id;
  @Indexed
  private String name;
  private BigDecimal price;
  @Indexed
  private String userId;
  @Hidden
  private String createdDate;
  @Hidden
  private String updatedDate;
  @TimeToLive
//  @Builder.Default
//  @JsonIgnore
  @Hidden
  private long expiration;

  public OrderEntity() {
  }

  private long calculateTTLToNext3AM() {
    Calendar now = Calendar.getInstance();
    Calendar next3AM = (Calendar) now.clone();
    next3AM.add(Calendar.DAY_OF_MONTH, 0);
    next3AM.set(Calendar.HOUR_OF_DAY, 0);
    next3AM.set(Calendar.MINUTE, 0);
    next3AM.set(Calendar.SECOND, 0);
    next3AM.set(Calendar.MILLISECOND, 0);

    return next3AM.getTimeInMillis() - now.getTimeInMillis();
  }

}
